package com.example.zegeju.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class PracticeTestService {
    public Object getPracticeTest(String test_id) throws ExecutionException, InterruptedException, IOException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("tests").document(test_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();


        String correct_answer="Correct answer";
        String wrong_answer="Wrong answer";
        String probable_high="Probable high score";

       /// ------------------> session creation

        long correctAnswer=0;
        long wrongAnswer=0;
        long probableHigh=0;

        /*
            for the whole section there is  a correct answer, right answer,
                total correct answer out of 33*2(reading and writing) pluse 27*2(math calc and math no calc)
                wrong answer out of 33(reading and writing) and 27(math calc and math no calc)

                progress bar fills up per every section----This shall be on the frontend

                probable high scores--> (score++ )-10)*(100/120)



            the
        ----->i need to make an algorithm that can scale
       the structure of the additional information that will be sent in addition to the practice test question
            {
            coo

            }

        */

        Object document;
        if (documentSnapshot.exists()) {

            // document exists

            document = documentSnapshot.toObject(Object.class);
            HashMap<String, Object> hashMapData = (HashMap<String, Object>) document;

            return hashMapData;


        }

        return "no test is found";
    }
    public Object registerPracticeTest(Object test) {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        Test test1= (Test)test;
        HashMap<String, Object> hashMapData = (HashMap<String, Object>) test;
        String testIdd = hashMapData.keySet().iterator().next();
        String test_Id = (String) hashMapData.get(testIdd);

        String ttt = "diagnosticTestOne";
        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("tests").document(test_Id).set(hashMapData);


        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Object registerAnswer(Object answer){

        HashMap<String, Object> hashMapData = (HashMap<String, Object>) answer;
        String testIdd= hashMapData.keySet().iterator().next();
        String test_Id= (String) hashMapData.get(testIdd);
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("answer").document(test_Id).set(answer);

        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
