package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class MapService {
    //IDs
    //the ids are for the three of the test types
    String MATH_CALC="MathCalculator";
    String MATH_NO_CALC="MathNoCalculator";
    String WRITING_LANG="WritingAndLanguage";
    String EVIDENCE_BASED="EvidenceBasedReading";
    //
    String WHEREISTHEUSER="WhereIsTheUser";
    String NOTSTARTEDSTATUS="NotActivated";
    String ALLDONE="alldone";




    /* there will a condition here to know where the test taker is
    then the after determining where the test taker is the set the userMap collection

    {"user_id":"weldegebrial Belete",
      "map":{
                "diagnosticTest":{
                    "WhereIsTheUser":"MathCalculator"
                },
                "practiceTest":{
                "WhereIsTheUser:"NotStarted"
                },
                "finalTest":{
                "WhereIsTheUser:"NotStarted"
                }
      }
    }
    and return the where the user is which ia not "NotStarted" and "AllDone"
     */

    public Object registerMapData(HashMap<String,Object> questionCatagory,String userId) {
        HashMap<String,Object>map=new HashMap<>();
        map.put("user_id",userId);
       // map.put("map",questionCatagory);

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        //HashMap<String,Object> questionCatagoryObj= (HashMap<String, Object>) questionCatagory;
        //String testId= (String) questionCatagoryObj.get("test_id");
        Set<String> keySets=questionCatagory.keySet();
        ArrayList<String> arrayOfKeys= new ArrayList<>(keySets);
        for(String elements:arrayOfKeys){
            HashMap<String,Object>temp1= (HashMap<String, Object>) questionCatagory.get(elements);
            map.put(elements,temp1.get("WhereIsTheUser"));
        }

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("mapData").document(userId).set(map);
        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getMapData(String userId){
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference2 = zgjUfirestore.collection("mapData").document(userId);
        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();
        long correctNum= 0;
        HashMap<String,Object> map=new HashMap<>();
        try {
            DocumentSnapshot documentSnapshot = future2.get();
            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                HashMap<String, Object> linkedHashMap = new HashMap<>((Map) document);
                map=linkedHashMap;
                //correctNum= (Long) linkedHashMap.get("correct");

            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(map);
        return map;
    }

    public Object update (String userId, String sectionId,String testId) throws ExecutionException, InterruptedException {
//        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        DocumentReference documentReference2 = zgjUfirestore.collection("mapData").document(userId);
//        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();
//        long correctNum= 0;
//        HashMap<String,Object> map=new HashMap<>();
//        try {
//            DocumentSnapshot documentSnapshot = future2.get();
//            Object document;
//            if (documentSnapshot.exists()) {
//
//                // document exists//
//                document = documentSnapshot.toObject(Object.class);
//
//                HashMap<String, Object> linkedHashMap = new HashMap<>((Map) document);
//                map=linkedHashMap;
//                //correctNum= (Long) linkedHashMap.get("correct");
//
//            }} catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //System.out.println(map);
//        return map;
        //System.out.println(userId);
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("mapData");
        Query query = documentReference.whereEqualTo("user_id", userId);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student2;
        System.out.println(sectionId);
        HashMap<String,String>Response=new HashMap<>();
        if (!querySnapshot.isEmpty()) {

            // document exists
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            Object temp= document.toObject(Object.class);
            //System.out.println(temp);
            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
            String sectionIdtoBeUpdated= new String();
            if (sectionId.contains("eading")||sectionId.contains("READING"))
            {
                sectionIdtoBeUpdated=EVIDENCE_BASED;
            }
            else if (sectionId.contains("riting")||sectionId.contains("Writing")||sectionId.contains("WRITING"))
            {
                sectionIdtoBeUpdated=WRITING_LANG;
            }
            else if (sectionId.contains("NoCalculator")||sectionId.contains("noCalculator")||sectionId.contains("nocalculator"))
            {
                sectionIdtoBeUpdated=MATH_NO_CALC;
            }
            else {
                sectionIdtoBeUpdated=MATH_CALC;
            }

            if (document.exists()) {
                if (testId.contains("iagnostic")){
                    docRef.update("diagnosticTest", sectionIdtoBeUpdated);
            }
                else if (testId.contains("final")){
                    docRef.update("finalTest", sectionIdtoBeUpdated);
                }
                Response.put("status","Map Data updated successfully!");
                return (Response);

            } else {
                Response.put("status","user not found!");
                return (Response);
            }
        }
        Response.put("Status","user not found! ");
        return Response;
    }

    public Object updatePractice(String email_userId, String section_id, String test_id) {
        return null;
    }


    //public Object getUserStatus(String userId) {

       // return null;
    //}
}
