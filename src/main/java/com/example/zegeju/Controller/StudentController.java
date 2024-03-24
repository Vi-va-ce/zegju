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
//    @Autowired
//    public TwilioOtpService twilioOtpService;
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
    public Object getStudent(HttpServletRequest request ,@RequestBody Map<String, Object> Data) throws InterruptedException, ExecutionException {
//  returnstudentService.getStudent(accessToken);}
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
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
    @PostMapping("/logIn")
    public Object logInStudent(@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {

        String email = loginData.get("email");
        String password = loginData.get("password");
        return ResponseEntity.ok(studentService.logInStudent(email, password));
    }

    @PutMapping("/changePassword")
    public Object updateStudentPassword(HttpServletRequest request,@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {

        System.out.println(loginData);
        // if (true){return loginData;}
        //String acccessToken = loginData.get("access_token");
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }

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
    public Object forgetPasswordUserCheck(@RequestHeader("authorization") String authorizationHeader
,@RequestBody Map<String, String> loginData) throws ExecutionException, InterruptedException {
        //String acccessToken = loginData.get("access_token");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }

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

    @PostMapping("/forgetPassword")
    public Object forgetpassword(@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
       // String email = loginData.get("email");
        long phoneNumber= Long.parseLong(loginData.get("phoneNumber"));
        System.out.println(phoneNumber);
//        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
//        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
//
//            String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
//
        String password = loginData.get("password");
        String otp = "verified";
        if (true){
            return studentService.changePassword(phoneNumber, password, otp);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }


    }/* ask the user to fill in email and phone number....then my end poing checks if there is a user with that email and check if the phone number matches with the email..
     and tells the frontend to proceed verifying the otp after otp verified ---> then the user will be on an interface where he can enter an new password and a confirm password
     */


    @PutMapping("/deleteUser")
    public Object deleteStudent(HttpServletRequest request,@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
        //String acccessToken = loginData.get("access_token");
        String acccessToken = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
        if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
            String emailToken = jwtTokenGenerator.decryptAccessToken(acccessToken);
            return studentService.deleteStudent(emailToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }
    }

    //@GetMapping("/VerifyOtp")
   // public String verifyStudent(@RequestParam String otp) {
//        return twilioOtpService.verifyPhoneNumber(otp);
//    }

    @GetMapping("/homepage")
    public Object endPointTesting(@RequestBody String accessToken) {
        return jwtTokenGenerator.extractClaims(accessToken);
    }

    @PostMapping("/studentProfile")

    public Object getStudentProfile(HttpServletRequest request,@RequestBody Map<String, String> loginData) throws InterruptedException, ExecutionException {
        System.out.println(loginData);
        // if (true){return loginData;}
       // String acccessToken = loginData.get("access_token");
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
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
    public Object uploadVerificationImage(@RequestParam("photo") MultipartFile file, @RequestHeader String authorizationHeader ) throws IOException {
//        String acccessToken = request.getHeader("Authorization");
       // System.out.println(request);
        //String authorizationHeader=request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        System.out.println();
       String acccessToken = null;
        String accessToken= authorizationHeader;
//        System.out.println(acccessToken);
//        System.out.println();
//        System.out.println(file);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NO AUTHORIZATION");
        }
        acccessToken=accessToken;
       // MultipartFile file = ((MultipartHttpServletRequest) request).getFile("photo");
      String decrpt = jwtTokenGenerator.decryptToken(acccessToken);
      if (decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))) {
        String emailtoken = jwtTokenGenerator.decryptAccessToken(acccessToken);
           // String emailtoken="weldegebrial.gesesse@gmail.com";
            return studentService.uploadVerificationImage(file, emailtoken);

     } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid  token") + "," + jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
     }

    }
    @PostMapping("/getPic")
    public Object getVerificationImg(@RequestBody String email) throws IOException {

        return studentService.getVerificationImg(email);
    }
//    @PostMapping("/uploadPic")
//    public Object uploadVerificationImg(HttpServletRequest request) throws IOException {
//        String emailtoken="weldegebrial.gesesse@gmail.com";
//        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("photo");
//        return studentService.uploadVerificationImage(file, emailtoken);
//    }

}