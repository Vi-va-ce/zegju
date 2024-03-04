package com.example.zegeju.Service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenManagement {
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;

    public Object getEmail(String accessToken) {
        if (jwtTokenGenerator.validateTokenn(accessToken)) {
            String email = jwtTokenGenerator.decryptAccessToken(accessToken);

            return email;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }
    }
//        public Object validateToken(String accessToken){
//            if(jwtTokenGenerator.validateAccessToken(accessToken)){
//                return true;
//            }
//            else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
//            }

            public Object validateToken(String Token){
                if(jwtTokenGenerator.extractClaims(Token)!=null){
                    Claims claims = jwtTokenGenerator.extractClaims(Token);
                    long expirationTime = claims.getExpiration().getTime();
                    long currentTime = System.currentTimeMillis();
                    return currentTime <= expirationTime;
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
                }
    }

//    public Object generateAccessToken(String userinfo) {
//
//    }
}
