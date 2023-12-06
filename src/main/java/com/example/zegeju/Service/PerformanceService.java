package com.example.zegeju.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PerformanceService {
    public Object generateResult(HashMap<String,Object> userResponse) {
        /* Generate  dashboard DATA*/
//        saveDashboardData(userResponse);
        /* USER RESPONSE DATA*/
        HashMap<String,Object>responseData= (HashMap<String, Object>) userResponse;

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("answer").document((String) responseData.get("test_id"));
        String documentId = "scoring";
        DocumentReference scoringdocumentRef = zgjUfirestore.collection("scoring_data").document(documentId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = scoringdocumentRef.get();
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        HashMap<String,Object> scoring = null;
        try{
            DocumentSnapshot documentSnap = documentSnapshotApiFuture.get();
            if (documentSnap.exists()){
                Object scoringDoc = documentSnap.toObject(Object.class);
                scoring= (HashMap<String,Object>) scoringDoc;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
        DocumentSnapshot documentSnapshot = future.get();


        Object document;
        if (documentSnapshot.exists()) {

            // document exists//
            document = documentSnapshot.toObject(Object.class);

            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>((Map) document);



            ArrayList<Object>answerSections= (ArrayList<Object>) linkedHashMap.get("sections");

            ArrayList<Object>userResponseSections= (ArrayList<Object>) userResponse.get("sections");


            HashMap<String,Object>answerSection1=(HashMap<String,Object>) answerSections.get(0);
            HashMap<String,Object>answerSection2=(HashMap<String,Object>) answerSections.get(1);
            HashMap<String,Object>answerSection3=(HashMap<String,Object>) answerSections.get(2);


            HashMap<String,Object>responseSection1=(HashMap<String,Object>) userResponseSections.get(0);///reading section
            HashMap<String,Object>responseSection2=(HashMap<String,Object>) userResponseSections.get(1);///writing section
            HashMap<String,Object>responseSection3=(HashMap<String,Object>) userResponseSections.get(2);///math section

            ArrayList<HashMap<String,Object>>answerQuestions1= (ArrayList<HashMap<String, Object>>) answerSection1.get("questions");
            ArrayList<HashMap<String,Object>>responseQuestions1= (ArrayList<HashMap<String, Object>>) responseSection1.get("user_responses");

            ArrayList<HashMap<String,Object>>answerQuestions2= (ArrayList<HashMap<String, Object>>) answerSection2.get("questions");
            ArrayList<HashMap<String,Object>>responseQuestions2= (ArrayList<HashMap<String, Object>>) responseSection2.get("user_responses");

            ArrayList<HashMap<String,Object>>answerQuestionsMath= (ArrayList<HashMap<String,Object>>) answerSection3.get("question_group");
            ArrayList<HashMap<String,Object>>responseQuestionsMath= (ArrayList<HashMap<String,Object>>) responseSection3.get("question_groups");
//            if(true){
//                return  answerSection3;
//            }

            ArrayList<HashMap<String,Object>>answerQuestions3= (ArrayList<HashMap<String,Object>>) answerQuestionsMath.get(0).get("questions");///Math calculator section
            ArrayList<HashMap<String,Object>>responseQuestions3= (ArrayList<HashMap<String,Object>>) responseQuestionsMath.get(0).get("user_responses");

            ArrayList<HashMap<String,Object>>answerQuestions4= (ArrayList<HashMap<String,Object>>) answerQuestionsMath.get(0).get("questions");///Math No calculator section
            ArrayList<HashMap<String,Object>>responseQuestions4= (ArrayList<HashMap<String,Object>>) responseQuestionsMath.get(0).get("user_responses");


            HashMap<String ,Long> section_Scores= new HashMap<>();
            HashMap<String , Object> total_Scores=new HashMap<>();
            HashMap<String ,Long> math_Scores= new HashMap<>();
            int size=0;
            long reading_score=0;
            for (int i =0 ; i < responseQuestions1.size(); i++){
                if (responseQuestions1.get(i).get("response").equals(answerQuestions1.get(i).get("answer")))
                 {
                    reading_score++;
                }
                else {
                    size++;
                    continue;

                }
            }
            String readingScoreId="Reading_Section_Score";
            section_Scores.put(readingScoreId,reading_score);


           // HashMap<String ,Long> writing_section_Score= new HashMap<>();

            long writing_score=0;

            for (int i =0 ; i < responseQuestions2.size(); i++){
                if (responseQuestions2.get(i).get("response").equals(answerQuestions2.get(i).get("answer")))
                {
                    writing_score++;
                }
                else {
                    continue;
                }
            }

            String writeingScoreId="Writing_Section_Score";
            section_Scores.put(writeingScoreId,writing_score);

            long math_calculator_score=0;

            for (int i =0 ; i < responseQuestions3.size(); i++){
                if (responseQuestions3.get(i).get("response").equals(answerQuestions3.get(i).get("answer")))
                {
                    math_calculator_score++;
                }
                else {

                    continue;
                }
            }
            String math_calculator_Id="Math_Calculator_Score";
            math_Scores.put(math_calculator_Id,math_calculator_score);
            section_Scores.put(math_calculator_Id,math_calculator_score);


            long math_no_calculator_score=0;

            for (int i =0 ; i < responseQuestions4.size(); i++){
                if (responseQuestions4.get(i).get("response").equals(answerQuestions4.get(i).get("answer")))
                {
                    math_no_calculator_score++;
                }
                else {

                    continue;
                }
            }
            String math_no_calculator_Id="Math_No_Calculator_Score";
            math_Scores.put(math_no_calculator_Id,math_no_calculator_score);

            section_Scores.put(math_no_calculator_Id,math_no_calculator_score);

            HashMap<String,Object>response= new HashMap<>();
            HashMap<String,Object>total_wrong_answer=new HashMap<>();

            total_wrong_answer.put("score",math_calculator_score+math_no_calculator_score+reading_score+writing_score);
            HashMap<String,Object>total_right_answer=new HashMap<>();
            HashMap<String,Object>mainSections= new HashMap<>();

            total_Scores.put("mainSections",mainSections);

            response.put("user_id",userResponse.get("use_id"));
            response.put("section-scores",section_Scores);

            return scoring;

        }
            return "no test is found";
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /// return (String)" "+(String) responseData.get("use_id");




        //// make the calling dashboard data generation and registration



        ////



    }
    public Object saveDashboardData(Object userResponse) {
        return null;
    }

}
