package com.example.zegeju.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PerformanceService {
    private ConversionService conversionService;

    @Autowired
    private SectionsPerformanceService sectionsPerformanceService;
    public Object generateResult(HashMap<String,Object> userResponses) throws ExecutionException, InterruptedException {
        /* Generate  dashboard DATA*/
//        saveDashboardData(userResponse);

        /* USER RESPONSE DATA*/
        // if the data.
        ////
        //System.out.println(userResponses);
       // if (true)return (sectionsPerformanceService.generateResult(userResponses));
        HashMap<String,Object> userResponse= (HashMap<String, Object>) sectionsPerformanceService.generateResult(userResponses);
        //System.out.println(userResponse);
        HashMap<String,Object>responseData= userResponse;

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("answer").document((String) responseData.get("test_id"));
        String documentId = "scoring";//// these should be changed into a specific id ... like scoring1 or test!Scoring1
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

            DocumentReference questionsCatagory = zgjUfirestore.collection("questionsCatagory").document((String) userResponse.get("test_id"));
            ApiFuture<DocumentSnapshot> documentSnapApiFuture = questionsCatagory.get();
            HashMap<String,Object> questionsCatagoryObject = null;

            HashMap<String,Object> question_catagories = null;


//            Object doc;
//            if (documentSnap.exists()) {
//
//                // document exists
//
//                doc = documentSnap.toObject(Object.class);
//                questionsCatagoryObject = (HashMap<String, Object>) doc;
//
//                return doc;
//
//
//            }
            ///// the data that is needed to make the dashborad analysis
            HashMap<String,Object> dashboardData = new HashMap<>();

            HashMap<String,Object> readingAndWriting  = new HashMap<>();
            String Reading_and_Writing_subscores="Reading_and_Writing_subscores";

            HashMap<String,Object> commandOfEvidence = new HashMap<>();
            String command_of_evidence="command_of_evidence";
            long comnd_of_evdnce= 0;

            HashMap<String,Object> wordsInContext = new HashMap<>();
            String words_in_context="words_in_context";
            long wrds_in_contxt= 0;

            HashMap<String,Object> expressionOfIdeas = new HashMap<>();
            String expression_of_Ideas="expression_of_Ideas";
            long exprssion_of_Ideas= 0;


            HashMap<String,Object> Math_subscore  = new HashMap<>();
            String Math_subscores="Math_subscores";

            HashMap<String,Object> heartOfAlgebra = new HashMap<>();
            String heart_of_algebra="heart_of_algebra";
            long hart_of_algebra= 0;

            HashMap<String,Object> problemSolvingAnalysis = new HashMap<>();
            String problem_solving_analysis="problem_solving_analysis";
            long prblm_solvng_analysis= 0;

            HashMap<String,Object> passportAdvancedMath = new HashMap<>();
            String passport_advanced_math="passport_advanced_math";
            long pasport_advanced_math= 0;


            HashMap<String,Object> crossTest= new HashMap<>();
            String cross_test="cross_test";

            HashMap<String,Object> scienceScore = new HashMap<>();
            String science_score="science_score";
            long science_scor= 0;

            HashMap<String,Object> historyScore = new HashMap<>();
            String history_score="history_score";
            long history_scor= 0;

            try{
                DocumentSnapshot documentSnap2 = documentSnapApiFuture.get();
                if (documentSnap2.exists()){
                    Object questionsCatagoryObj = documentSnap2.toObject(Object.class);
                    questionsCatagoryObject= (HashMap<String,Object>) questionsCatagoryObj;

                    question_catagories= (HashMap<String, Object>) questionsCatagoryObject.get("question_catagories");
//                    String question_id= (String) responseQuestions1.get(0).get("question_id");
//                    HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores);
//
//                    String questionCatagoryobj= (String) questionCatagory.get(question_id);
//                   return questionCatagoryobj.equals("command_of_evidence");

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            HashMap<String ,Long> section_Scores= new HashMap<>();
            HashMap<String , Object> total_Scores=new HashMap<>();
            HashMap<String ,Long> math_Scores= new HashMap<>();
            int size=0;
            long reading_score=0;

            for (int i =0 ; i < responseQuestions1.size(); i++){
                String answerID= (String) answerQuestions1.get(i).get("answer");
                String responseId= (String) responseQuestions1.get(i).get("response");
                String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                 {
                    reading_score++;
                    String question_id= (String) responseQuestions1.get(i).get("question_id");
                     HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores);

                      String questionCatagoryobj= (String) questionCatagory.get(question_id);
                    if(questionCatagoryobj.equals("command_of_evidence")){
                        comnd_of_evdnce++;
                    }
                    else if(questionCatagoryobj.equals("words_in_context")){
                        wrds_in_contxt++;
                    }
                    else if(questionCatagoryobj.equals("expression_of_Ideas")){
                        exprssion_of_Ideas++;
                    }
                    else if(questionCatagoryobj.equals("science_score")){
                        science_scor++;
                    }
                    else if(questionCatagoryobj.equals("history_score")){
                        history_scor++;
                    }



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
                String answerID= (String) answerQuestions2.get(i).get("answer");
                String responseId= (String) responseQuestions2.get(i).get("response");
                String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                {
                    writing_score++;
                    String question_id= (String) responseQuestions2.get(i).get("question_id");
                    String Reading_and_Writing_subscores2= "Reading_and_Writing_subscores2";
                    HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores2);
                    String questionCatagoryobj= (String) questionCatagory.get(question_id);
                    if(questionCatagoryobj.equals("command_of_evidence")){
                        comnd_of_evdnce++;
                    }
                    else if(questionCatagoryobj.equals("words_in_context")){
                        wrds_in_contxt++;
                    }
                    else if(questionCatagoryobj.equals("expression_of_Ideas")){
                        exprssion_of_Ideas++;
                    }
                    else if(questionCatagoryobj.equals("science_score")){
                        science_scor++;
                    }
                    else if(questionCatagoryobj.equals("history_score")){
                        history_scor++;
                    }

                }
                else {
                    continue;
                }
            }

            String writeingScoreId="Writing_Section_Score";
            section_Scores.put(writeingScoreId,writing_score);

            long math_calculator_score=0;

            for (int i =0 ; i < responseQuestions3.size(); i++){
                String answerID= (String) answerQuestions3.get(i).get("answer");
                String responseId= (String) responseQuestions3.get(i).get("response");
                String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                {
                    math_calculator_score++;
                    String question_id= (String) responseQuestions3.get(i).get("question_id");

                    HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Math_subscores);
                    String questionCatagoryobj= (String) questionCatagory.get(question_id);
                    if(questionCatagoryobj.equals("heart_of_algebra")){
                        hart_of_algebra++;
                    }
                    else if(questionCatagoryobj.equals("problem_solving_analysis")){
                        prblm_solvng_analysis++;
                    }
                    else if(questionCatagoryobj.equals("passport_advanced_math")){
                        pasport_advanced_math++;
                    }
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
                String answerID= (String) answerQuestions4.get(i).get("answer");
                String responseId= (String) responseQuestions4.get(i).get("response");
                String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                {
                    math_no_calculator_score++;
                    String question_id= (String) responseQuestions4.get(i).get("question_id");
                    String Math_subscores2="Math_subscores2";
                    HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Math_subscores2);
                    String questionCatagoryobj= (String) questionCatagory.get(question_id);

                    if(questionCatagoryobj.equals("heart_of_algebra")){
                        hart_of_algebra++;
                    }
                    else if(questionCatagoryobj.equals("problem_solving_analysis")){
                        prblm_solvng_analysis++;
                    }
                    else if(questionCatagoryobj.equals("passport_advanced_math")){
                        pasport_advanced_math++;
                    }
                }
                else {

                    continue;
                }
            }
            //////////dash board score generating phase

            commandOfEvidence.put(command_of_evidence,comnd_of_evdnce);

            wordsInContext.put(words_in_context,wrds_in_contxt);
            expressionOfIdeas.put(expression_of_Ideas,exprssion_of_Ideas);

            readingAndWriting.put(command_of_evidence,comnd_of_evdnce);
            readingAndWriting.put(words_in_context,wrds_in_contxt);
            readingAndWriting.put(expression_of_Ideas,exprssion_of_Ideas);



            heartOfAlgebra.put(heart_of_algebra,hart_of_algebra);
            problemSolvingAnalysis.put(problem_solving_analysis,prblm_solvng_analysis);
            passportAdvancedMath.put(passport_advanced_math,pasport_advanced_math);

            Math_subscore.put(heart_of_algebra,hart_of_algebra);
            Math_subscore.put(problem_solving_analysis,prblm_solvng_analysis);
            Math_subscore.put(passport_advanced_math,pasport_advanced_math);

            scienceScore.put(science_score,science_scor);
            historyScore.put(history_score,history_scor);

            crossTest.put(science_score,science_scor);
            crossTest.put(history_score,history_scor);

            dashboardData.put(Reading_and_Writing_subscores,readingAndWriting);
            dashboardData.put(Math_subscores,Math_subscore);
            dashboardData.put(cross_test,crossTest);
            dashboardData.put("use_id",userResponse.get("use_id"));


            //// The test row result

            String math_no_calculator_Id="Math_No_Calculator_Score";
            math_Scores.put(math_no_calculator_Id,math_no_calculator_score);
            section_Scores.put(math_no_calculator_Id,math_no_calculator_score);
            HashMap<String,Object> testData = new HashMap<>();

            /////user id for the test result generation
            String userid= (String) userResponse.get("use_id");
            testData.put("sectionScores",section_Scores);

            HashMap<String,Object>response= new HashMap<>();
            HashMap<String,Object>total_wrong_answer=new HashMap<>();

            total_wrong_answer.put("score",math_calculator_score+math_no_calculator_score+reading_score+writing_score);
            HashMap<String,Object>total_right_answer=new HashMap<>();
            HashMap<String,Object>mainSections= new HashMap<>();

            total_Scores.put("mainSections",mainSections);

            response.put("use_id",userResponse.get("use_id"));
            response.put("section-scores",section_Scores);


            if (true) generateDashboardData(dashboardData);
            generateSectionTestScore(section_Scores,userid,scoring);
            return "Response Submitted";


        }
            return "no test is found";
        } catch (ExecutionException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /// return (String)" "+(String) responseData.get("use_id")

        /// make the calling dashboard data generation and registration

        ///

    }
    public Object generateDashboardDataForPracticeTestResult(HashMap<String,Object> userResponse) throws ExecutionException, InterruptedException {
        /* Generate  dashboard DATA*/
//        saveDashboardData(userResponse);

        /* USER RESPONSE DATA*/
        // if the data.
        ////
        //System.out.println(userResponses);
        // if (true)return (sectionsPerformanceService.generateResult(userResponses));
        //HashMap<String,Object> userResponse= (HashMap<String, Object>) sectionsPerformanceService.generateResult(userResponses);
        //System.out.println(userResponse);

        HashMap<String,Object>responseData= userResponse;

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("answer").document((String) responseData.get("test_id"));
        String documentId = "scoring";//// these should be changed into a specific id ... like scoring1 or test!Scoring1
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

                DocumentReference questionsCatagory = zgjUfirestore.collection("questionsCatagory").document((String) userResponse.get("test_id"));
                ApiFuture<DocumentSnapshot> documentSnapApiFuture = questionsCatagory.get();
                HashMap<String,Object> questionsCatagoryObject = null;

                HashMap<String,Object> question_catagories = null;


//            Object doc;
//            if (documentSnap.exists()) {
//
//                // document exists
//
//                doc = documentSnap.toObject(Object.class);
//                questionsCatagoryObject = (HashMap<String, Object>) doc;
//
//                return doc;
//
//
//            }
                ///// the data that is needed to make the dashborad analysis
                HashMap<String,Object> dashboardData = new HashMap<>();

                HashMap<String,Object> readingAndWriting  = new HashMap<>();
                String Reading_and_Writing_subscores="Reading_and_Writing_subscores";

                HashMap<String,Object> commandOfEvidence = new HashMap<>();
                String command_of_evidence="command_of_evidence";
                long comnd_of_evdnce= 0;

                HashMap<String,Object> wordsInContext = new HashMap<>();
                String words_in_context="words_in_context";
                long wrds_in_contxt= 0;

                HashMap<String,Object> expressionOfIdeas = new HashMap<>();
                String expression_of_Ideas="expression_of_Ideas";
                long exprssion_of_Ideas= 0;


                HashMap<String,Object> Math_subscore  = new HashMap<>();
                String Math_subscores="Math_subscores";

                HashMap<String,Object> heartOfAlgebra = new HashMap<>();
                String heart_of_algebra="heart_of_algebra";
                long hart_of_algebra= 0;

                HashMap<String,Object> problemSolvingAnalysis = new HashMap<>();
                String problem_solving_analysis="problem_solving_analysis";
                long prblm_solvng_analysis= 0;

                HashMap<String,Object> passportAdvancedMath = new HashMap<>();
                String passport_advanced_math="passport_advanced_math";
                long pasport_advanced_math= 0;


                HashMap<String,Object> crossTest= new HashMap<>();
                String cross_test="cross_test";

                HashMap<String,Object> scienceScore = new HashMap<>();
                String science_score="science_score";
                long science_scor= 0;

                HashMap<String,Object> historyScore = new HashMap<>();
                String history_score="history_score";
                long history_scor= 0;

                try{
                    DocumentSnapshot documentSnap2 = documentSnapApiFuture.get();
                    if (documentSnap2.exists()){
                        Object questionsCatagoryObj = documentSnap2.toObject(Object.class);
                        questionsCatagoryObject= (HashMap<String,Object>) questionsCatagoryObj;

                        question_catagories= (HashMap<String, Object>) questionsCatagoryObject.get("question_catagories");
//                    String question_id= (String) responseQuestions1.get(0).get("question_id");
//                    HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores);
//
//                    String questionCatagoryobj= (String) questionCatagory.get(question_id);
//                   return questionCatagoryobj.equals("command_of_evidence");

                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                HashMap<String ,Long> section_Scores= new HashMap<>();
                HashMap<String , Object> total_Scores=new HashMap<>();
                HashMap<String ,Long> math_Scores= new HashMap<>();
                int size=0;
                long reading_score=0;

                for (int i =0 ; i < responseQuestions1.size(); i++){
                    String answerID= (String) answerQuestions1.get(i).get("answer");
                    String responseId= (String) responseQuestions1.get(i).get("response");
                    String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                    String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                    if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                    {
                        reading_score++;
                        String question_id= (String) responseQuestions1.get(i).get("question_id");
                        HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores);

                        String questionCatagoryobj= (String) questionCatagory.get(question_id);
                        if(questionCatagoryobj.equals("command_of_evidence")){
                            comnd_of_evdnce++;
                        }
                        else if(questionCatagoryobj.equals("words_in_context")){
                            wrds_in_contxt++;
                        }
                        else if(questionCatagoryobj.equals("expression_of_Ideas")){
                            exprssion_of_Ideas++;
                        }
                        else if(questionCatagoryobj.equals("science_score")){
                            science_scor++;
                        }
                        else if(questionCatagoryobj.equals("history_score")){
                            history_scor++;
                        }



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
                    String answerID= (String) answerQuestions2.get(i).get("answer");
                    String responseId= (String) responseQuestions2.get(i).get("response");
                    String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                    String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                    if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                    {
                        writing_score++;
                        String question_id= (String) responseQuestions2.get(i).get("question_id");
                        String Reading_and_Writing_subscores2= "Reading_and_Writing_subscores2";
                        HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Reading_and_Writing_subscores2);
                        String questionCatagoryobj= (String) questionCatagory.get(question_id);
                        if(questionCatagoryobj.equals("command_of_evidence")){
                            comnd_of_evdnce++;
                        }
                        else if(questionCatagoryobj.equals("words_in_context")){
                            wrds_in_contxt++;
                        }
                        else if(questionCatagoryobj.equals("expression_of_Ideas")){
                            exprssion_of_Ideas++;
                        }
                        else if(questionCatagoryobj.equals("science_score")){
                            science_scor++;
                        }
                        else if(questionCatagoryobj.equals("history_score")){
                            history_scor++;
                        }

                    }
                    else {
                        continue;
                    }
                }

                String writeingScoreId="Writing_Section_Score";
                section_Scores.put(writeingScoreId,writing_score);

                long math_calculator_score=0;

                for (int i =0 ; i < responseQuestions3.size(); i++){
                    String answerID= (String) answerQuestions3.get(i).get("answer");
                    String responseId= (String) responseQuestions3.get(i).get("response");
                    String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                    String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                    if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                    {
                        math_calculator_score++;
                        String question_id= (String) responseQuestions3.get(i).get("question_id");

                        HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Math_subscores);
                        String questionCatagoryobj= (String) questionCatagory.get(question_id);
                        if(questionCatagoryobj.equals("heart_of_algebra")){
                            hart_of_algebra++;
                        }
                        else if(questionCatagoryobj.equals("problem_solving_analysis")){
                            prblm_solvng_analysis++;
                        }
                        else if(questionCatagoryobj.equals("passport_advanced_math")){
                            pasport_advanced_math++;
                        }
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
                    String answerID= (String) answerQuestions4.get(i).get("answer");
                    String responseId= (String) responseQuestions4.get(i).get("response");
                    String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                    String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                    if (lastTwoElementsResponse.equals(lastTwoElementsAnswer))
                    {
                        math_no_calculator_score++;
                        String question_id= (String) responseQuestions4.get(i).get("question_id");
                        String Math_subscores2="Math_subscores2";
                        HashMap<String , Object> questionCatagory= (HashMap<String, Object>) question_catagories.get(Math_subscores2);
                        String questionCatagoryobj= (String) questionCatagory.get(question_id);

                        if(questionCatagoryobj.equals("heart_of_algebra")){
                            hart_of_algebra++;
                        }
                        else if(questionCatagoryobj.equals("problem_solving_analysis")){
                            prblm_solvng_analysis++;
                        }
                        else if(questionCatagoryobj.equals("passport_advanced_math")){
                            pasport_advanced_math++;
                        }
                    }
                    else {

                        continue;
                    }
                }
                //////////dash board score generating phase

                commandOfEvidence.put(command_of_evidence,comnd_of_evdnce);

                wordsInContext.put(words_in_context,wrds_in_contxt);
                expressionOfIdeas.put(expression_of_Ideas,exprssion_of_Ideas);

                readingAndWriting.put(command_of_evidence,comnd_of_evdnce);
                readingAndWriting.put(words_in_context,wrds_in_contxt);
                readingAndWriting.put(expression_of_Ideas,exprssion_of_Ideas);



                heartOfAlgebra.put(heart_of_algebra,hart_of_algebra);
                problemSolvingAnalysis.put(problem_solving_analysis,prblm_solvng_analysis);
                passportAdvancedMath.put(passport_advanced_math,pasport_advanced_math);

                Math_subscore.put(heart_of_algebra,hart_of_algebra);
                Math_subscore.put(problem_solving_analysis,prblm_solvng_analysis);
                Math_subscore.put(passport_advanced_math,pasport_advanced_math);

                scienceScore.put(science_score,science_scor);
                historyScore.put(history_score,history_scor);

                crossTest.put(science_score,science_scor);
                crossTest.put(history_score,history_scor);

                dashboardData.put(Reading_and_Writing_subscores,readingAndWriting);
                dashboardData.put(Math_subscores,Math_subscore);
                dashboardData.put(cross_test,crossTest);
                dashboardData.put("use_id",userResponse.get("use_id"));


                //// The test row result

                String math_no_calculator_Id="Math_No_Calculator_Score";
                math_Scores.put(math_no_calculator_Id,math_no_calculator_score);
                section_Scores.put(math_no_calculator_Id,math_no_calculator_score);
                HashMap<String,Object> testData = new HashMap<>();

                /////user id for the test result generation
                String userid= (String) userResponse.get("use_id");
                testData.put("sectionScores",section_Scores);

                HashMap<String,Object>response= new HashMap<>();
                HashMap<String,Object>total_wrong_answer=new HashMap<>();

                total_wrong_answer.put("score",math_calculator_score+math_no_calculator_score+reading_score+writing_score);
                HashMap<String,Object>total_right_answer=new HashMap<>();
                HashMap<String,Object>mainSections= new HashMap<>();

                total_Scores.put("mainSections",mainSections);

                response.put("use_id",userResponse.get("use_id"));
                response.put("section-scores",section_Scores);


                //QW34if (true) generatePracticeTestDashboardData(dashboardData);
                return generatePracticeTestDashboardData(dashboardData);


            }
            return "no test is found";
        } catch (ExecutionException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /// return (String)" "+(String) responseData.get("use_id")

        /// make the calling dashboard data generation and registration

        ///

    }
    public String setOto1(String score){
        if (score.equals("0")) return "1";
        return score;
    }
    private Object generateSectionTestScore(HashMap<String, Long> testData, String userId, HashMap<String, Object> scoring) {

        long writingScore= testData.get("Writing_Section_Score");
        long mathCalcScore= testData.get("Math_Calculator_Score");
        long mathNoCalcScore= testData.get("Math_No_Calculator_Score");
        long readingScore= testData.get("Reading_Section_Score");
//        System.out.println(testData);
        String readingScoreString= String.valueOf(readingScore);
        String writingScoreString= String.valueOf(writingScore);
        String mathCalcScoreString= String.valueOf(mathCalcScore);
        String mathNoCalcScoreString= String.valueOf(mathNoCalcScore);

       mathCalcScoreString=setOto1(mathCalcScoreString);
        mathNoCalcScoreString= setOto1(mathNoCalcScoreString);
        //System.out.println(readingScoreString);
        HashMap<String, Long>SAT_ENGLISH= (HashMap<String, Long>) scoring.get("SAT_ENGLISH");
        HashMap<String, Long>SAT_MATH= (HashMap<String, Long>) scoring.get("SAT_MATH");

        if(true){
            System.out.println(SAT_MATH);
        }
        String xx= String.valueOf((SAT_ENGLISH.get(readingScoreString)));
        String xx1= String.valueOf((SAT_ENGLISH.get(writingScoreString)));
        String xx2= String.valueOf((SAT_MATH.get(mathCalcScoreString)));
        String xx3= String.valueOf((SAT_MATH.get(mathNoCalcScoreString)));
        //System.out.println((SAT_ENGLISH.get(readingScoreString)));
        long readingOutOf400=Long.valueOf(xx);
        long writingOutOf400= Long.valueOf(xx1);
        long mathCalcOutOf400= Long.valueOf(xx2);
        long mathNoCalcOutOf400= Long.valueOf(xx3);
        //System.out.println(readingOutOf400);
        HashMap<String, Long>totalScore= new HashMap<>();
        long totalscore=readingOutOf400+writingOutOf400+mathCalcOutOf400+mathNoCalcOutOf400;
        totalScore.put("totalScore",totalscore);



        //// the out put data structures
        String userid= userId;
        String score="score";
        String subSections="subSections";

        HashMap <String,Object>subSection=new HashMap<>();
        String evidence_based_reading="evidence_based_reading";
        HashMap <String,Long>evidence_based=new HashMap<>();
        long evidenceBased= (readingScore*15)/33;

        evidence_based.put(score,evidenceBased);
        subSection.put(evidence_based_reading,evidence_based);

        String writting_language="writting_language";
        HashMap <String,Long>writting=new HashMap<>();
        long writingLang=(writingScore*15)/33;

        writting.put(score,writingLang);
        subSection.put(writting_language,writting);

        String math_calculator= "math_calculator";
        HashMap <String,Long>math_calc=new HashMap<>();
        long mathCalc=(mathCalcScore*15)/27;

        math_calc.put(score,mathCalc);
        subSection.put(math_calculator,math_calc);


        String math_no_calculator="math_no_calculator";
        HashMap <String,Long>math_no_calc=new HashMap<>();
        long mathNocalc=(mathNoCalcScore*15)/27;

        math_no_calc.put(score,mathNocalc);
        subSection.put(math_no_calculator,math_no_calc);


        String mainSections="mainSections";
        HashMap <String,Object>mainSection=new HashMap<>();
        String total_right_answer="total_right_answer";
        HashMap <String,Long>total_right_answers=new HashMap<>();
        long totalRightAnswer=((writingScore+mathCalcScore+mathNoCalcScore+readingScore)*12)/10;
        total_right_answers.put(score,totalRightAnswer);

        mainSection.put(total_right_answer,total_right_answers);

        String total_wrong_answer="total_wrong_answer";
        HashMap <String,Long>total_wrong_answers=new HashMap<>();
        long totalWrongAnswer=100- totalRightAnswer;
        total_wrong_answers.put(score,totalWrongAnswer);
        mainSection.put(total_wrong_answer,total_wrong_answers);




        String twoSections="twoSections";
        HashMap <String,Object>twoSection=new HashMap<>();
        String sat_english="sat_english";
        HashMap <String,Float>sat_englsh=new HashMap<>();

        float satEnglish=((readingScore+writingScore)*50)/66;
        sat_englsh.put(score,satEnglish);
        twoSection.put(sat_english,sat_englsh);
//Generating Raw Score
        generateRawDiagnosticScoreForSections(writingScore+readingScore,mathCalcScore+mathNoCalcScore,userId);
        String sat_math="sat_math";
        HashMap <String,Float>sat_maths=new HashMap<>();
        float satMath=(mathCalcScore+mathNoCalcScore*50)/54;
        sat_maths.put(score,satMath);
        twoSection.put(sat_math,sat_maths);

        HashMap<String,Object>testResultoutData= new HashMap<>();
        testResultoutData.put("userid",userid);
        testResultoutData.put(subSections,subSection);
        testResultoutData.put(mainSections,mainSection);
        testResultoutData.put(twoSections,twoSection);
        testResultoutData.put("scoreInTheCircle",totalScore);
        //registerLeaderBoard(totalscore,userId);

        registerTestResult(testResultoutData,userid);
        return testResultoutData;
    }

    private String generateRawDiagnosticScoreForSections(long l, long l1, String userId) {
        String testId="diagnostic_testOne";
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        String user_Id= userId;
        HashMap<String,Long>rawTestScore=new HashMap<>();
        HashMap<String, HashMap<String, Long>> map=new HashMap<>();
        rawTestScore.put("sat_english",l);
        rawTestScore.put("sat_math",l1);

        map.put(testId,rawTestScore);
        HashMap<String,Long> map2=new HashMap<>();
        map2.put("correct", l*0);


        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("rawDiagnosticScore").document("welde.gesesse@gmail.com").set(map);
        ApiFuture<WriteResult> collectionApifuture2 = zgjUfirestore.collection("practiceTestRealTimeScore").document("welde.gesesse@gmail.com").set(map2);
        try {
            collectionApifuture2.get().getUpdateTime().toString();
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public Object registerTestResult(HashMap<String,Object> questionCatagory, String userId) {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        String user_Id= userId;

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("testResultData").document("welde.gesesse@gmail.com").set(questionCatagory);
        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public Object generateLeaderBoard() {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("leaderBoard").document("leaderBoard");
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = null;
        try {
            documentSnapshot = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        Object document;
        if (documentSnapshot.exists()) {

            // document exists

            document = documentSnapshot.toObject(Object.class);
            HashMap<String, Object> hashMapData = (HashMap<String, Object>) document;

            return hashMapData;


        }

        return "no test is found";
    }

//    public Object registerLeaderBoard(long score, String userId){
//
//        //if condition if there is a score registered in that user_ID,  get the hashmap, delete the map, then re
//
//        ///else condition register
//
//
//
//
//
//
//        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//
//        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("leaderBoard").document("leaderBoard").set(score);
//        try {
//            return collectionApifuture.get().getUpdateTime().toString();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
////    public Void generateLeaderBoard() {
//////        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//////        DocumentReference documentReference = zgjUfirestore.collection("leaderBoard").document("leaderBoard");//// the data must be according to this user id as a key of a hashmap that has name and score of that student
////        ApiFuture<DocumentSnapshot> future2 = documentReference.get();
////        DocumentSnapshot documentSnapshotlead = null;
////        try {
////            documentSnapshotlead = future2.get();
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        } catch (ExecutionException e) {
////            throw new RuntimeException(e);
////        }
//
//
//
//
//        Object document;
//        if (documentSnapshotlead.exists()) {
//
//            // document exists
//
//            document = documentSnapshotlead.toObject(Object.class);
//            HashMap<String, Object> leaderBoardData = (HashMap<String, Object>) document;
//
//            HashMap<String,Object>leaderBoardOutData= new HashMap<>();
//
//
//            /////i need to map the user id and the user total sscore
//
//            //return leaderBoardData;
//            Set<String> keys = leaderBoardData.keySet();
//            HashMap<String,Object>value= new HashMap<>();
//            HashMap<String, Long> userTOScoreMap= new HashMap<>();
//
//            // Iterate over the keys and print them
//            for (String key : keys) {
//                value= (HashMap<String, Object>) leaderBoardData.get(key);
//                userTOScoreMap.put(key, (Long) value.get("score"));
//            }
//            HashMap<String, Long> temporary= sortHashMapByValues(userTOScoreMap);
//            Set<String> sortedKeys = leaderBoardData.keySet();
//            for (String key : sortedKeys) {
//                leaderBoardOutData.put(key,leaderBoardData.get(key));
//            }
//            return leaderBoardOutData;
//        }
//
//
//
//        return null;
//    }
public Object generatePracticeTestDashboardData(HashMap<String, Object> dashboardData) {
    HashMap<String,Object> dashboardDisplayData = new HashMap<>();
    //// temporary data holders
    HashMap<String,Object>reading_writing = (HashMap<String, Object>) dashboardData.get("Reading_and_Writing_subscores");
    HashMap<String,Object>cross_tst=(HashMap<String, Object>) dashboardData.get("cross_test");
    HashMap<String,Object>Math_scores=(HashMap<String, Object>) dashboardData.get("Math_subscores");
    long comd_evdnc= (long) reading_writing.get("command_of_evidence");
    long wrds_Incntxt= (long) reading_writing.get("words_in_context");
    long expr_idea= (long) reading_writing.get("expression_of_Ideas");
    long hrt_algbra=(long)Math_scores.get("heart_of_algebra");
    long prob_anlsis=(long)Math_scores.get("problem_solving_analysis");
    long pass_advcd=(long)Math_scores.get("passport_advanced_math");
    long scince_scor=(long)cross_tst.get("science_score");
    long history_score=(long)cross_tst.get("history_score");


    HashMap<String,Object> readingAndWriting  = new HashMap<>();
    String Reading_and_Writing_subscores="Reading_and_Writing_subscores";


    String first_section="first_section";
    HashMap<String,Object> firstSection1 = new HashMap<>();
    HashMap<String,Object> firstSection2 = new HashMap<>();
    String second_section="second_section";
    HashMap<String,Object> secondSection1 = new HashMap<>();
    HashMap<String,Object> secondSection2 = new HashMap<>();

    HashMap<String,Object> commandOfEvidence = new HashMap<>();
    String command_of_evidence="command_of_evidence";
    String comnd_of_score1 ="right_answer";
    String comnd_of_score2 ="wrong_answer";
    long comnd_of_scor2= 0;
    String comnd_of_score3 ="out_of";
    long comnd_of_scor3= 0;
    long comnd_of_scor1= 0;

    if(comd_evdnc>10){///there should be some correction to automate out of what the numbers should be
        comnd_of_scor1=10;
        comnd_of_scor2=0;
        comnd_of_scor3=10;

    } else if (comd_evdnc<10) {
        comnd_of_scor1=comd_evdnc;
        comnd_of_scor3=10;
        comnd_of_scor2=comnd_of_scor3-comnd_of_scor1;

    }

    commandOfEvidence.put( comnd_of_score1,comnd_of_scor1);
    commandOfEvidence.put( comnd_of_score2,comnd_of_scor2);
    commandOfEvidence.put( comnd_of_score3,comnd_of_scor3);

    firstSection1.put(command_of_evidence,commandOfEvidence);

    HashMap<String,Object> commandOfEvidence2 = new HashMap<>();
    String command_of_evidence2="command_of_evidence";
    String comnd_of_score2_1 ="right_answer";
    String comnd_of_score2_2 ="wrong_answer";
    String comnd_of_score2_3 ="out_of";
    long comnd_of_scor2_1= comnd_of_scor1*10;
    long comnd_of_scor2_2= comnd_of_scor2*10;
    long comnd_of_scor2_3= comnd_of_scor3*10;

    commandOfEvidence2.put( comnd_of_score2_1,comnd_of_scor2_1);
    commandOfEvidence2.put( comnd_of_score2_2,comnd_of_scor2_2);
    commandOfEvidence2.put( comnd_of_score2_3,comnd_of_scor2_3);

    secondSection1.put(command_of_evidence2,commandOfEvidence2);

    HashMap<String,Object> wordsInContext = new HashMap<>();
    String words_in_context="words_in_context";
    String wrds_cntxt_score1 ="right_answer";
    String wrds_cntxt_score2 ="wrong_answer";
    String wrds_cntxt_score3 ="out_of";
    long wrds_cntxt_scor1= 0;
    long wrds_cntxt_scor2= 0;
    long wrds_cntxt_scor3= 0;


    if(wrds_Incntxt>10){///there should be some correction to automate 'out of' what the numbers should be
        wrds_cntxt_scor1=10;
        wrds_cntxt_scor2=0;
        wrds_cntxt_scor3=10;

    } else if (wrds_Incntxt<10) {
        wrds_cntxt_scor1=wrds_Incntxt;
        wrds_cntxt_scor3=10;
        wrds_cntxt_scor2=wrds_cntxt_scor3-wrds_cntxt_scor1;

    }

    wordsInContext.put( wrds_cntxt_score1,wrds_cntxt_scor1);
    wordsInContext.put( wrds_cntxt_score2,wrds_cntxt_scor2);
    wordsInContext.put( wrds_cntxt_score3,wrds_cntxt_scor3);

    firstSection1.put(words_in_context,wordsInContext);


    HashMap<String,Object> wordsInContext2 = new HashMap<>();
    String words_in_context2="words_in_context";
    String wrds_cntxt_score2_1 ="right_answer";
    String wrds_cntxt_score2_2 ="wrong_answer";
    String wrds_cntxt_score2_3 ="out_of";
    long wrds_cntxt_scor2_1= wrds_cntxt_scor1*10;
    long wrds_cntxt_scor2_2= wrds_cntxt_scor2*10;
    long wrds_cntxt_scor2_3= wrds_cntxt_scor3*10;


    wordsInContext2.put( wrds_cntxt_score2_1,wrds_cntxt_scor2_1);
    wordsInContext2.put( wrds_cntxt_score2_2,wrds_cntxt_scor2_2);
    wordsInContext2.put( wrds_cntxt_score2_3,wrds_cntxt_scor2_3);

    secondSection1.put(words_in_context2,wordsInContext2);

    HashMap<String,Object> expressionOfIdeas = new HashMap<>();
    String expression_of_Ideas="expression_of_Ideas";
    String exprssion_Id_score1 ="right_answer";
    String exprssion_Id_score2 ="wrong_answer";
    String exprssion_Id_score3 ="out_of";
    long exprssion_Id_scor1= 0;
    long exprssion_Id_scor2= 0;
    long exprssion_Id_scor3= 0;

    if(expr_idea>10){///there should be some correction to automate 'out of' what the numbers should be
        exprssion_Id_scor1=10;
        exprssion_Id_scor2=0;
        exprssion_Id_scor3=10;

    } else if (expr_idea<10) {
        exprssion_Id_scor1=expr_idea;
        exprssion_Id_scor3=10;
        exprssion_Id_scor2=exprssion_Id_scor3-exprssion_Id_scor1;

    }

    expressionOfIdeas.put( exprssion_Id_score1,exprssion_Id_scor1);
    expressionOfIdeas.put( exprssion_Id_score2,exprssion_Id_scor2);
    expressionOfIdeas.put( exprssion_Id_score3,exprssion_Id_scor3);

    firstSection1.put(expression_of_Ideas,expressionOfIdeas);

    HashMap<String,Object> expressionOfIdeas2 = new HashMap<>();
    String expression_of_Ideas2="expression_of_Ideas";
    String exprssion_Id_score2_1 ="right_answer";
    String exprssion_Id_score2_2 ="wrong_answer";
    String exprssion_Id_score2_3 ="out_of";
    long exprssion_Id_scor2_1= exprssion_Id_scor1*10;
    long exprssion_Id_scor2_2= exprssion_Id_scor2*10;
    long exprssion_Id_scor2_3= exprssion_Id_scor3*10;

    expressionOfIdeas2.put( exprssion_Id_score2_1,exprssion_Id_scor2_1);
    expressionOfIdeas2.put( exprssion_Id_score2_2,exprssion_Id_scor2_2);
    expressionOfIdeas2.put( exprssion_Id_score2_3,exprssion_Id_scor2_3);

    secondSection1.put(expression_of_Ideas2,expressionOfIdeas2);

    HashMap<String,Object> average = new HashMap<>();
    String average_right_answer="average";
    String average_right_answer1 ="right_answer";
    String average_wrong_answer2 ="wrong_answer";
    String average_out_of3 ="out_of";
    long average_score1= comnd_of_scor1+wrds_cntxt_scor1+exprssion_Id_scor1;
    long average_score2= comnd_of_scor2+wrds_cntxt_scor2+exprssion_Id_scor2;
    long average_score3= comnd_of_scor3+wrds_cntxt_scor3+exprssion_Id_scor3;

    average.put(average_right_answer1,average_score1);
    average.put(average_wrong_answer2,average_score2);
    average.put(average_out_of3,average_score3);

    HashMap<String,Object> average2 = new HashMap<>();
    String average_right_answer2="average";
    String average_right_answer2_1 ="right_answer";
    String average_wrong_answer2_2 ="wrong_answer";
    String average_out_of2_3 ="out_of";
    long average_score2_1= (comnd_of_scor2_1+wrds_cntxt_scor2_1+exprssion_Id_scor2_1)/3;
    long average_score2_2=(comnd_of_scor2_2+wrds_cntxt_scor2_2+exprssion_Id_scor2_2)/3;
    long average_score2_3= (comnd_of_scor2_3+wrds_cntxt_scor2_1+exprssion_Id_scor2_3)/3;
    average2.put(average_right_answer2_1,average_score2_1);
    average2.put(average_wrong_answer2_2,average_score2_2);
    average2.put(average_out_of2_3,average_score2_3);


    firstSection1.put(average_right_answer,average);
    secondSection1.put(average_right_answer2,average);

    readingAndWriting.put(first_section,firstSection1);
    readingAndWriting.put(second_section,secondSection1);


    String Math_subscores="Math_subscores";

    HashMap<String,Object> Math_subscore  = new HashMap<>();



    HashMap<String,Object> heartOfAlgebra = new HashMap<>();
    String heart_of_algebra="heart_of_algebra";
    String hart_of_algebra_score1 ="right_answer";
    String hart_of_algebra_score2 ="wrong_answer";
    String hart_of_algebra_score3 ="out_of";
    long hart_of_algebra_scor1= 0;
    long hart_of_algebra_scor2= 0;
    long hart_of_algebra_scor3= 0;

    if(hrt_algbra>10){///there should be some correction to automate 'out of' what the numbers should be
        hart_of_algebra_scor1=10;
        hart_of_algebra_scor2=0;
        hart_of_algebra_scor3=10;

    } else if (hrt_algbra<10) {
        hart_of_algebra_scor1=hrt_algbra;
        hart_of_algebra_scor3=10;
        hart_of_algebra_scor2=hart_of_algebra_scor3-hart_of_algebra_scor1;

    }

    heartOfAlgebra.put( hart_of_algebra_score1,hart_of_algebra_scor1);
    heartOfAlgebra.put( hart_of_algebra_score2,hart_of_algebra_scor2);
    heartOfAlgebra.put( hart_of_algebra_score3,hart_of_algebra_scor3);

    firstSection2.put(heart_of_algebra,heartOfAlgebra);

    HashMap<String,Object> heartOfAlgebra1 = new HashMap<>();
    String heart_of_algebra2="heart_of_algebra";
    String hart_of_algebra_score2_1 ="right_answer";
    String hart_of_algebra_score2_2 ="wrong_answer";
    String hart_of_algebra_score2_3 ="out_of";
    long hart_of_algebra_scor2_1= hart_of_algebra_scor1*10;
    long hart_of_algebra_scor2_2= hart_of_algebra_scor2*10;
    long hart_of_algebra_scor2_3= hart_of_algebra_scor3*10;


    heartOfAlgebra1.put( hart_of_algebra_score2_1,hart_of_algebra_scor2_1);
    heartOfAlgebra1.put( hart_of_algebra_score2_2,hart_of_algebra_scor2_2);
    heartOfAlgebra1.put( hart_of_algebra_score2_3,hart_of_algebra_scor2_3);

    secondSection2.put(heart_of_algebra2,heartOfAlgebra1);


    HashMap<String,Object> problemSolvingAnalysis = new HashMap<>();
    String problem_solving_analysis="problem_solving_analysis";
    String problem_solving_score1 ="right_answer";
    String problem_solving_score2 ="wrong_answer";
    String problem_solving_score3 ="out_of";
    long problem_solving_scor1= 0;
    long problem_solving_scor2= 0;
    long problem_solving_scor3= 0;
    if(prob_anlsis>10){///there should be some correction to automate 'out of' what the numbers should be
        problem_solving_scor1=10;
        problem_solving_scor2=0;
        problem_solving_scor3=10;

    } else if (prob_anlsis<10) {
        problem_solving_scor1=prob_anlsis;
        problem_solving_scor3=10;
        problem_solving_scor2=problem_solving_scor3-problem_solving_scor1;

    }

    problemSolvingAnalysis.put( problem_solving_score1,problem_solving_scor1);
    problemSolvingAnalysis.put( problem_solving_score2,problem_solving_scor2);
    problemSolvingAnalysis.put( problem_solving_score3,problem_solving_scor3);

    firstSection2.put(problem_solving_analysis,problemSolvingAnalysis);

    HashMap<String,Object> problemSolvingAnalysis1 = new HashMap<>();
    String problem_solving_analysis2="problem_solving_analysis";
    String problem_solving_score2_1 ="right_answer";
    String problem_solving_score2_2 ="wrong_answer";
    String problem_solving_score2_3 ="out_of";
    long problem_solving_scor2_1= problem_solving_scor1*10;
    long problem_solving_scor2_2= problem_solving_scor2*10;
    long problem_solving_scor2_3= problem_solving_scor3*10;



    problemSolvingAnalysis1.put( problem_solving_score2_1,problem_solving_scor2_1);
    problemSolvingAnalysis1.put( problem_solving_score2_2,problem_solving_scor2_2);
    problemSolvingAnalysis1.put( problem_solving_score2_3,problem_solving_scor2_3);

    secondSection2.put(problem_solving_analysis2,problemSolvingAnalysis1);


    HashMap<String,Object> passportAdvancedMath = new HashMap<>();
    String passport_advanced_math="passport_advanced_math";
    String passport_advanced_score1 ="right_answer";
    String passport_advanced_score2 ="wrong_answer";
    String passport_advanced_score3 ="out_of";
    long pasport_advanced_scor1= 0;
    long pasport_advanced_scor2= 0;
    long pasport_advanced_scor3= 0;

    if(pass_advcd>10){///there should be some correction to automate 'out of' what the numbers should be
        pasport_advanced_scor1=10;
        pasport_advanced_scor2=0;
        pasport_advanced_scor3=10;

    } else if (pass_advcd<10) {
        pasport_advanced_scor1=pass_advcd;
        pasport_advanced_scor3=10;
        pasport_advanced_scor2=pasport_advanced_scor3-pasport_advanced_scor1;

    }

    passportAdvancedMath.put( passport_advanced_score1,pasport_advanced_scor1);
    passportAdvancedMath.put( passport_advanced_score2,pasport_advanced_scor2);
    passportAdvancedMath.put( passport_advanced_score3,pasport_advanced_scor3);

    firstSection2.put(passport_advanced_math,passportAdvancedMath);

    HashMap<String,Object> passportAdvancedMath1 = new HashMap<>();
    String passport_advanced_math2="passport_advanced_math";
    String passport_advanced_score2_1 ="right_answer";
    String passport_advanced_score2_2 ="wrong_answer";
    String passport_advanced_score2_3 ="out_of";
    long pasport_advanced_scor2_1= pasport_advanced_scor1*10;
    long pasport_advanced_scor2_2= pasport_advanced_scor2*10;
    long pasport_advanced_scor2_3= pasport_advanced_scor3*10;

    passportAdvancedMath1.put( passport_advanced_score2_1,pasport_advanced_scor2_1);
    passportAdvancedMath1.put( passport_advanced_score2_2,pasport_advanced_scor2_2);
    passportAdvancedMath1.put( passport_advanced_score2_3,pasport_advanced_scor2_3);


    secondSection2.put(passport_advanced_math2,passportAdvancedMath1);
    HashMap<String,Object> average_1 = new HashMap<>();
    String average_right_answer_1="average";
    String average_right_answer1_1 ="right_answer";
    String average_wrong_answer2_1 ="wrong_answer";
    String average_out_of3_1 ="out_of";
    long average_score11= hart_of_algebra_scor1+problem_solving_scor1+pasport_advanced_scor1;
    long average_score21= hart_of_algebra_scor2+problem_solving_scor2+pasport_advanced_scor2;
    long average_score31= hart_of_algebra_scor3+problem_solving_scor3+pasport_advanced_scor3;

    average_1.put(average_right_answer1_1,average_score11);
    average_1.put(average_wrong_answer2_1,average_score21);
    average_1.put(average_out_of3_1,average_score31);

    HashMap<String,Object> average_2 = new HashMap<>();
    String average_right_answer_2="average";
    String average_right_answer2_ ="right_answer";
    String average_wrong_answer2_ ="wrong_answer";
    String average_out_of2_ ="out_of";
    long average_score22= (hart_of_algebra_scor2_1+problem_solving_scor2_1+pasport_advanced_scor2_1)/3;
    long average_score23=(hart_of_algebra_scor2_2+problem_solving_scor2_2+pasport_advanced_scor2_2)/3;
    long average_score24= (hart_of_algebra_scor2_3+problem_solving_scor2_3+pasport_advanced_scor2_3)/3;
    average_2.put(average_right_answer2_,average_score22);
    average_2.put(average_wrong_answer2_,average_score23);
    average_2.put(average_out_of2_,average_score24);


    firstSection2.put(average_right_answer_1,average_1);
    secondSection2.put(average_right_answer_2,average_2);

    Math_subscore.put(first_section,firstSection2);
    Math_subscore.put(second_section,secondSection2);

    HashMap<String,Object> crossTest= new HashMap<>();
    String cross_test="cross_test";


    HashMap<String,Object> scienceScore = new HashMap<>();
    String science_score="science_score";
    String scince_score="score";
    String science_score1 ="right_answer";//out of 100
    String science_score2 ="wrong_answer";//out of 100
    long science_scor1=0;
    long science_scor2= 0;
    long science_scor3= 0;

    if(scince_scor+10==10){
        science_scor1=0;
    } else if (scince_scor+10>10 &&  scince_scor+10<=20 ) {
        science_scor1=scince_scor+10;

    } else if (scince_scor+10>20) {
        science_scor1=20;
    }
    science_scor2=(science_scor1*4);
    science_scor3=100-(science_scor1*4);
    scienceScore.put(scince_score,science_scor1);
    scienceScore.put(science_score1,science_scor2);
    scienceScore.put(science_score2,science_scor3);

    crossTest.put(science_score,scienceScore);

    HashMap<String,Object> historyScore = new HashMap<>();
    String history_scor="history_score";
    String histry_score1="score";
    String history_score2 ="right_answer";//out of 100
    String history_score3 ="wrong_answer";//out of 100

    long history_scor1= 0;
    long history_scor2= 0;
    long history_scor3= 0;
    if(history_score+10==10){
        history_scor1=0;
    } else if (history_score+10>10 &&  history_score+10<=20 ) {
        history_scor1=history_score+10;

    } else if (history_score+10>20) {
        history_scor1=20;
    }
    history_scor2=(history_scor1*4);
    history_scor3=100-(history_scor1*4);


    historyScore.put(histry_score1,history_scor1);
    historyScore.put(history_score2,history_scor2);
    historyScore.put(history_score3,history_scor3);

    crossTest.put(science_score,scienceScore);
    crossTest.put(history_scor,historyScore);
    String userId= (String) dashboardData.get("use_id");
    dashboardDisplayData.put("use_id","welde.gesesse@gmail.com");
    dashboardDisplayData.put(Reading_and_Writing_subscores, readingAndWriting);
    dashboardDisplayData.put(Math_subscores,Math_subscore);
    dashboardDisplayData.put(cross_test,crossTest);
//        System.out.println(dashboardDisplayData);
    return dashboardDisplayData;

}


    public Object generateDashboardData(HashMap<String, Object> dashboardData) {
        HashMap<String,Object> dashboardDisplayData = new HashMap<>();
        //// temporary data holders
        HashMap<String,Object>reading_writing = (HashMap<String, Object>) dashboardData.get("Reading_and_Writing_subscores");
        HashMap<String,Object>cross_tst=(HashMap<String, Object>) dashboardData.get("cross_test");
        HashMap<String,Object>Math_scores=(HashMap<String, Object>) dashboardData.get("Math_subscores");
        long comd_evdnc= (long) reading_writing.get("command_of_evidence");
        long wrds_Incntxt= (long) reading_writing.get("words_in_context");
        long expr_idea= (long) reading_writing.get("expression_of_Ideas");
        long hrt_algbra=(long)Math_scores.get("heart_of_algebra");
        long prob_anlsis=(long)Math_scores.get("problem_solving_analysis");
        long pass_advcd=(long)Math_scores.get("passport_advanced_math");
        long scince_scor=(long)cross_tst.get("science_score");
        long history_score=(long)cross_tst.get("history_score");


        HashMap<String,Object> readingAndWriting  = new HashMap<>();
        String Reading_and_Writing_subscores="Reading_and_Writing_subscores";


        String first_section="first_section";
        HashMap<String,Object> firstSection1 = new HashMap<>();
        HashMap<String,Object> firstSection2 = new HashMap<>();
        String second_section="second_section";
        HashMap<String,Object> secondSection1 = new HashMap<>();
        HashMap<String,Object> secondSection2 = new HashMap<>();

        HashMap<String,Object> commandOfEvidence = new HashMap<>();
        String command_of_evidence="command_of_evidence";
        String comnd_of_score1 ="right_answer";
        String comnd_of_score2 ="wrong_answer";
        long comnd_of_scor2= 0;
        String comnd_of_score3 ="out_of";
        long comnd_of_scor3= 0;
        long comnd_of_scor1= 0;

        if(comd_evdnc>10){///there should be some correction to automate out of what the numbers should be
            comnd_of_scor1=10;
            comnd_of_scor2=0;
            comnd_of_scor3=10;

        } else if (comd_evdnc<10) {
            comnd_of_scor1=comd_evdnc;
            comnd_of_scor3=10;
            comnd_of_scor2=comnd_of_scor3-comnd_of_scor1;

        }

        commandOfEvidence.put( comnd_of_score1,comnd_of_scor1);
        commandOfEvidence.put( comnd_of_score2,comnd_of_scor2);
        commandOfEvidence.put( comnd_of_score3,comnd_of_scor3);

        firstSection1.put(command_of_evidence,commandOfEvidence);

        HashMap<String,Object> commandOfEvidence2 = new HashMap<>();
        String command_of_evidence2="command_of_evidence";
        String comnd_of_score2_1 ="right_answer";
        String comnd_of_score2_2 ="wrong_answer";
        String comnd_of_score2_3 ="out_of";
        long comnd_of_scor2_1= comnd_of_scor1*10;
        long comnd_of_scor2_2= comnd_of_scor2*10;
        long comnd_of_scor2_3= comnd_of_scor3*10;

        commandOfEvidence2.put( comnd_of_score2_1,comnd_of_scor2_1);
        commandOfEvidence2.put( comnd_of_score2_2,comnd_of_scor2_2);
        commandOfEvidence2.put( comnd_of_score2_3,comnd_of_scor2_3);

        secondSection1.put(command_of_evidence2,commandOfEvidence2);

        HashMap<String,Object> wordsInContext = new HashMap<>();
        String words_in_context="words_in_context";
        String wrds_cntxt_score1 ="right_answer";
        String wrds_cntxt_score2 ="wrong_answer";
        String wrds_cntxt_score3 ="out_of";
        long wrds_cntxt_scor1= 0;
        long wrds_cntxt_scor2= 0;
        long wrds_cntxt_scor3= 0;


        if(wrds_Incntxt>10){///there should be some correction to automate 'out of' what the numbers should be
            wrds_cntxt_scor1=10;
            wrds_cntxt_scor2=0;
            wrds_cntxt_scor3=10;

        } else if (wrds_Incntxt<10) {
            wrds_cntxt_scor1=wrds_Incntxt;
            wrds_cntxt_scor3=10;
            wrds_cntxt_scor2=wrds_cntxt_scor3-wrds_cntxt_scor1;

        }

        wordsInContext.put( wrds_cntxt_score1,wrds_cntxt_scor1);
        wordsInContext.put( wrds_cntxt_score2,wrds_cntxt_scor2);
        wordsInContext.put( wrds_cntxt_score3,wrds_cntxt_scor3);

        firstSection1.put(words_in_context,wordsInContext);


        HashMap<String,Object> wordsInContext2 = new HashMap<>();
        String words_in_context2="words_in_context";
        String wrds_cntxt_score2_1 ="right_answer";
        String wrds_cntxt_score2_2 ="wrong_answer";
        String wrds_cntxt_score2_3 ="out_of";
        long wrds_cntxt_scor2_1= wrds_cntxt_scor1*10;
        long wrds_cntxt_scor2_2= wrds_cntxt_scor2*10;
        long wrds_cntxt_scor2_3= wrds_cntxt_scor3*10;


        wordsInContext2.put( wrds_cntxt_score2_1,wrds_cntxt_scor2_1);
        wordsInContext2.put( wrds_cntxt_score2_2,wrds_cntxt_scor2_2);
        wordsInContext2.put( wrds_cntxt_score2_3,wrds_cntxt_scor2_3);

        secondSection1.put(words_in_context2,wordsInContext2);

        HashMap<String,Object> expressionOfIdeas = new HashMap<>();
        String expression_of_Ideas="expression_of_Ideas";
        String exprssion_Id_score1 ="right_answer";
        String exprssion_Id_score2 ="wrong_answer";
        String exprssion_Id_score3 ="out_of";
        long exprssion_Id_scor1= 0;
        long exprssion_Id_scor2= 0;
        long exprssion_Id_scor3= 0;

        if(expr_idea>10){///there should be some correction to automate 'out of' what the numbers should be
            exprssion_Id_scor1=10;
            exprssion_Id_scor2=0;
            exprssion_Id_scor3=10;

        } else if (expr_idea<10) {
            exprssion_Id_scor1=expr_idea;
            exprssion_Id_scor3=10;
            exprssion_Id_scor2=exprssion_Id_scor3-exprssion_Id_scor1;

        }

        expressionOfIdeas.put( exprssion_Id_score1,exprssion_Id_scor1);
        expressionOfIdeas.put( exprssion_Id_score2,exprssion_Id_scor2);
        expressionOfIdeas.put( exprssion_Id_score3,exprssion_Id_scor3);

        firstSection1.put(expression_of_Ideas,expressionOfIdeas);

        HashMap<String,Object> expressionOfIdeas2 = new HashMap<>();
        String expression_of_Ideas2="expression_of_Ideas";
        String exprssion_Id_score2_1 ="right_answer";
        String exprssion_Id_score2_2 ="wrong_answer";
        String exprssion_Id_score2_3 ="out_of";
        long exprssion_Id_scor2_1= exprssion_Id_scor1*10;
        long exprssion_Id_scor2_2= exprssion_Id_scor2*10;
        long exprssion_Id_scor2_3= exprssion_Id_scor3*10;

        expressionOfIdeas2.put( exprssion_Id_score2_1,exprssion_Id_scor2_1);
        expressionOfIdeas2.put( exprssion_Id_score2_2,exprssion_Id_scor2_2);
        expressionOfIdeas2.put( exprssion_Id_score2_3,exprssion_Id_scor2_3);

        secondSection1.put(expression_of_Ideas2,expressionOfIdeas2);

        HashMap<String,Object> average = new HashMap<>();
        String average_right_answer="average";
        String average_right_answer1 ="right_answer";
        String average_wrong_answer2 ="wrong_answer";
        String average_out_of3 ="out_of";
        long average_score1= comnd_of_scor1+wrds_cntxt_scor1+exprssion_Id_scor1;
        long average_score2= comnd_of_scor2+wrds_cntxt_scor2+exprssion_Id_scor2;
        long average_score3= comnd_of_scor3+wrds_cntxt_scor3+exprssion_Id_scor3;

        average.put(average_right_answer1,average_score1);
        average.put(average_wrong_answer2,average_score2);
        average.put(average_out_of3,average_score3);

        HashMap<String,Object> average2 = new HashMap<>();
        String average_right_answer2="average";
        String average_right_answer2_1 ="right_answer";
        String average_wrong_answer2_2 ="wrong_answer";
        String average_out_of2_3 ="out_of";
        long average_score2_1= (comnd_of_scor2_1+wrds_cntxt_scor2_1+exprssion_Id_scor2_1)/3;
        long average_score2_2=(comnd_of_scor2_2+wrds_cntxt_scor2_2+exprssion_Id_scor2_2)/3;
        long average_score2_3= (comnd_of_scor2_3+wrds_cntxt_scor2_1+exprssion_Id_scor2_3)/3;
        average2.put(average_right_answer2_1,average_score2_1);
        average2.put(average_wrong_answer2_2,average_score2_2);
        average2.put(average_out_of2_3,average_score2_3);


        firstSection1.put(average_right_answer,average);
        secondSection1.put(average_right_answer2,average);

        readingAndWriting.put(first_section,firstSection1);
        readingAndWriting.put(second_section,secondSection1);


        String Math_subscores="Math_subscores";

        HashMap<String,Object> Math_subscore  = new HashMap<>();



        HashMap<String,Object> heartOfAlgebra = new HashMap<>();
        String heart_of_algebra="heart_of_algebra";
        String hart_of_algebra_score1 ="right_answer";
        String hart_of_algebra_score2 ="wrong_answer";
        String hart_of_algebra_score3 ="out_of";
        long hart_of_algebra_scor1= 0;
        long hart_of_algebra_scor2= 0;
        long hart_of_algebra_scor3= 0;

        if(hrt_algbra>10){///there should be some correction to automate 'out of' what the numbers should be
            hart_of_algebra_scor1=10;
            hart_of_algebra_scor2=0;
            hart_of_algebra_scor3=10;

        } else if (hrt_algbra<10) {
            hart_of_algebra_scor1=hrt_algbra;
            hart_of_algebra_scor3=10;
            hart_of_algebra_scor2=hart_of_algebra_scor3-hart_of_algebra_scor1;

        }

        heartOfAlgebra.put( hart_of_algebra_score1,hart_of_algebra_scor1);
        heartOfAlgebra.put( hart_of_algebra_score2,hart_of_algebra_scor2);
        heartOfAlgebra.put( hart_of_algebra_score3,hart_of_algebra_scor3);

        firstSection2.put(heart_of_algebra,heartOfAlgebra);

        HashMap<String,Object> heartOfAlgebra1 = new HashMap<>();
        String heart_of_algebra2="heart_of_algebra";
        String hart_of_algebra_score2_1 ="right_answer";
        String hart_of_algebra_score2_2 ="wrong_answer";
        String hart_of_algebra_score2_3 ="out_of";
        long hart_of_algebra_scor2_1= hart_of_algebra_scor1*10;
        long hart_of_algebra_scor2_2= hart_of_algebra_scor2*10;
        long hart_of_algebra_scor2_3= hart_of_algebra_scor3*10;


        heartOfAlgebra1.put( hart_of_algebra_score2_1,hart_of_algebra_scor2_1);
        heartOfAlgebra1.put( hart_of_algebra_score2_2,hart_of_algebra_scor2_2);
        heartOfAlgebra1.put( hart_of_algebra_score2_3,hart_of_algebra_scor2_3);

        secondSection2.put(heart_of_algebra2,heartOfAlgebra1);


        HashMap<String,Object> problemSolvingAnalysis = new HashMap<>();
        String problem_solving_analysis="problem_solving_analysis";
        String problem_solving_score1 ="right_answer";
        String problem_solving_score2 ="wrong_answer";
        String problem_solving_score3 ="out_of";
        long problem_solving_scor1= 0;
        long problem_solving_scor2= 0;
        long problem_solving_scor3= 0;
        if(prob_anlsis>10){///there should be some correction to automate 'out of' what the numbers should be
            problem_solving_scor1=10;
            problem_solving_scor2=0;
            problem_solving_scor3=10;

        } else if (prob_anlsis<10) {
            problem_solving_scor1=prob_anlsis;
            problem_solving_scor3=10;
            problem_solving_scor2=problem_solving_scor3-problem_solving_scor1;

        }

        problemSolvingAnalysis.put( problem_solving_score1,problem_solving_scor1);
        problemSolvingAnalysis.put( problem_solving_score2,problem_solving_scor2);
        problemSolvingAnalysis.put( problem_solving_score3,problem_solving_scor3);

        firstSection2.put(problem_solving_analysis,problemSolvingAnalysis);

        HashMap<String,Object> problemSolvingAnalysis1 = new HashMap<>();
        String problem_solving_analysis2="problem_solving_analysis";
        String problem_solving_score2_1 ="right_answer";
        String problem_solving_score2_2 ="wrong_answer";
        String problem_solving_score2_3 ="out_of";
        long problem_solving_scor2_1= problem_solving_scor1*10;
        long problem_solving_scor2_2= problem_solving_scor2*10;
        long problem_solving_scor2_3= problem_solving_scor3*10;



        problemSolvingAnalysis1.put( problem_solving_score2_1,problem_solving_scor2_1);
        problemSolvingAnalysis1.put( problem_solving_score2_2,problem_solving_scor2_2);
        problemSolvingAnalysis1.put( problem_solving_score2_3,problem_solving_scor2_3);

        secondSection2.put(problem_solving_analysis2,problemSolvingAnalysis1);


        HashMap<String,Object> passportAdvancedMath = new HashMap<>();
        String passport_advanced_math="passport_advanced_math";
        String passport_advanced_score1 ="right_answer";
        String passport_advanced_score2 ="wrong_answer";
        String passport_advanced_score3 ="out_of";
        long pasport_advanced_scor1= 0;
        long pasport_advanced_scor2= 0;
        long pasport_advanced_scor3= 0;

        if(pass_advcd>10){///there should be some correction to automate 'out of' what the numbers should be
            pasport_advanced_scor1=10;
            pasport_advanced_scor2=0;
            pasport_advanced_scor3=10;

        } else if (pass_advcd<10) {
            pasport_advanced_scor1=pass_advcd;
            pasport_advanced_scor3=10;
            pasport_advanced_scor2=pasport_advanced_scor3-pasport_advanced_scor1;

        }

        passportAdvancedMath.put( passport_advanced_score1,pasport_advanced_scor1);
        passportAdvancedMath.put( passport_advanced_score2,pasport_advanced_scor2);
        passportAdvancedMath.put( passport_advanced_score3,pasport_advanced_scor3);

        firstSection2.put(passport_advanced_math,passportAdvancedMath);

        HashMap<String,Object> passportAdvancedMath1 = new HashMap<>();
        String passport_advanced_math2="passport_advanced_math";
        String passport_advanced_score2_1 ="right_answer";
        String passport_advanced_score2_2 ="wrong_answer";
        String passport_advanced_score2_3 ="out_of";
        long pasport_advanced_scor2_1= pasport_advanced_scor1*10;
        long pasport_advanced_scor2_2= pasport_advanced_scor2*10;
        long pasport_advanced_scor2_3= pasport_advanced_scor3*10;

        passportAdvancedMath1.put( passport_advanced_score2_1,pasport_advanced_scor2_1);
        passportAdvancedMath1.put( passport_advanced_score2_2,pasport_advanced_scor2_2);
        passportAdvancedMath1.put( passport_advanced_score2_3,pasport_advanced_scor2_3);


        secondSection2.put(passport_advanced_math2,passportAdvancedMath1);
        HashMap<String,Object> average_1 = new HashMap<>();
        String average_right_answer_1="average";
        String average_right_answer1_1 ="right_answer";
        String average_wrong_answer2_1 ="wrong_answer";
        String average_out_of3_1 ="out_of";
        long average_score11= hart_of_algebra_scor1+problem_solving_scor1+pasport_advanced_scor1;
        long average_score21= hart_of_algebra_scor2+problem_solving_scor2+pasport_advanced_scor2;
        long average_score31= hart_of_algebra_scor3+problem_solving_scor3+pasport_advanced_scor3;

        average_1.put(average_right_answer1_1,average_score11);
        average_1.put(average_wrong_answer2_1,average_score21);
        average_1.put(average_out_of3_1,average_score31);

        HashMap<String,Object> average_2 = new HashMap<>();
        String average_right_answer_2="average";
        String average_right_answer2_ ="right_answer";
        String average_wrong_answer2_ ="wrong_answer";
        String average_out_of2_ ="out_of";
        long average_score22= (hart_of_algebra_scor2_1+problem_solving_scor2_1+pasport_advanced_scor2_1)/3;
        long average_score23=(hart_of_algebra_scor2_2+problem_solving_scor2_2+pasport_advanced_scor2_2)/3;
        long average_score24= (hart_of_algebra_scor2_3+problem_solving_scor2_3+pasport_advanced_scor2_3)/3;
        average_2.put(average_right_answer2_,average_score22);
        average_2.put(average_wrong_answer2_,average_score23);
        average_2.put(average_out_of2_,average_score24);


        firstSection2.put(average_right_answer_1,average_1);
        secondSection2.put(average_right_answer_2,average_2);

        Math_subscore.put(first_section,firstSection2);
        Math_subscore.put(second_section,secondSection2);

        HashMap<String,Object> crossTest= new HashMap<>();
        String cross_test="cross_test";


        HashMap<String,Object> scienceScore = new HashMap<>();
        String science_score="science_score";
        String scince_score="score";
        String science_score1 ="right_answer";//out of 100
        String science_score2 ="wrong_answer";//out of 100
        long science_scor1=0;
        long science_scor2= 0;
        long science_scor3= 0;

        if(scince_scor+10==10){
            science_scor1=0;
        } else if (scince_scor+10>10 &&  scince_scor+10<=20 ) {
            science_scor1=scince_scor+10;

        } else if (scince_scor+10>20) {
            science_scor1=20;
        }
        science_scor2=(science_scor1*4);
        science_scor3=100-(science_scor1*4);
        scienceScore.put(scince_score,science_scor1);
        scienceScore.put(science_score1,science_scor2);
        scienceScore.put(science_score2,science_scor3);

        crossTest.put(science_score,scienceScore);

        HashMap<String,Object> historyScore = new HashMap<>();
        String history_scor="history_score";
        String histry_score1="score";
        String history_score2 ="right_answer";//out of 100
        String history_score3 ="wrong_answer";//out of 100

        long history_scor1= 0;
        long history_scor2= 0;
        long history_scor3= 0;
        if(history_score+10==10){
            history_scor1=0;
        } else if (history_score+10>10 &&  history_score+10<=20 ) {
            history_scor1=history_score+10;

        } else if (history_score+10>20) {
            history_scor1=20;
        }
        history_scor2=(history_scor1*4);
        history_scor3=100-(history_scor1*4);


        historyScore.put(histry_score1,history_scor1);
        historyScore.put(history_score2,history_scor2);
        historyScore.put(history_score3,history_scor3);

        crossTest.put(science_score,scienceScore);
        crossTest.put(history_scor,historyScore);
        String userId= (String) dashboardData.get("use_id");
        dashboardDisplayData.put("use_id","welde.gesesse@gmail.com");
        dashboardDisplayData.put(Reading_and_Writing_subscores, readingAndWriting);
        dashboardDisplayData.put(Math_subscores,Math_subscore);
        dashboardDisplayData.put(cross_test,crossTest);
//        System.out.println(dashboardDisplayData);
        return registerDashboardData(dashboardDisplayData,"welde.gesesse@gmail.com");

    }


    public Object registerDashboardData(HashMap<String,Object> questionCatagory, String userId) {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        String user_Id= (String) questionCatagory.get("use_id");
       // System.out.println(questionCatagory);

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("dashboard_data").document(userId).set(questionCatagory);
        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getDashBoardData(String use_id) throws ExecutionException, InterruptedException, IOException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("dashboard_data").document(use_id);
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




    public static LinkedHashMap<String, Long> sortHashMapByValues(HashMap<String, Long> hashMap) {
        List<Map.Entry<String, Long>> list = new LinkedList<>(hashMap.entrySet());

        // Sort the list based on values in descending order
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Create a new LinkedHashMap to store the sorted entries
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();

        // Iterate over the sorted list and put entries into the new LinkedHashMap
        for (Map.Entry<String, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


}
