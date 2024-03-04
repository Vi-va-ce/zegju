package com.example.zegeju.Controller;


import com.example.zegeju.Service.JwtTokenGenerator;
import com.example.zegeju.Service.ResultService;
import com.example.zegeju.Service.StudentService;
import com.example.zegeju.Service.TokenManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(path = "/api/v3/student")


public class ResultController {
    @Autowired
    public ResultService resultService;
    @Autowired
    public TokenManagement tokenManagement;
    @Autowired
    public JwtTokenGenerator jwtTokenGenerator;
    @GetMapping("/getTest")
    public Object getTest(@RequestBody Map<String,Object> Data ) throws ExecutionException, InterruptedException, IOException {

        String acccessToken= (String) Data.get("access_token");
        String test_id= (String) Data.get("test_id");
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);

            return resultService.getStudentAnalysis(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }





    }
    @PostMapping("/registertestCatagory")
    public Object putTest(@RequestBody Map<String,Object> Data) {
        String acccessToken= (String) Data.get("accessToken");
        Object testCatagory= Data.get("testCatagory");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);

            return resultService.registerCatagory(testCatagory);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }



}
