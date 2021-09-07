package smehhh.reactive.webFlux.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import smehhh.reactive.webFlux.domain.User;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken).getSubject();
    }

    public boolean validateToken(String authToken) {
        return getClaimsFromToken(authToken).getExpiration().before(new Date());
    }

    public Claims getClaimsFromToken(String authToken) {
        String key = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String genToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));
        long exp = Long.parseLong(expiration);
        Date creationDate = new Date();
        Date expDate = new Date(creationDate.getTime() + exp * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
