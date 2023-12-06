package com.example.zegeju.Controller;


import com.example.zegeju.Service.ResultService;
import com.example.zegeju.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v3/student")


public class ResultController {
    @Autowired
    public ResultService resultService;
    @GetMapping("/getTest")
    public Object getTest(@RequestParam String test_id) throws ExecutionException, InterruptedException, IOException {
        return resultService.getStudentAnalysis(test_id);

    }
}
