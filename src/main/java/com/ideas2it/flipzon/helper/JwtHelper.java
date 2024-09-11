package com.ideas2it.flipzon.helper;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ideas2it.flipzon.exception.AuthenticationException;

public class JwtHelper {

    /**
     * <p>
     *  Extract username from token
     * </p>
     *
     * @return username - username from token
     */
    public static String extractUserNameFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return principal.toString();
        }
        throw new AuthenticationException("Unable to retrieve user details from security context");
    }
}
