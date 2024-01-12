package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.Student;
import com.example.zegeju.Domain.Test.LogInParameters;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import com.google.firebase.cloud.FirestoreClient;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


@Service
public class   StudentService {
    @Autowired
    private TwilioOtpService twilioOtpService;



    public String createStudent(Student stud) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        int otp = Integer.parseInt(RandomStringUtils.randomNumeric(6));
        stud.setOtp(otp);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                stud.getPhone_no(),
//                60,
//                TimeUnit.SECONDS,
//                Executors.newScheduledThreadPool(1),
//                callbacks);
      //  twilioOtpService.sendOTP("+251"+String.valueOf(stud.getPhoneNumber()),String.valueOf(stud.getOtp()));
        //        FirebaseToken token = firebaseAuth.generatePhoneAuthCode(xphoneNumber);
//        String verificationId = token.getUid();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        String email = stud.getEmail();
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student;
        if (querySnapshot.isEmpty()) {

            ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("student_user").document(stud.getFirstName()).set(stud);
            return collectionApifuture.get().getUpdateTime().toString();
        }

        return "Sorry a user with this email already exists";
    }
//    import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.MediaType;5
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
    


    public Student getStudent(String email) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student;
        if (querySnapshot.getDocuments().size() > 0) {

            // document exists

            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

            if (document.exists()) {

                // convert to object
                student = document.toObject(Student.class);
                return student;
            }

        }

        return null;
    }

    public String updatePassword(String email,String password) throws ExecutionException, InterruptedException {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student;
        if (!querySnapshot.isEmpty()) {

            // document exists
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

            if (document.exists()) {

                docRef.update("password", password);

                System.out.println("User password updated successfully!");
            } else {
                System.out.println("User not found!");
            }
            return null;
        }
        return null;
    }

    public String deleteStudent(String email) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();


        if (!querySnapshot.isEmpty()) {

            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

            ApiFuture<WriteResult> deleteResult = docRef.delete();

            deleteResult.get();

            return "User successfully deleted!";
        }

        return "no user found";

    }

    public Object logInStudent(String email,String password) throws ExecutionException, InterruptedException {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        Query query = zgjUfirestore.collection("student_user")
                .whereEqualTo("email", email);

        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot results = future.get();


        if (results.isEmpty()) {
            return "No Student is found with the email";

        }

        DocumentSnapshot doc = results.getDocuments().get(0);

        String storedPassword = doc.getString("password");

        if (!storedPassword.equals(password)) {

            return "incorrect password" ;
        }
        else {
            String login="login Successful";
            String token=generateToken(email+password);
            HashMap<String,String>logInToken= new HashMap<>();
            logInToken.put("log_in_Status", login);
            logInToken.put("user_token", token);
            System.out.println(logInToken);
            return logInToken;
        }

//       return generateToken(email+password);
    }

    public static String generateToken(String subject){
        final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";
        final long EXPIRATION_TIME = 86400000;
    // 24 hours
        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }


    public static String decryptToken(String token) {
        final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            // Token has expired
            // Handle accordingly
        } catch (JwtException e) {
            // Invalid token
            // Handle accordingly
        }

        return null;
    }

    public String changePassword(String email, String password, String otp) throws ExecutionException, InterruptedException {
            //otp then new password
        if(otp=="verified"){

            Firestore zgjUfirestore = FirestoreClient.getFirestore();
            CollectionReference documentReference = zgjUfirestore.collection("student_user");
            Query query = documentReference.whereEqualTo("email", email);
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot querySnapshot = future.get();
            Student student2;
            if (!querySnapshot.isEmpty()) {

                // document exists
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

                if (document.exists()) {

                    docRef.update("password", password);

                    System.out.println("Password Changed successfully!");
                } else {
                    System.out.println("user not found!");
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public Object forgetPasswordUserCheck(String email, int phoneNumber) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student2;
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            if(document.exists()){
                student2 = document.toObject(Student.class);
                if(phoneNumber==student2.getPhoneNumber()){
                    return "user with that email and phonenumber exists";
                }
                else return "the phone number entered doesn't match";

            }
            else return "There is no user with this email. Please sign up!";
        }
        else return "There is no user with this email. Please sign up!";

    }
//
}