package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.StudentProfile;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class StudentProfileService {
    public String createStudentProfile(StudentProfile studentProfile){
        Firestore zegejuDatabase = FirestoreClient.getFirestore();

     return "profile Created for" + studentProfile;
    }
}
