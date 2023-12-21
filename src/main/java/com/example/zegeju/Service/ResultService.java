package com.example.zegeju.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class ResultService {
    public Object getStudentAnalysis(String user_id) {

        return null;
    }

    public Object registerCatagory(Object questionCatagory) {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        HashMap<String,Object>questionCatagoryObj= (HashMap<String, Object>) questionCatagory;
        String testId= (String) questionCatagoryObj.get("test_id");

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("questionsCatagory").document(testId).set(questionCatagoryObj);
        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
