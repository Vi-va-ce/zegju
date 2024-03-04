package com.example.zegeju.Domain.Student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
        private String accessToken;
        private String refreshToken;
        public JwtResponse(String accessToken, String refreshToken) {
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
        }

}
