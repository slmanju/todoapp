package com.manjula.todo.config.security;

import com.manjula.todo.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public final class JwtUtils {

    private static String SECRET = "winteriscoming";
    private static int EXPIRATION = 60 * 60 * 1000; // 1 hour

    public static String generateToken(final JwtDto jwtDto) {
        Claims claims = Jwts.claims().setSubject(jwtDto.getUsername());
        claims.put("userId", jwtDto.getUserId() + "");
        claims.put("role", jwtDto.getRole());

        Date createdDate = new Date();
        Date expiration = new Date(createdDate.getTime() + EXPIRATION);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static JwtDto parseToken(final String token) {
        Claims body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return JwtDto.builder()
                .username(body.getSubject())
                .userId(Long.valueOf((String) body.get("userId")))
                .role((String) body.get("role"))
                .build();
    }

}
