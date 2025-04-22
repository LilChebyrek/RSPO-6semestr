package ru.sibsutis.shop.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.secret-key}")
    private final String SECRET_KEY = "your_very_long_secret_key_must_be_32_chars!";

    private final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Value("${security.session.lifetime}")
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 часов

    public String generateToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

    public Long extractUserId(String token) {
        return Long.parseLong(extractClaims(token).getSubject());
    }

    public LocalDateTime extractExpiryDate(String token) {
        Date expirationDate = extractClaims(token).getExpiration();
        return expirationDate.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
