package com.example.zegeju.Controller;

import com.example.zegeju.Domain.Student.Student;

import com.example.zegeju.Domain.Test.LogInParameters;
import com.example.zegeju.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController


@RequestMapping(path = "api/v1/student")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST})
public class StudentController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public StudentProfileService studentProfileService;
    @Autowired
    public TwilioOtpService twilioOtpService;
    @Autowired
    public JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    public TokenManagement tokenManagement;


    @PostMapping("/registerUser")
    public String createStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
        System.out.println(student);
        return studentService.createStudent(student);
    }


    @GetMapping("/getUser")

    public Object getStudent(@RequestBody Map<String, Object> Data) throws InterruptedException, ExecutionException {
//        return studentService.getStudent(accessToken);}
        String acccessToken = (String) Data.get("access_token");
        //if(true)return acccessToken;
        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);

        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String userinfo = jwtTokenGenerator.decryptAccessToken(acccessToken);
            return studentService.getStudent(userinfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }

    }

    //    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST})
    @GetMapping("/logIn")
    public Object logInStudent(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {

        String email = loginData.get("email");
        String password = loginData.get("password");
        return ResponseEntity.ok(studentService.logInStudent(email, password));
    }

    @PutMapping("/changePassword")
    public Object updateStudentPassword(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {

        System.out.println(loginData);
        // if (true){return loginData;}
        String acccessToken = loginData.get("access_token");

        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            //
            String email = loginData.get("email");
            String password = loginData.get("password");
            System.out.println(emailtoken + " " + password);
            return studentService.updatePassword(emailtoken, password);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }


    }
    //////i am gonna

    @PostMapping("/forgetPasswordUserCheck")
    public Object forgetPasswordUserCheck(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {
        String acccessToken = loginData.get("access_token");

        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String email = loginData.get("email");
            int phoneNumber = Integer.parseInt(loginData.get("phoneNumber"));
            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);

            return studentService.forgetPasswordUserCheck(emailtoken, phoneNumber);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }


    }

    @PutMapping("/forgetPassword")
    public Object forgetpassword(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {

        String acccessToken = loginData.get("access_token");

        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {

            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            String password = loginData.get("password");
            String otp = loginData.get("otp");
            return studentService.changePassword(emailtoken, password, otp);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }


    }/* ask the user to fill in email and phone number....then my end poing checks if there is a user with that email and check if the phone number matches with the email..
     and tells the frontend to proceed verifying the otp after otp verified ---> then the user will be on an interface where he can enter an new password and a confirm password
     */


    @PutMapping("/deleteUser")
    public Object deleteStudent(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
        String acccessToken = loginData.get("access_token");
        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailToken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            return studentService.deleteStudent(emailToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }


    }

    @GetMapping("/VerifyOtp")
    public String verifyStudent(@RequestParam String otp) {
        return twilioOtpService.verifyPhoneNumber(otp);
    }

    @GetMapping("/homepage")
    public Object endPointTesting(@RequestBody String accessToken) {
        return jwtTokenGenerator.extractClaims(accessToken);
    }

    @PostMapping("/studentProfile")

    public Object getStudentProfile(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
        System.out.println(loginData);
        // if (true){return loginData;}
        String acccessToken = loginData.get("access_token");
        String userId = new String();
        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            userId = emailtoken;
            return studentProfileService.createStudentProfile(emailtoken, userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }
        //// both of them needs to be the same
    }

    @PostMapping("/uploadPicture")
    public Object uploadVerificationImage(HttpServletRequest request) throws IOException {
        String acccessToken = request.getHeader("Authorization");
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            return studentService.uploadVerificationImage(file, emailtoken);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }

    }
}