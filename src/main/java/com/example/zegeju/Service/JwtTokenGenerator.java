package com.example.zegeju.Service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class JwtTokenGenerator {
    private static final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";
        private static final long ACCESS_TOKEN_EXPIRATION_TIME = 10_000; // 30 seconds
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 30_600_000; //

    public static String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public static String generateRefreshToken(String username) {


        // Set the cookie path to root ("/") to make it accessible across the entire application
        return generateToken(username, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private static String generateToken(String username, long expirationTime) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
                SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("email", username)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationTime, ChronoUnit.SECONDS)))
                .signWith(hmacKey)
                .compact();
        return jwtToken;
    }
//    private static String generateToken(String username, long expirationTime) {
//        Claims claims = Jwts.claims().setSubject(username);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
public  String decryptToken(String jwtString) {
//    String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
            SignatureAlgorithm.HS256.getJcaName());

    Jws<Claims> jwt = Jwts.parserBuilder()
            .setSigningKey(hmacKey)
            .build()
            .parseClaimsJws(jwtString);

    return jwt.getBody().getSubject();
}
    public  String decryptAccessToken(String token) {
        return decryptToken(token);
    }

    public  String decryptRefreshToken(String token) {
        return decryptToken(token);
    }


//    public  String decryptToken(String token) {
//        //final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";
//
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            return claims.getSubject();
//        } catch (ExpiredJwtException e) {
//            // Token has expired
//            // Handle accordingly
//        } catch (JwtException e) {
//            // Invalid token
//            // Handle accordingly
//        }
//
//        return null;
//    }

//    public  String decryptRefreshTokens(String token) {
//       // final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";
//
//        try {
//
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            return claims.getSubject();
//
//
//        } catch (ExpiredJwtException e) {
//            // Token has expired
//            // Handle accordingly
//        } catch (JwtException e) {
//            // Invalid token
//            // Handle accordingly
//        }
//
//        return null;
//    }

    public Authentication validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();

            if (expirationDate.before(currentDate)) {
                // Token has expired
                return new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
            } else {
                // Token is valid
                String username = claims.getSubject();
                List<GrantedAuthority> authorities = extractAuthorities(claims);
                return new UsernamePasswordAuthenticationToken(username, token, authorities);
            }
        } catch (ExpiredJwtException e) {
            // Token has expired
            return new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
        } catch (JwtException e) {
            // Invalid token
            return new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
        }
    }
    public boolean validateTokenn(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();

            return expirationDate.before(currentDate);
        } catch (ExpiredJwtException e) {
            // Token has expired
            return true;
        } catch (JwtException e) {
            // Invalid token
            return true;
        }
    }
    private List<GrantedAuthority> extractAuthorities(Claims claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract authorities from claims if applicable
        List<String> authorityList = claims.get("authorities", List.class);
        if (authorityList != null) {
            for (String authority : authorityList) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;
    }
//        public  boolean validateToken(String token) {
//            try {
//                Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
//                        SignatureAlgorithm.HS256.getJcaName());
//
//                Jws<Claims> jwt = Jwts.parserBuilder()
//                        .setSigningKey(hmacKey)
//                        .build()
//                        .parseClaimsJws(token);
//
//                long currentTime
//                return ()<=jwt.getBody().getExpiration();
//            } catch (Exception e) {
//                return false;
//            }
//        }

        public Claims extractClaims(String accessToken) {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(accessToken).getBody();
        }

        private static SecretKey getSecretKey() {
            return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        }


    public  boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
