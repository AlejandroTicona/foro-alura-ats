package com.forohub.forohub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service

public class JwtService {
  private static final String SECRET_KEY = "bXlfc3VwZXJfc2VjcmV0X2tleV90aGF0X2lzXzMyX2J5dGVz";
  private static final long EXPIRATION_MS = 1000 * 60 * 60 * 10; // 10 hours
  private Key getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256) // ✅ use Key object
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()       // ✅ parserBuilder() not parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

}
