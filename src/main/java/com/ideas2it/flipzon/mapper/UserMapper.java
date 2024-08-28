package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * UserMapper have methods for conversion of entity and dto
 * </p>
 */
public class UserMapper {

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
    /**
     * <p>
     *  Convert UserDto to UserEntity
     * </p>
     * @param customerDto {@link CustomerDto}
     * @return User  {@link User}
     */
    public static User convertUserEntity (CustomerDto customerDto) {
        return User.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .password(encoder.encode(customerDto.getPassword()))
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }

    public static User convertUserEntity (DeliveryDto deliveryDto) {
        return User.builder()
                .name(deliveryDto.getName())
                .email(deliveryDto.getEmail())
                .password(deliveryDto.getPassword())
                .phoneNumber(deliveryDto.getPhoneNumber())
                .build();
    }
}
