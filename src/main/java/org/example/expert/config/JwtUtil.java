package org.example.expert.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.expiration}")
    private long tokenTime; // 60분

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;
    //private Key key;
    //private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken
            (Long userId, String email, String nickname ,UserRole userRole) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(String.valueOf(userId))
                        .claim("email", email)
                        .claim("nickname", nickname)
                        .claim("userRole", userRole)
                        .expiration(new Date(date.getTime() + tokenTime))
                        .issuedAt(date) // 발급일
                        .signWith(key)
                        .compact();
    }

    public boolean validateToken(String token) {

        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            extractClaims(token);
            return true;
        }catch (JwtException e) {
            return false;
        }

    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ServerException("Not Found Token");
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserId(String token) {
        return Long.parseLong(extractClaims(token).getSubject());
    }

    public String extractUserEmail(String token) {
        return extractClaims(token).get("email").toString();
    }

    public String extractUserNickname(String token) {
        return extractClaims(token).get("nickname").toString();
    }

    public String extractUserRole(String token) {
        return extractClaims(token).get("userRole").toString();
    }

}
