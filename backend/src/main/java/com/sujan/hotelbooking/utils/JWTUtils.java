package com.sujan.hotelbooking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtils {

    // Generate a secret key securely
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)  // Set claims (can add more info in the claims map)
                .setSubject(username)  // Set the subject as the username
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // Set expiration time (24 hours)
                .signWith(secretKey)  // Sign the token with the secret key
                .compact();
    }

    // Extract claims from the token
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secretKey)  // Set the secret key
                .build()
                .parseClaimsJws(token)  // Parse the token
                .getBody();
        return claimsResolver.apply(claims);  // Return the extracted claims
    }

    // Extract username from token (subject)
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);  // Extract the subject (username)
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);  // Extract the expiration date
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Return true if the token has expired
    }

    // Validate the token (check if username matches and token is not expired)
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));  // Return true if valid
    }
}
