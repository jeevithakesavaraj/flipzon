package com.ideas2it.flipzon.util;

import java.security.Key;
import java.util.Date;

import com.ideas2it.flipzon.exception.AccessDeniedException;
import com.ideas2it.flipzon.exception.MyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    private static String secretKey = "5ab076caf4fa2a4542fdf504474ae7c6ce1d719e04ee749d170335f51648f859";
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
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void verifyToken(String authorization) {
        try {
            Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authorization).getBody();
        } catch (Exception e) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
