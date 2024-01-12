package com.example.zegeju.Controller;


import com.example.zegeju.Service.PerformanceService;
import com.example.zegeju.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping(path = "/api/v2/student")

public class TestController {


    @Autowired
    private TestService testService;

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/getPracticeTest")
    public Object getTest(@RequestParam String test_id) throws ExecutionException, InterruptedException, IOException {
        return testService.getTest(test_id);

    }
    @GetMapping("/getFinalTest")
    public Object getFinalTest(@RequestParam String test_id) throws ExecutionException, InterruptedException, IOException {
        return testService.getTest(test_id);

    }

    @GetMapping("/diagnosticTest")
    public Object getDiagnosingTest( ) throws ExecutionException, InterruptedException, IOException {
        return testService.getTest("diagnostic_testOne");

    }
    @PostMapping("/Test")
    public String getTTest() {
        return "am here";

    }

    @PostMapping("/registerTest")
    public Object putTest(@RequestBody Object test1) {

        return testService.registerTest(test1);
    }
    @PostMapping("/registerAnswer")
    public Object registerAnswer(@RequestBody Object ans){

        return testService.registerAnswer(ans);
    }/// this was for testing puposes

    @PostMapping("/userResponse")
    public  Object userResponse(@RequestBody HashMap<String,Object> userResponse){
        return performanceService.generateResult(userResponse);
    }
    @GetMapping("/dashBoardData")
    public Object getDashBoardData(@RequestParam String use_id) throws ExecutionException, InterruptedException, IOException {
        return performanceService.getDashBoardData(use_id);

    }
    @GetMapping("/leaderBoard")
    public  Object leaderBoard(){
        return performanceService.generateLeaderBoard();
    }
}
