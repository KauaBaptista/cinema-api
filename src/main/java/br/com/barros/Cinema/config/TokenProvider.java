package br.com.barros.Cinema.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${jwt.expiration}")
    private Long expirationTime;
    @Value("${jwt.key}")
    private String key;

    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        assert user != null;
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

}
