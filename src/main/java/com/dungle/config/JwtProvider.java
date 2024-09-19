package com.dungle.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        // String jwt = Jwts.builder().setIssuer("dungle").setIssuedAt(new
        // Date()).setExpiration(new Date(new Date().getTime()+86400000)).claim("email",
        // auth.getName()).signWith(key).compact();
        String jwt = Jwts.builder()
                .claim("iss", "dungle") // Replaces setIssuer("dungle")
                .claim("iat", new Date()) // Replaces setIssuedAt(new Date())
                .claim("exp", new Date(new Date().getTime() + 86400000)) // Replaces setExpiration(new Date())
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
        return jwt;
    }

    public static String getEmailFromJwtToken(String jwt) {
        // Xử lý Bearer Token
        jwt = jwt.substring(7); // Xóa phần "Bearer " khỏi token

        // Phân tích JWT
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()  // Sử dụng phương thức parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)  // Phân tích JWT đã ký và lấy các claims
                .getBody();
        String email = String.valueOf(claims.get("email"));
        // return claims.get("email", String.class);  // Lấy email từ claim
        return email;
    }
}
