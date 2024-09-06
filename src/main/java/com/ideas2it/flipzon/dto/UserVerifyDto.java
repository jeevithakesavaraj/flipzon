package com.ideas2it.flipzon.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * This is for verify otp for users
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Data
@Builder
public class UserVerifyDto {

    @Email
    private String mailID;
    private String otp;
}
