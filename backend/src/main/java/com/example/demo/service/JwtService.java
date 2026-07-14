package com.example.demo.service;

import com.example.demo.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;

    @PostConstruct
    public void initializeSecretKey() {
        secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }

    public String generateToken(Users user){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email" , user.getEmail())
                .claim("role" , user.getRole())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String token){
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role" ,  String.class);
    }

    public String extractEmail(String token){
        return extractAllClaims(token).get("email"  , String.class);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token){
        return !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

}
