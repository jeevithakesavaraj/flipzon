package com.ideas2it.flipzon.service;

import java.util.Collections;
import java.util.Set;

import com.ideas2it.flipzon.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.configuration.JwtService;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.dto.AuthenticationResponse;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryPersonDto;
import com.ideas2it.flipzon.dto.LoginDto;
import com.ideas2it.flipzon.dto.UserVerifyDto;
import com.ideas2it.flipzon.mapper.UserMapper;
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
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    /**
     * <p>
     * register customer
     * </p>
     *
     * @param customerDto {@link CustomerDto}
     * @return String - OTP verification
     */
    public String registerCustomer(CustomerDto customerDto) {
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
                return "User registered successfully in customer role.";
            }
            throw new MyException("Email Id - " + customerDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        boolean isMailSend = true;
        for (Otp otp : emailSenderService.getOtpAndMailId()) {
            if (otp.getMailId().equals(customerDto.getEmail())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            return "Verify your mailID with OTP";
        }
        String receiverMail = customerDto.getEmail();
        String subject = "OTP for registration";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "OTP for signUp Verification " + otp;
        emailSenderService.addOtp(customerDto.getName(), customerDto.getEmail(),customerDto.getPassword(), customerDto.getPhoneNumber(), otp);
        emailSenderService.sendEmail(receiverMail, subject, body);
        return "Verify your mailID with OTP";
    }

    /**
     * <p>
     * Register delivery person
     * </p>
     *
     * @param deliveryPersonDto {@link DeliveryPersonDto}
     * @return String - OTP verification
     */
    public String registerDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
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
                return "User registered successfully in delivery person role.";
            }
            throw new MyException("Email Id - " + deliveryPersonDto.getEmail()
                    + " Already Exist.Please Login or use Another EmailId");
        }
        boolean isMailSend = true;
        for (Otp otp : emailSenderService.getOtpAndMailId()) {
            if (otp.getMailId().equals(deliveryPersonDto.getEmail())) {
                isMailSend = false;
                break;
            }
        }
        if (!isMailSend) {
            return "Verify your mailID with OTP";
        }
        String receiverMail = deliveryPersonDto.getEmail();
        String subject = "OTP for Registration";
        String otp = String.valueOf(OtpGenerator.generateOtp());
        String body = "OTP for signUp Verification" + otp;
        emailSenderService.addOtp(deliveryPersonDto.getName(), deliveryPersonDto.getEmail(),deliveryPersonDto.getPassword(), deliveryPersonDto.getPhoneNumber(), deliveryPersonDto.getIdProof(), otp);
        emailSenderService.sendEmail(receiverMail, subject, body);
        return "Verify your mailID with OTP";
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
     * Verify the customer using Otpl
     * </p>
     * @param userVerifyDto {@link UserVerifyDto}
     * @return savedCustomerDto {@link CustomerDto}
     */
    public CustomerDto verifyCustomer(UserVerifyDto userVerifyDto) {
        boolean isVerifyOtp = false;
        for (Otp otp : emailSenderService.getOtpAndMailId()) {
            if (otp.getMailId().equals(userVerifyDto.getEmailId()) && otp.getOtp().equals(userVerifyDto.getOtp())) {
                isVerifyOtp = true;
            }
        }
        if (isVerifyOtp) {
            Role roles = roleService.getRoleByName(UserRole.ROLE_CUSTOMER);
            for (Otp otp : emailSenderService.getOtpAndMailId()) {
                if (otp.getMailId().equals(userVerifyDto.getEmailId())) {
                    CustomerDto customerDto = CustomerDto.builder()
                            .name(otp.getName())
                            .email(otp.getMailId())
                            .password(otp.getPassword())
                            .phoneNumber(otp.getPhoneNumber())
                            .build();
                    User user = UserMapper.convertUserEntity(customerDto);
                    user.setRole(Collections.singleton(roles));
                    User savedUser = userService.addUser(user);
                    Customer customer = UserMapper.convertCustomerEntity(customerDto, savedUser);
                    CustomerDto savedCustomerDto = customerService.addCustomer(customer);
                    emailSenderService.deleteOtp(otp);
                    return savedCustomerDto;
                }
            }
        }
        throw new ResourceNotFoundException();
    }

    /**
     * <p>
     * Verify the delivery person using Otp
     * </p>
     * @param userVerifyDto {@link UserVerifyDto}
     * @return savedDeliveryPersonDto {@link DeliveryPersonDto}
     */
    public DeliveryPersonDto verifyDeliveryPerson(UserVerifyDto userVerifyDto) {
        boolean isVerifyOtp = false;
        for (Otp otp : emailSenderService.getOtpAndMailId()) {
            if (otp.getMailId().equals(userVerifyDto.getEmailId()) && otp.getOtp().equals(userVerifyDto.getOtp())) {
                isVerifyOtp = true;
            }
        }
        if (isVerifyOtp) {
            Role roles = roleService.getRoleByName(UserRole.ROLE_DELIVERYPERSON);
            for (Otp otp : emailSenderService.getOtpAndMailId()) {
                if (otp.getMailId().equals(userVerifyDto.getEmailId())) {
                    DeliveryPersonDto deliveryPersonDto = DeliveryPersonDto.builder()
                            .name(otp.getName())
                            .email(otp.getMailId())
                            .password(otp.getPassword())
                            .phoneNumber(otp.getPhoneNumber())
                            .idProof(otp.getIdProof())
                            .build();
                    User user = UserMapper.convertUserEntity(deliveryPersonDto);
                    user.setRole(Collections.singleton(roles));
                    User savedUser = userService.addUser(user);
                    DeliveryPerson deliveryPerson = UserMapper.convertDeliveryEntity(deliveryPersonDto, savedUser);
                    DeliveryPersonDto savedDeliveryPersonDto = deliveryService.createDelivery(deliveryPerson);
                    return savedDeliveryPersonDto;
                }
            }
        }
        throw new ResourceNotFoundException();
    }
}
