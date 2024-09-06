package com.ideas2it.flipzon.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.configuaration.JwtService;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryPersonDto;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserVerifyDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.DeliveryPerson;
import com.ideas2it.flipzon.model.Role;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.UserRole;
import com.ideas2it.flipzon.util.OtpGenerator;
/**
 * <p>
 * This class represents for service for user authentication
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private EmailSenderService emailSenderService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
    private static final Map<String, String> otpSaver = new HashMap<>();
    private static final Map<String, CustomerDto> cacheMemory = new HashMap<>();
    private static final Map<String, DeliveryPersonDto> secondCacheMemory = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    /**
     * <p>
     * register customer
     * </p>
     *
     * @param customerDto {@link CustomerDto}
     * @return APIResponse {@link APIResponse}
     */
    public APIResponse registerCustomer(CustomerDto customerDto) {
        Role roles = roleService.getRoleByName(UserRole.ROLE_CUSTOMER);
        if (userService.checkByEmail(customerDto.getEmail())) {
            User existingUser = userService.getByEmail(customerDto.getEmail());
            boolean checkRole = checkRole(existingUser.getRole(), roles.getId());
            if (!checkRole) {
                Set<Role> userRoles = existingUser.getRole();
                userRoles.add(roles);
                existingUser.setId(customerDto.getId());
                existingUser.setRole(userRoles);
                User savedUser = userService.addUser(existingUser);
                Customer customer = UserMapper.convertCustomerEntity(customerDto, savedUser);
                customerService.addCustomer(customer);
                apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                apiResponse.setStatus(HttpStatus.CREATED.value());
                return apiResponse;
            }
            throw new MyException("Email Id - " + customerDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        boolean isMailSend = true;
        for (Map.Entry<String, String> entry : otpSaver.entrySet()) {
            if (entry.getKey().equals(customerDto.getEmail())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData("Verify your mail with OTP");
            return apiResponse;
        }
        String receiverMail = customerDto.getEmail();
        String subject = "OTP for registration";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "OTP for signUp Verification" + otp;
        otpSaver.put(customerDto.getEmail(), otp);
        emailSenderService.sendEmail(receiverMail, subject, body);
        cacheMemory.put(customerDto.getEmail(), customerDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * <p>
     * Register delivery person
     * </p>
     *
     * @param deliveryPersonDto {@link DeliveryPersonDto}
     * @return APIResponse {@link APIResponse}
     */
    public APIResponse registerDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
        Role role = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
        if (userService.checkByEmail(deliveryPersonDto.getEmail())) {
            User user = userService.getByEmail(deliveryPersonDto.getEmail());
            boolean checkRole = checkRole(user.getRole(), role.getId());
            if (!checkRole) {
                Set<Role> roles = user.getRole();
                roles.add(role);
                user.setId(user.getId());
                user.setRole(roles);
                User savedUser = userService.addUser(user);
                DeliveryPerson deliveryPerson = UserMapper.convertDeliveryEntity(deliveryPersonDto, savedUser);
                deliveryService.createDelivery(deliveryPerson);
                apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                apiResponse.setStatus(HttpStatus.CREATED.value());
                return apiResponse;
            }
            throw new MyException("Email Id - " + deliveryPersonDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        boolean isMailSend = true;
        for (Map.Entry<String, String> entry : otpSaver.entrySet()) {
            if (entry.getKey().equals(deliveryPersonDto.getEmail())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData("Verify your mail with OTP");
            return apiResponse;
        }
        String receiverMail = deliveryPersonDto.getEmail();
        String subject = "OTP for Registration";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "OTP for signUp Verification" + otp;
        otpSaver.put(deliveryPersonDto.getEmail(), otp);
        emailSenderService.sendEmail(receiverMail, subject, body);
        secondCacheMemory.put(deliveryPersonDto.getEmail(), deliveryPersonDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * <p>
     * Authenticate login using credentials
     * </p>
     *
     * @param loginDto {@link LoginDto}
     * @return {@link AuthenticationResponse}
     */
    public AuthenticationResponse authenticate(LoginDto loginDto) {
        User user = userService.getByEmail(loginDto.getEmail());
        if (null == user) {
            throw new ResourceNotFoundException("No user is found in this emailId:", loginDto.getEmail());
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        loginDto.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user);
        LOGGER.info("User authenticated successfully!");
        return AuthenticationResponse.builder()
                .status(HttpStatus.OK.value())
                .token(jwtToken)
                .build();

    }

    /**
     * <p>
     * Check if the role is present in the set of roles of the user
     * </p>
     *
     * @param roles  Set of roles where user have registered
     * @param roleId ID of the role
     * @return boolean if the role is present, return true or else false
     */
    public boolean checkRole(Set<Role> roles, long roleId) {
        for (Role userRole : roles) {
            if (userRole.getId() == roleId) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Verify the customer using Otp
     * </p>
     * @param userVerifyDto {@link UserVerifyDto}
     * @return APIResponse  {@link APIResponse}
     */
    public APIResponse verifyCustomer(UserVerifyDto userVerifyDto) {
        boolean isVerifyOtp = false;
        for (Map.Entry<String, String> entry : otpSaver.entrySet()) {
            if (entry.getKey().equals(userVerifyDto.getMailID()) && entry.getValue().equals(userVerifyDto.getOtp())) {
                otpSaver.remove(entry.getKey());
                isVerifyOtp = true;
            }
        }
        if (isVerifyOtp) {
            Role roles = roleService.getRoleByName(UserRole.ROLE_CUSTOMER);
            for (Map.Entry<String, CustomerDto> entry : cacheMemory.entrySet()) {
                if (entry.getKey().equals(userVerifyDto.getMailID())) {
                    User user = UserMapper.convertUserEntity(entry.getValue());
                    user.setRole(Collections.singleton(roles));
                    User savedUser = userService.addUser(user);
                    Customer customer = UserMapper.convertCustomerEntity(entry.getValue(), savedUser);
                    customerService.addCustomer(customer);
                    apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                    apiResponse.setStatus(HttpStatus.CREATED.value());
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    /**
     * <p>
     * Verify the delivery person using Otp
     * </p>
     * @param userVerifyDto {@link UserVerifyDto}
     * @return APIResponse  {@link APIResponse}
     */
    public APIResponse verifyDeliveryPerson(UserVerifyDto userVerifyDto) {
        boolean isVerifyOtp = false;
        for (Map.Entry<String, String> entry : otpSaver.entrySet()) {
            if (entry.getKey().equals(userVerifyDto.getMailID()) && entry.getValue().equals(userVerifyDto.getOtp())) {
                otpSaver.remove(entry.getKey());
                isVerifyOtp = true;
            }
        }
        if (isVerifyOtp) {
            Role roles = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
            for (Map.Entry<String, DeliveryPersonDto> entry : secondCacheMemory.entrySet()) {
                if (entry.getKey().equals(userVerifyDto.getMailID())) {
                    User user = UserMapper.convertUserEntity(entry.getValue());
                    user.setRole(Collections.singleton(roles));
                    User savedUser = userService.addUser(user);
                    DeliveryPerson deliveryPerson = UserMapper.convertDeliveryEntity(entry.getValue(), savedUser);
                    deliveryService.createDelivery(deliveryPerson);
                    apiResponse.setData(savedUser.getEmail() + " registered successfully.");
                    apiResponse.setStatus(HttpStatus.CREATED.value());
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return apiResponse;
        }
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }
}
