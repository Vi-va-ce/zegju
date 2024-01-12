package com.example.zegeju.Controller;

import com.example.zegeju.Domain.Student.Student;

import com.example.zegeju.Domain.Test.LogInParameters;
import com.example.zegeju.Service.StudentProfileService;
import com.example.zegeju.Service.StudentService;
import com.example.zegeju.Service.TwilioOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController


@RequestMapping(path = "api/v1/student")
public class StudentController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public StudentProfileService studentProfileService;
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
    @CrossOrigin(origins = "http://192.168.109.71:5173/SignupPage/otp/LoginPage")
    @PostMapping("/logIn")
    public Object logInStudent(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {

        String email = loginData.get("email");
        String password = loginData.get("password");
        return studentService.logInStudent(email,password);
    }

    @PutMapping("/changePassword")
    public String  updateStudentPassword(@RequestParam String email,@RequestParam String password) throws InterruptedException, ExecutionException {
//       Map<String, String> loginData
//        String email = loginData.get("email");
//        String password = loginData.get("password");
        System.out.println(email+" "+password);
        return studentService.updatePassword(email,password);//////i am gonna
    }
    @PostMapping("/forgetPasswordUserCheck")
    public Object forgetPasswordUserCheck(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {

        String email = loginData.get("email");
        int phoneNumber = Integer.parseInt(loginData.get("phoneNumber"));
        return studentService.forgetPasswordUserCheck(email,phoneNumber);
    }
    @PutMapping("/forgetPassword")
    public String  forgetpassword(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
        String email = loginData.get("email");
        String password= loginData.get("email");
        String otp=loginData.get("otp");
        return studentService.changePassword(email,password,otp);
    }/* ask the user to fill in email and phone number....then my end poing checks if there is a user with that email and check if the phone number matches with the email..
     and tells the frontend to proceed verifying the otp after otp verified ---> then the user will be on an interface where he can enter an new password and a confirm password
     */


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
    @PostMapping("/studentProfile")

        public Object  getStudentProfile(@RequestParam String email, @RequestParam String userId) throws InterruptedException, ExecutionException {
            return studentProfileService.createStudentProfile(email,userId);//// both of them needs to be the same
        }

}
