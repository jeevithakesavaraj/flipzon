package com.ideas2it.flipzon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * This represents for token which is response back to the user
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Integer status;
    private String token;
}
