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

    @GetMapping("/getPracticeTest1")
    public Object getPracticeTest1(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
//        String test_id=loginData.get("test_id");practice_testOne
        String test_id="practice_testOne";
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }
    @GetMapping("/getPracticeTest2")
    public Object getPracticeTest2(HttpServletRequest request,@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
//        String acccessToken= loginData.get("access_token");
        String authorizationHeader = request.getHeader("Authorization");

        System.out.println(authorizationHeader);
        // Extract the token from the Authorization headers
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        //        String test_id=loginData.get("test_id");
        String test_id="practice_testTwo";
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }
    @GetMapping("/getPracticeTest3")
    public Object getPracticeTest3(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
        //        String test_id=loginData.get("test_id");
        String test_id="practice_testThree";
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }
    @GetMapping("/getPracticeTest4")
    public Object getPracticeTest4(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
        //        String test_id=loginData.get("test_id");
        String test_id="practice_testFour";
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);
            return testService.getTest(test_id);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }



    }
    @GetMapping("/getPracticeTest5")
    public Object getPracticeTest5(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        String acccessToken= loginData.get("access_token");
        //        String test_id=loginData.get("test_id");
        String test_id="practice_testFive";
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
//        String test_id=loginData.get("test_id");
        String test_id="final_test_one";
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
    public Object getDiagnosingTest(@RequestHeader("authorization") String authorizationHeader) throws ExecutionException, InterruptedException, IOException {
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        //        String test_id=loginData.get("test_id");
        String test_id="diagnostic_testOne";
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
    public  Object userResponse(HttpServletRequest request, @RequestBody HashMap<String,Object> userResponse) throws IOException, ExecutionException, InterruptedException {
       String authorizationHeader = request.getHeader("Authorization");

        System.out.println(authorizationHeader);
        // Extract the token from the Authorization headers
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        //String acccessToken= (String) userResponse.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {

            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);

            return performanceService.generateResult(emailtoken,userResponse);
        }

        else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
       }

    }
    @PostMapping("/TestResult")
    public  Object getSectionTestResult(HttpServletRequest request) throws IOException, ExecutionException, InterruptedException {
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {

            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);

            return performanceService.getSectionTestScore(emailtoken);
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
    public Object getDashBoardData(HttpServletRequest request ) throws ExecutionException, InterruptedException, IOException {
        //if (true)return performanceService.getDashBoardData("welde.gesesse@gmail.com");
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
       // String acccessToken=loginData.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);

            //String emailtoken= "welde.gesesse@gmail.com";
            return performanceService.getDashBoardData(emailtoken);


//            //String accessToken = new String();
//            String use_id = new String();
//            if (tokenManagement.validateToken(accessToken) != "Invalid access token") {
//                String email = jwtTokenGenerator.decryptAccessToken(accessToken);
//                return performanceService.getDashBoardData(use_id);
//
//            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
    }
    @GetMapping("/leaderBoard")
    //@RequestBody String accessToken
    public  Object leaderBoard(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
//        String acccessToken=loginData.get("access_token");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
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
    public Object realTimeTestResult(HttpServletRequest request ,@RequestBody HashMap<String,Object> userResponse) throws ExecutionException, InterruptedException {
//    HashMap<String,String>data=new HashMap<>();

//      System.out.println(userResponse);
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        //String acccessToken= (String) userResponse.get("access_token");

        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String emailtoken=jwtTokenGenerator.decryptAccessToken(acccessToken);
        return practiceTestService.generateRealTimeResult(userResponse,emailtoken);
    }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");

    }
//    @GetMapping("/practiceTestResponse")
//    //@RequestBody String requestBody
//    public Object generateRealTimeResult(HttpServletRequest request,@RequestBody  HashMap<String,Object>userResponse) throws ExecutionException, InterruptedException {
//        String authorizationHeader = request.getHeader("Authorization");
//        String acccessToken = null;
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
//        }
//
//        else {
//            System.out.println("NO AUTHORIZATION");
//        }
//
//        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
//        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
//            return practiceTestService.generateRealTimeResult(userResponse);
//        }
//        System.out.println(userResponse);
//        practiceTestService.generateRealTimeResult(userResponse);
//        return 0;
//    }

}
