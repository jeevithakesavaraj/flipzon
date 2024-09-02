package com.ideas2it.flipzon.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.dto.UserDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Delivery;
import com.ideas2it.flipzon.model.User;
/**
 * <p>
 * UserMapper have methods for conversion of entity and dto
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class UserMapper {

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    /**
     * <p>
     *  Convert UserDto to UserEntity
     * </p>
     * @param userDto {@link UserDto}
     * @return User  {@link User}
     */
    public static User convertUserEntity (UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }

    /**
     * <p>
     * Convert CustomerDto to User
     * </p>
     * @param customerDto {@link CustomerDto}
     * @return {@link User}
     */
    public static User convertUserEntity (CustomerDto customerDto) {
        return User.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .password(encoder.encode(customerDto.getPassword()))
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }

    /**
     * <p>
     * Convert Delivery Dto to User Entity
     * </p>
     * @param deliveryDto {@link DeliveryDto}
     * @return {@link User}
     */
    public static User convertUserEntity (DeliveryDto deliveryDto) {
        return User.builder()
                .name(deliveryDto.getName())
                .email(deliveryDto.getEmail())
                .password(encoder.encode(deliveryDto.getPassword()))
                .phoneNumber(deliveryDto.getPhoneNumber())
                .build();
    }

    /**
     * <p>
     * Convert Delivery Dto to Entity
     * </p>
     * @param deliveryDto {@link DeliveryDto}
     * @param user {@link User}
     * @return {@link Delivery}
     */
    public static Delivery convertDeliveryEntity (DeliveryDto deliveryDto, User user) {
        return Delivery.builder()
                .user(user)
                .idProof(deliveryDto.getIdProof())
                .build();
    }

    /**
     * <p>
     * Convert Customer Dto to Entity
     * </p>
     * @param customerDto {@link CustomerDto}
     * @param user {@link User}
     * @return {@link Customer}
     */
    public static Customer convertCustomerEntity (CustomerDto customerDto, User user) {
        return Customer.builder()
                .user(user)
                .build();
    }
}
