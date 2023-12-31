package com.example.cafemanagementsystem.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtUtil {
    Logger log= LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET_KEY = "34743777217A25432A462D4A614E645267556B586E3272357538782F413F4428";

    public String extractUsername(String token) {
        return extractClam(token, Claims::getSubject);
    }

    public String generateToken(String username, String role) {
        log.info("Inside generateToken()");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.info("Inside createToken()");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //yesma dheraii time ko lagi token rakhnu hudaina past ma ne jana sakxa
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 60 * 1000))) // 10 hours in milliseconds
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClam(token, Claims::getExpiration);
    }

    public <T> T extractClam(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSiginInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSiginInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
