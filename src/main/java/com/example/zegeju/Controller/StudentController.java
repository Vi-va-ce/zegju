package com.example.zegeju.Controller;

import com.example.zegeju.Domain.Student.Student;

import com.example.zegeju.Service.StudentService;
import com.example.zegeju.Service.TwilioOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/student")
public class StudentController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public TwilioOtpService twilioOtpService;


    @PostMapping("/registerUser")
    public String  createStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
        return studentService.createStudent(student);
    }


    @GetMapping("/getUser")
    public Student  getStudent(@RequestParam String email) throws InterruptedException, ExecutionException {
        return studentService.getStudent(email);
    }
    @GetMapping("/logIn")
    public String logInStudent(@RequestParam String email, @RequestParam String password) throws ExecutionException, InterruptedException {
        return studentService.logInStudent(email,password);
    }

    @PutMapping("/updateUser")
    public String  updateStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
        return studentService.updateStudent(student);
    }

    @PutMapping("/deleteUser")
    public String  deleteStudent(@RequestParam  String email) throws InterruptedException, ExecutionException {
        
        return studentService.deleteStudent(email);
    }
    @GetMapping("/VerifyOtp")
    public String verifyStudent(@RequestParam String otp){
        return twilioOtpService.verifyPhoneNumber(otp);
    }
    @GetMapping("/test")
    public ResponseEntity<String> endPointTesting(){return ResponseEntity.ok("it isworking");}
}
