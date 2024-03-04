package com.example.zegeju.Controller;


import com.example.zegeju.Domain.TestResponse.TestResponse;
import com.example.zegeju.Service.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping(path = "/api/v2/student")

public class TestController {


    @Autowired
    private TestService testService;

    @Autowired
    public TokenManagement tokenManagement;
    @Autowired
    public JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PracticeTestService practiceTestService;

    @GetMapping("/getPracticeTest")
    public Object getTest(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
        String test_id=loginData.get("test_id");
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }
    @GetMapping("/getFinalTest")
    public Object getFinalTest(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
        String test_id=loginData.get("test_id");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }


    }

    @GetMapping("/diagnosticTest")
    public Object getDiagnosingTest(@RequestBody Map<String, String> loginData ) throws ExecutionException, InterruptedException, IOException {
        String acccessToken = loginData.get("access_token");
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest("diagnostic_testOne");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }


    }
    @GetMapping("/diagnosticTEST")
    public Object getDiagnosingTst() throws ExecutionException, InterruptedException, IOException {

            return testService.getTest("diagnostic_testOne");



    }
    @GetMapping("/Test")
    public Object getTTest() {

            return "am here";

    }

    @PostMapping("/registerTest")
    public Object putTest(@RequestBody Object test1) {
            return testService.registerTest(test1);

    }
    @PostMapping("/registerAnswer")
    public Object registerAnswer(@RequestBody Map<String,Object> registration){
        String acccessToken= (String) registration.get("access_token");
        Object ans=registration.get("test1");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.registerAnswer(ans);

        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }

    }/// this was for testing puposes

    @PostMapping("/userResponse")
    public  Object userResponse( @RequestBody HashMap<String,Object> userResponse) throws IOException, ExecutionException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();

        String acccessToken= (String) userResponse.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {

            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);

            return performanceService.generateResult(userResponse);
        }

        else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
       }

    }
//    @Component
//    public class MapDeserializer extends JsonDeserializer<Map<String,Object>> {
//
//        @Override
//        public Map<String,Object> deserialize(JsonParser p, DeserializationContext ctxt)
//                throws IOException {
//
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String,Object> map = mapper.readValue(p, Map.class);
//
//            return map;
//        }
//
//    }
//
//    // Configuration
//    @Configuration
//    public class DeserializerConfig {
//
//        @Bean
//        public MapDeserializer deserializer() {
//            return new MapDeserializer();
//        }
//
//        @Bean
//        public ObjectMapper objectMapper() {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.registerModule(new SimpleModule()
//                    .addDeserializer(Map.class, deserializer()));
//            return mapper;
//        }
//
//    }
    @GetMapping("/dashBoardData")
    //@RequestBody String accessToken,@RequestParam String use_id
    public Object getDashBoardData(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        //if (true)return performanceService.getDashBoardData("welde.gesesse@gmail.com");
        String acccessToken=loginData.get("access_token");

//        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
//        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
//            String emailtoken=jwtTokenGenerator.decryptAccessToken(acccessToken);

                String emailtoken= "welde.gesesse@gmail.com";
           return performanceService.getDashBoardData(emailtoken);


//        String accessToken= new String();
//        String use_id= new String();
//        if(tokenManagement.validateToken(accessToken)!="Invalid access token"){
//            String email=jwtTokenGenerator.decryptAccessToken(accessToken);
//            return performanceService.getDashBoardData(use_id);
//
//        }

        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
    }
    @GetMapping("/leaderBoard")
    //@RequestBody String accessToken
    public  Object leaderBoard(@RequestBody Map<String, String> loginData){
        String acccessToken=loginData.get("access_token");
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){

            String emailtoken=jwtTokenGenerator.decryptAccessToken(acccessToken);
//        if(tokenManagement.validateToken(accessToken)!="Invalid access token"){
//            String email=jwtTokenGenerator.decryptAccessToken(accessToken);

            return performanceService.generateLeaderBoard();
      }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");


    }

    @PostMapping("/practiceTestResult")
    public Object realTimeTestResult(@RequestBody HashMap<String,Object> userResponse){
//    HashMap<String,String>data=new HashMap<>();

//      System.out.println(userResponse);
        String acccessToken= (String) userResponse.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
        return practiceTestService.generateRealTimeResult(userResponse);
    }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");

    }
    @GetMapping("/practiceTestResponse")
    //@RequestBody String requestBody
    public Object generateRealTimeResult(@RequestBody  HashMap<String,Object>userResponse){
        String acccessToken= (String) userResponse.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            return practiceTestService.generateRealTimeResult(userResponse);
        }
        System.out.println(userResponse);
        practiceTestService.generateRealTimeResult(userResponse);
        return 0;
    }

}
