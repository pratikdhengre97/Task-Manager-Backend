package com.yourapp.taskmanager.security;

import com.yourapp.taskmanager.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

//    private final String SECRET = ${JWT_SECRET};
    private final String secret;
    private final Key signKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
        return signKey;
    }

//    public String generateToken(String email, String role) {
//        return Jwts.builder()
//                .setSubject(email)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
public String generateToken(User user) {
    return Jwts.builder()
            .setSubject(user.getId().toString())
            .claim("email", user.getEmail())
            .claim("role", user.getRole())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // returns UUID string
    }

//    public String extractEmail(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }


    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}