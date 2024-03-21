package com.example.zegeju.Controller;

import com.example.zegeju.Domain.HomePage.PaymentDetails;
import com.example.zegeju.Service.HomePageGenerator;
import com.example.zegeju.Service.JwtTokenGenerator;
import com.example.zegeju.Service.TokenManagement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/api/v4/student")
public class HomePageController {
    @Autowired
    public HomePageGenerator homePageGenerator;

    @Autowired
    public JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    public TokenManagement tokenManagement;
//    @RequestHeader(value = "Authorization") String authorizationHeader,
//     System.out.println(authorizationHeader);
//    String[] parts = authorizationHeader.split(" ");

    @GetMapping("/getHomePageData")
    public Object getData(HttpServletRequest request) throws ExecutionException, InterruptedException, IOException {

//        System.out.println(authorizationHeader);
       // System.out.println(request);
       // System.out.println();
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        // Extract the token from the Authorization header
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

       else {
            System.out.println("NO AUTHORIZATION");
        }
        System.out.println(acccessToken);
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        System.out.println(decrpt);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){

        String userInfo=jwtTokenGenerator.decryptAccessToken(acccessToken);

        return homePageGenerator.getHomePageData(userInfo);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }

    }
    @PutMapping("/updateHomePageData")
    public Object updateData(@RequestBody Map<String,Object> Data ) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= (String) Data.get("access_token");
        PaymentDetails paymentDetails= (PaymentDetails) Data.get("paymentDetails");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);;

            String typeOfTest=null;
            String fieldname=null;
            return homePageGenerator.updateHomepageFiled(email,typeOfTest,fieldname);



        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }




    }
//    @RequestMapping(value = "/get-refresh-token", method = RequestMethod.OPTIONS)
//    public Object endPointTesting(){
//        HashMap<String,String>name=new HashMap<>();
//        name.put("name","Brook");
//        return name;
//    }
//    @RequestMapping(value = "/get-refresh-token", method = RequestMethod.OPTIONS)
//    public Object getAccessToken(@RequestBody String refreshToken) {
//        if(jwtTokenGenerator.validateToken(refreshToken)){
//            String userinfo= jwtTokenGenerator.decryptRefreshToken(refreshToken);
//            return jwtTokenGenerator.generateAccessToken(userinfo);
//        }
//
//        else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//        }
//    }
    @PostMapping("/refresh-token")
    public Object refreshToken(@RequestBody Map<String,Object>  loginData) throws IOException {
        String refreshToken = (String) loginData.get("refresh_token");
       // System.out.println(refreshToken);
//        }
//        String refreshToken = loginData.get("refreshToken");
        String decrpt=jwtTokenGenerator.decryptToken(refreshToken);

        if(decrpt.equals(jwtTokenGenerator.decryptToken(refreshToken))){
            String userinfo= jwtTokenGenerator.decryptToken(refreshToken);
            return jwtTokenGenerator.generateAccessToken(userinfo);
        }

        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(refreshToken).getExpiration().getTime();
        }
//        tokenManagement.refreshToken(request, response);

    }
}
