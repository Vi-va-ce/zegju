package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.Student;
import com.example.zegeju.Domain.Student.StudentProfile;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
@Service
public class StudentProfileService {
    public Object createStudentProfile(String email,String use_id) throws ExecutionException, InterruptedException { //the userId and the email should be the same
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student = new Student();
        if (querySnapshot.getDocuments().size() > 0) {

            // document exists

            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

            if (document.exists()) {

                // convert to object
                student = document.toObject(Student.class);

            }

        }


        String userId= student.getEmail();
        String picture= String.valueOf(student.getFirstName().toString().indexOf(0));
        String fName= student.getFirstName();
        String lName= student.getLastName();
        String emailProf= student.getEmail();
        String phonenumber= "+251"+String.valueOf(student.getPhoneNumber());
        HashMap<String,Object>dashboardScore=new HashMap<>();
        try {
            dashboardScore= (HashMap<String, Object>) getTestResultData(use_id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        HashMap<String,Object> scores = new HashMap<String,Object>();

        HashMap<String,Object> SATenglish= (HashMap<String, Object>) dashboardScore.get("sat_english");
        Object SATenglishScore= SATenglish.get("score");/// needs to be checked
        HashMap<String,Object> SATmath= (HashMap<String, Object>) dashboardScore.get("sat_math");
        Object  SATmathScore= (Long) SATmath.get("score");/// needs to be checked
        Object totalScore= (HashMap<String, Object>) dashboardScore.get("scoreInTheCircle");

        scores.put("sat_english",SATenglishScore);
        scores.put("sat_math",SATmathScore);
        scores.put("average", totalScore);

        HashMap<String,Object> studentProfile = new HashMap<String,Object>();
        studentProfile.put("useId",userId);
        studentProfile.put("picture",picture);
        studentProfile.put("firstName",fName);
        studentProfile.put("lastName",lName);
        studentProfile.put("email",emailProf);
        studentProfile.put("phoneNumber",phonenumber);
        studentProfile.put("scores",scores);

     return  studentProfile;
    }



    public Object getTestResultData(String use_id) throws ExecutionException, InterruptedException, IOException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("testResultData").document(use_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();


        Object document;
        if (documentSnapshot.exists()) {

            // document exists

            document = documentSnapshot.toObject(Object.class);
            HashMap<String, Object> hashMapData = (HashMap<String, Object>) document;

            return hashMapData;


        }

        return "no dashboard data is found";
    }
    public Student getStudentProfile(String email) throws ExecutionException, InterruptedException {
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


    ////////////profile



}
