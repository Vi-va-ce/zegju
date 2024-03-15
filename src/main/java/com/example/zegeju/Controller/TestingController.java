package com.example.zegeju.Controller;

import com.example.zegeju.Domain.PracticeTests.Response;
import com.example.zegeju.Service.PerformanceService;
import com.example.zegeju.Service.PracticeTestService;
import com.example.zegeju.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/api/v9/student")
public class TestingController {
    @Autowired
    private PracticeTestService practiceTestService;
    @Autowired
    private TestService testService;
    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/Test")
    public Object getTTest(@RequestBody HashMap<String, Object> responses) throws ExecutionException, InterruptedException {
        //System.out.println(responses);
        String userId = (String) responses.get("userId");
        HashMap<String, Object> response = (HashMap<String, Object>) responses.get("response");

        return practiceTestService.updateDashboardData(userId, response);

    }

    @GetMapping("/dashBoardData")
    //@RequestBody String accessToken,@RequestParam String use_id
    public Object getDashBoardData(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException, IOException {
        //if (true)return performanceService.getDashBoardData("welde.gesesse@gmail.com");
        //String acccessToken=loginData.get("access_token");

//        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
//        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
//            String emailtoken=jwtTokenGenerator.decryptAccessToken(acccessToken);

        String emailtoken = loginData.get("emailToken");
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

    @GetMapping("/getPracticeTestt1")
    public Object getPracticeTestt1() throws ExecutionException, InterruptedException, IOException {

//        String test_id=loginData.get("test_id");practice_testOne
        String test_id = "practice_testOne";


        return testService.getTest(test_id);
    }
    @GetMapping("/diagnosticTestt")
    public Object getDiagnosingTest( ) throws ExecutionException, InterruptedException, IOException {
        //String acccessToken = loginData.get("access_token");
        //        String test_id=loginData.get("test_id");
        String test_id="diagnostic_testOne";

        return testService.getTest(test_id);




    }

}
