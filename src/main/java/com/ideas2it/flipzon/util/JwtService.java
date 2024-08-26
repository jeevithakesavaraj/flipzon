package com.ideas2it.flipzon.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import com.ideas2it.flipzon.model.User;

@Component
public class JwtService {

    private static String secretKey = "Flipzon";
    private static long expiryDuration = 60 * 60;

    Date date = new Date();
    Date expiryTime = new Date(expiryDuration);


    public String generateJwt(User user) {
        Claims claims = Jwts.claims()
                .setIssuer(String.valueOf(user.getId()))
                .setExpiration(expiryTime).build();
        return Jwts.builder().setClaims(claims).compact();
    }
}
