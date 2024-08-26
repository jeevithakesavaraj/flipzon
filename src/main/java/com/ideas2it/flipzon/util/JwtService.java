package com.ideas2it.flipzon.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.ideas2it.flipzon.model.User;

/**
 * <p>
 * JWt service is for generating Jwt token
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Component
public class JwtService {

    private static String secretKey = "Flipzon";
    private static long expiryDuration = 60 * 60;

    /**
     * <p>
     * Generate JWT token for the user
     * </p>
     * @param user  {@link User}
     * @return String Jwt token
     */
    public String generateJwt(User user) {
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;

        Date date = new Date(milliTime);
        Date expiry = new Date(expiryTime);

        Claims claims = Jwts.claims()
                .setIssuer(String.valueOf(user.getId()))
                .setExpiration(expiry).build();

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
