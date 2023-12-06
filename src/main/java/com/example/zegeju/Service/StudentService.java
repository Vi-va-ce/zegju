package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        twilioOtpService.sendOTP("+251"+String.valueOf(stud.getPhone_no()),String.valueOf(stud.getOtp()));
        //        FirebaseToken token = firebaseAuth.generatePhoneAuthCode(xphoneNumber);
//        String verificationId = token.getUid();


        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("student_user").document(stud.getFname()).set(stud);
        return collectionApifuture.get().getUpdateTime().toString();

    }

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

    public String updateStudent(Student stud) {

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

            return "Document successfully deleted!";
        }

        return "no user found";

    }

    public String logInStudent(String email, String password) throws ExecutionException, InterruptedException {


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
            return "login Successful";
        }

        // generate and return JWT token

//        return generateJwt(doc);
    }
//
}