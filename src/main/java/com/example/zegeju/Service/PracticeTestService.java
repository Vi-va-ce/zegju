package com.example.zegeju.Service;

import org.json.JSONObject;
import com.example.zegeju.Domain.PracticeTests.Sections;
import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PracticeTestService {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private SectionsPerformanceService sectionsPerformanceService;
    public Object getPracticeTest(String test_id) throws ExecutionException, InterruptedException, IOException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = zgjUfirestore.collection("tests").document(test_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();


        String correct="correct";
        String incorrect="incorrect";
        String progress="progress";
        String sameScoringProbabiloty="sameScoringProbability";
       /// ------------------> session creation
        long correctNum=0;
        long incorrectNum=0;
        long progressNum=0;
        long sameScoringProbabilotyNum=0;
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


//    public Object registerAnswer(Object answer){
//
//        HashMap<String, Object> hashMapData = (HashMap<String, Object>) answer;
//        String testIdd= hashMapData.keySet().iterator().next();
//        String test_Id= (String) hashMapData.get(testIdd);
//        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("answer").document(test_Id).set(answer);
//
//        try {
//            return collectionApifuture.get().getUpdateTime().toString();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public HashMap<String, Object> generateRealTimeResult(HashMap<String,Object> response) throws ExecutionException, InterruptedException {
        /*
        * i will accept the sent data and
        * generate che if it is right
        * */
        //Data to be exported

        String correct="correct";
        String incorrect="incorrect";
        String progress="progress";
        String sameScoringProbabiloty="sameScoringProbability";
        String explain="explanation";
        /// ------------------> score holders
        long correctNum=0;
        long correctNumScalled=0;

        long incorrectNum=0;
        long incorrectNumScalled=0;
        long progressNum=0;
        String explanation=new String();
        long sameScoringProbabilotyNum=0;

        /*
        long sameScoringProbability{the scoring probability gets high when lower
        }
         */
        //String email= (String) response.get("email");

        Set<String> keys= response.keySet();
        List<String> keyList = new ArrayList<>(keys);
        String testid= keyList.get(0);//test_id


        HashMap<String,Object>userResponseSection= (HashMap<String, Object>) response.get(testid);

        Set<String>keys2=userResponseSection.keySet();
        List<String> keyList2 = new ArrayList<>(keys2);
//        System.out.println(userResponseSection);
        String sectionId= keyList2.get(0);//section_id
        HashMap<String,Object>responses= (HashMap<String, Object>) userResponseSection.get(sectionId);
        Set<String>keys3=responses.keySet();
        //
        if(sectionId.contains("Reading")){progressNum=1;}
        else if (sectionId.contains("writing")) {
            
            progressNum=2;
            
        } else if (sectionId.contains("no Calculator")) {
            progressNum=4;
        } else {
            progressNum=3;
        }
        /*
        scale of getting probality high
        i have to fetch the Diagnostic test the math and the english
        then>>> the the user will that he has higher probality of getting the same result until he/she reaches the score the diagnostic test score plus 150
        if the diagnostic test result of a test score is equal to the diagnostic test result or is equal to 150 plus score
        100%
        then a huge drop of probaniliy of scorin the same adding another 150 it is per section
        95%
        then another huge drop will be there to add another 150 score it is per section
        85% then keep in looseing 10 percent from here on
        ////>
        the implementation will be
        i will get the total section score from dashborad data  and fo the scoring
        --put the datas into two section scores
        if the section id contains writing and reading
        use the writing and reading scores and scale the
        else if it contains math in it
        use the second section score
         */
        String userId= "welde.gesesse@gmail.com";
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference2 = zgjUfirestore.collection("practiceTestRealTimeScore").document(userId);
        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();

        /*
        Updating the dashboard data with the practiceTest User response
         */
        updateDashboardData(userId, response);
        /*

         */

        try {
            DocumentSnapshot documentSnapshot = future2.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                HashMap<String, Object> linkedHashMap = new HashMap<>((Map) document);

                correctNum= (Long) linkedHashMap.get("correct");

            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        List<String> keyList3 = new ArrayList<>(keys3);
        String questionId= keyList3.get(0);
        String responseId= (String) responses.get(questionId);
        /*
        Getting the answer for the specific test and assigning it into  anwe
         */
        DocumentReference documentReference3 = zgjUfirestore.collection("rawDiagnosticScore").document(userId);
        ApiFuture<DocumentSnapshot> future3 = documentReference3.get();
        long diagnosticEglishResult=0;
        long diagnosticMathResult=0;
        try {
            DocumentSnapshot documentSnapshot = future3.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                HashMap<String, Object> linkedHashMap = new HashMap<>((Map) document);
                HashMap<String,Long>rawHashMapData= (HashMap<String, Long>) linkedHashMap.get("diagnostic_testOne");
             diagnosticEglishResult=rawHashMapData.get("sat_english");
                diagnosticMathResult= rawHashMapData.get("sat_math");
            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //
        DocumentReference documentReference = zgjUfirestore.collection("answer").document(testid);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        try {
            DocumentSnapshot documentSnapshot = future.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>((Map) document);


                ArrayList<Object> answerSections = (ArrayList<Object>) linkedHashMap.get("sections");
                HashMap<String,Object>answerSection1=(HashMap<String,Object>) answerSections.get(0);//Reading Section
                HashMap<String,Object>answerSection2=(HashMap<String,Object>) answerSections.get(1);//Writing Section
                HashMap<String,Object>answerSection3=(HashMap<String,Object>) answerSections.get(2);//Math   Section
               // System.out.println(answerSections);
                ArrayList<HashMap<String,Object>>answerQuestions1= (ArrayList<HashMap<String, Object>>) answerSection1.get("questions");
                
                for(HashMap<String,Object> elements:answerQuestions1){
                    //System.out.println(questionId);
                    //System.out.println(elements.get("question_id"));
                    if(questionId.equals(elements.get("question_id"))){

                        String answerID= (String) elements.get("answer");
                        String lastTwoElementsAnswer = answerID.substring(Math.max(0, answerID.length() - 2));
                        String lastTwoElementsResponse = responseId.substring(Math.max(0, responseId.length() - 2));
                        if(lastTwoElementsResponse.equals(lastTwoElementsAnswer)){
                           correctNum=correctNum+1;

                            explanation= (String) elements.get("explanation");

                            udpdatePracticeTestRealTimeScore(correctNum,userId);
                            long correctNum2=  getPracticeTestRealTimeScore(userId);
                            if (correctNum2<=diagnosticEglishResult+10)

                            {
                                sameScoringProbabilotyNum=100;
                            }


                            else if (correctNum2<=diagnosticEglishResult+21) {
                                sameScoringProbabilotyNum=95;
                        }
                            else  {
                                sameScoringProbabilotyNum=85;//exponential decrease from now on
                            }

                        } else {
                            incorrectNum=incorrectNum++;
                            incorrectNumScalled=incorrectNum*(10/12);
                        }
                    }
                }

            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);

        }

        HashMap<String,Object>hashMapExported=new HashMap<>();
        HashMap<String,Object>dataFetched=new HashMap<>();
        System.out.println(getPracticeTestRealTimeScore(userId));
        long fetchedScore=getPracticeTestRealTimeScore(userId);

        //System.out.println(fetchedScore);
        correctNumScalled=((fetchedScore*10)/12);
        incorrectNumScalled=100-correctNumScalled;

        hashMapExported.put(correct,correctNumScalled);
        hashMapExported.put(incorrect,incorrectNumScalled);
        hashMapExported.put(progress,progressNum);
        hashMapExported.put(sameScoringProbabiloty,sameScoringProbabilotyNum);
        hashMapExported.put(explain,explanation);
        System.out.println(hashMapExported);
        return hashMapExported;
    }

    public Object updateDashboardData(String userId, HashMap<String,Object> responseToBe) throws ExecutionException, InterruptedException {
///Instantiation of user responsedata
//
        HashMap<String,Object> responses= (HashMap<String, Object>) sectionsPerformanceService.generateResult(responseToBe);


        responses.put("use_id",userId);
        if(true){

            return responses;
        }
        HashMap<String,Object> generatedPracticeTestDashboard=(HashMap<String, Object>) performanceService.generateDashboardDataForPracticeTestResult(responses,userId);
        if(getPracticeTestAccumulatedResponse(userId)!=null)
        {
                    HashMap<String,Object>accumulatedResponse= (HashMap<String, Object>) getPracticeTestAccumulatedResponse(userId);
            if(true){

                return generatedPracticeTestDashboard;
            }
                    HashMap <String,Object>toBeUpdatedDashBoardData=addDashboardData(generatedPracticeTestDashboard,accumulatedResponse);
//                    if(true){
//                        return toBeUpdatedDashBoardData;
//                    }
                    performanceService.registerDashboardData(toBeUpdatedDashBoardData,userId);
        }


        if (true){
            return responses;
        }



        String test_id= (String) responses.get("test_id");

        System.out.println(responses);
        ArrayList<Sections>sections= (ArrayList<Sections>) responses.get("sections");
        HashMap<String,Object> response=new HashMap<>();
        response.put("test_id",test_id);
        response.put("sections",sections);


        ////---------------------------------
        String testId= (String) response.get("test_id");
        if(testId.contains("ractice")){
            ArrayList<Sections>sectionss= (ArrayList<Sections>) response.get("sections");
            String sectonId= sectionss.get(0).getSection_id();
//            if
        }
       //response1.setSections((ArrayList<Sections>) response.get("sections"));
//



        return response;
    }

    private HashMap<String,Object> addDashboardData(HashMap<String, Object> generatedPracticeTestDashboard, HashMap<String, Object> accumulatedResponse) {
        HashMap<String,Object>dashboardData=new HashMap<>();

        JSONObject json1Object = new JSONObject(generatedPracticeTestDashboard);
      JSONObject json2Object = new JSONObject(accumulatedResponse);
      // Math score parseing
        int advancedMathRightAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("right_answer");

        int advancedMathOutOf = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("out_of");

        int advancedMathWrongAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("wrong_answer");

        int averageRightAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageOutOf = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageWrongAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int heartOfAlgebraRightAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("right_answer");

        int heartOfAlgebraOutOf = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("out_of");

        int heartOfAlgebraWrongAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("wrong_answer");


        int problemSolvingAnalysisRightAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("right_answer");

        int problemSolvingAnalysisOutOf = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("out_of");

        int problemSolvingAnalysisWrongAnswer = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("wrong_answer");

        int advancedMathRightAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("right_answer");

        int advancedMathOutOf2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("out_of");

        int advancedMathWrongAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .getInt("wrong_answer");

        int averageRightAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageOutOf2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageWrongAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("wrong_answer");


        int heartOfAlgebraRightAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("right_answer");

        int heartOfAlgebraOutOf2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("out_of");

        int heartOfAlgebraWrongAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .getInt("wrong_answer");


        int problemSolvingAnalysisRightAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("right_answer");

        int problemSolvingAnalysisOutOf2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("out_of");

        int problemSolvingAnalysisWrongAnswer2 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("wrong_answer");

        int advancedMathRightAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("right_answer");

        int advancedMathOutOf1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("out_of");

        int advancedMathWrongAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("wrong_answer");

        int averageRightAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageOutOf1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageWrongAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int heartOfAlgebraRightAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("right_answer");

        int heartOfAlgebraOutOf1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("out_of");

        int heartOfAlgebraWrongAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("wrong_answer");


        int problemSolvingAnalysisRightAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("right_answer");

        int problemSolvingAnalysisOutOf1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("out_of");

        int problemSolvingAnalysisWrongAnswer1 = json1Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("wrong_answer");

        int advancedMathRightAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("right_answer");

        int advancedMathOutOf21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("out_of");

        int advancedMathWrongAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .getInt("wrong_answer");

        int averageRightAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageOutOf21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageWrongAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("wrong_answer");


        int heartOfAlgebraRightAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("right_answer");

        int heartOfAlgebraOutOf21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("out_of");

        int heartOfAlgebraWrongAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .getInt("wrong_answer");


        int problemSolvingAnalysisRightAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("right_answer");

        int problemSolvingAnalysisOutOf21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("out_of");

        int problemSolvingAnalysisWrongAnswer21 = json2Object.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .getInt("wrong_answer");
//// adding the math subscores
        long advancedMathRightAnswerNew=advancedMathRightAnswer+advancedMathRightAnswer2;
        long advancedMathOutOfNew=advancedMathOutOf+advancedMathOutOf2;
        long advancedMathWrongAnswerNew=advancedMathWrongAnswer+advancedMathWrongAnswer2;
        long averageRightAnswerNew=averageRightAnswer+averageRightAnswer2;
        long averageOutOfNew=averageOutOf+averageOutOf2;
        long averageWrongAnswerNew=averageWrongAnswer+averageWrongAnswer2;

        long heartOfAlgebraRightNew=heartOfAlgebraRightAnswer+heartOfAlgebraRightAnswer2;
        long heartOfAlgebraOutOfNew=heartOfAlgebraOutOf+heartOfAlgebraOutOf2;
        long heartOfAlgebraWrongAnswerNew=heartOfAlgebraWrongAnswer+heartOfAlgebraWrongAnswer2;

        long problemSolvingAnalysisRightAnswerNew=problemSolvingAnalysisRightAnswer+problemSolvingAnalysisRightAnswer2;
        long problemSolvingAnalysisOutOfNew=problemSolvingAnalysisOutOf+problemSolvingAnalysisOutOf2;
        long problemSolvingAnalysisWrongAnswerNew=problemSolvingAnalysisWrongAnswer+problemSolvingAnalysisWrongAnswer2;


        long advancedMathRightAnswerNew1=advancedMathRightAnswer1+advancedMathRightAnswer21;
        long advancedMathOutOfNew1=advancedMathOutOf1+advancedMathOutOf21;
        long advancedMathWrongAnswerNew1=advancedMathWrongAnswer1+advancedMathWrongAnswer21;
        long averageRightAnswerNew1=averageRightAnswer1+averageRightAnswer21;
        long averageOutOfNew1=averageOutOf1+averageOutOf21;
        long averageWrongAnswerNew1=averageWrongAnswer1+averageWrongAnswer21;

        long heartOfAlgebraRightNew1=heartOfAlgebraRightAnswer1+heartOfAlgebraRightAnswer21;
        long heartOfAlgebraOutOfNew1=heartOfAlgebraOutOf1+heartOfAlgebraOutOf21;
        long heartOfAlgebraWrongAnswerNew1=heartOfAlgebraWrongAnswer1+heartOfAlgebraWrongAnswer21;

        long problemSolvingAnalysisRightAnswerNew1=problemSolvingAnalysisRightAnswer1+problemSolvingAnalysisRightAnswer21;
        long problemSolvingAnalysisOutOfNew1=problemSolvingAnalysisOutOf1+problemSolvingAnalysisOutOf21;
        long problemSolvingAnalysisWrongAnswerNew1=problemSolvingAnalysisWrongAnswer1+problemSolvingAnalysisWrongAnswer21;
        /////

        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("Math_subscores", new JSONObject(json1Object.getJSONObject("Math_subscores").toString()));
        newJsonObject.put("Reading_and_Writing_subscores", new JSONObject(json2Object.getJSONObject("Reading_and_Writing_subscores").toString()));
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .put("right_answer", advancedMathRightAnswerNew);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .put("out_of", advancedMathOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("passport_advanced_math")
                .put("wrong_answer", advancedMathWrongAnswerNew);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("right_answer", averageRightAnswerNew);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("out_of", averageOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("wrong_answer", averageWrongAnswerNew);

        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .put("right_answer", heartOfAlgebraRightNew);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .put("out_of", heartOfAlgebraOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("heart_of_algebra")
                .put("wrong_answer", heartOfAlgebraWrongAnswerNew);

        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .put("right_answer", problemSolvingAnalysisRightAnswerNew);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .put("out_of", problemSolvingAnalysisOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("first_section")
                .getJSONObject("problem_solving_analysis")
                .put("wrong_answer", problemSolvingAnalysisWrongAnswerNew);

        /////
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .put("right_answer", advancedMathRightAnswerNew1);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .put("out_of", advancedMathOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("passport_advanced_math")
                .put("wrong_answer", advancedMathWrongAnswerNew1);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("right_answer", averageRightAnswerNew1);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("out_of", averageOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("wrong_answer", averageWrongAnswerNew1);

        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .put("right_answer", heartOfAlgebraRightNew1);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .put("out_of", heartOfAlgebraOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("heart_of_algebra")
                .put("wrong_answer", heartOfAlgebraWrongAnswerNew1);

        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .put("right_answer", problemSolvingAnalysisRightAnswerNew1);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .put("out_of", problemSolvingAnalysisOutOf);
        newJsonObject.getJSONObject("Math_subscores")
                .getJSONObject("second_section")
                .getJSONObject("problem_solving_analysis")
                .put("wrong_answer", problemSolvingAnalysisWrongAnswerNew1);
///////////////////////////////////////////////////////////////////////////
        int scienceScore = json1Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("score");

        int scienceRightAnswer = json1Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("right_answer");

        int scienceWrongAnswer = json1Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("wrong_answer");

        int historyScore = json1Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("score");

        int historyRightAnswer = json1Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("right_answer");

        int historyWrongAnswer = json1Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("wrong_answer");

        int scienceScore1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("score");

        int scienceRightAnswer1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("right_answer");

        int scienceWrongAnswer1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .getInt("wrong_answer");

        int historyScore1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("score");

        int historyRightAnswer1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("right_answer");

        int historyWrongAnswer1 = json2Object.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .getInt("wrong_answer");
        long scienceScoreNew=scienceScore+scienceScore1;
        long scienceRightAnswerNew=scienceRightAnswer+scienceRightAnswer1;
        long scienceWrongAnswerNew=scienceWrongAnswer+scienceWrongAnswer1;
        long historyScoreNew=historyScore+historyScore1;
        long historyRightAnswerNew=historyRightAnswer+historyRightAnswer1;
        long historyWrongAnswerNew=historyWrongAnswer+historyWrongAnswer1;

        ///
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .put("score", scienceScoreNew);
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .put("right_answer",scienceRightAnswerNew);
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("science_score")
                .put("wrong_answer",scienceWrongAnswerNew);
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .put("score",historyScoreNew);
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .put("right_answer",historyRightAnswerNew);
        newJsonObject.getJSONObject("cross_test")
                .getJSONObject("history_score")
                .put("wrong_answer",historyWrongAnswerNew);
        //////

        int averageReadingRightAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageReadingOutOf = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageReadingWrongAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int wordsInContextRightAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("right_answer");

        int wordsInContextOutOf = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("out_of");

        int wordsInContextWrongAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("wrong_answer");

        int expressionOfIdeasRightAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("right_answer");

        int expressionOfIdeasOutOf = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("out_of");

        int expressionOfIdeasWrongAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("wrong_answer");

        int commandOfEvidenceRightAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("right_answer");

        int commandOfEvidenceOutOf = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("out_of");

        int commandOfEvidenceWrongAnswer = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("wrong_answer");

        int averageReadingRightAnswer1= json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageReadingOutOf1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageReadingWrongAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int wordsInContextRightAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("right_answer");

        int wordsInContextOutOf1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("out_of");

        int wordsInContextWrongAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("wrong_answer");

        int expressionOfIdeasRightAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("right_answer");

        int expressionOfIdeasOutOf1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("out_of");

        int expressionOfIdeasWrongAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("wrong_answer");

        int commandOfEvidenceRightAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("right_answer");

        int commandOfEvidenceOutOf1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("out_of");

        int commandOfEvidenceWrongAnswer1 = json1Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("wrong_answer");

        int averageReadingRightAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageReadingOutOf2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageReadingWrongAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int wordsInContextRightAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("right_answer");

        int wordsInContextOutOf2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("out_of");

        int wordsInContextWrongAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .getInt("wrong_answer");

        int expressionOfIdeasRightAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("right_answer");

        int expressionOfIdeasOutOf2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("out_of");

        int expressionOfIdeasWrongAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("wrong_answer");

        int commandOfEvidenceRightAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("right_answer");

        int commandOfEvidenceOutOf2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("out_of");

        int commandOfEvidenceWrongAnswer2 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .getInt("wrong_answer");

        int averageReadingRightAnswer21= json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("right_answer");

        int averageReadingOutOf21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("out_of");

        int averageReadingWrongAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .getInt("wrong_answer");

        int wordsInContextRightAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("right_answer");

        int wordsInContextOutOf21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("out_of");

        int wordsInContextWrongAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .getInt("wrong_answer");

        int expressionOfIdeasRightAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("right_answer");

        int expressionOfIdeasOutOf21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("out_of");

        int expressionOfIdeasWrongAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .getInt("wrong_answer");

        int commandOfEvidenceRightAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("right_answer");

        int commandOfEvidenceOutOf21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("out_of");

        int commandOfEvidenceWrongAnswer21 = json2Object.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .getInt("wrong_answer");








        long averageReadingRightAnswerNew=averageReadingRightAnswer+averageReadingRightAnswer2;
        long averageReadingOutOfNew=averageReadingOutOf+averageReadingOutOf2;
        long averageReadingWrongAnswerNew=averageReadingWrongAnswer+averageReadingWrongAnswer2;
        long wordsInContextRightAnswerNew=wordsInContextRightAnswer+wordsInContextRightAnswer2;
        long wordsInContextOutOfNew=wordsInContextOutOf+wordsInContextOutOf2;
        long wordsInContextWrongAnswerNew=wordsInContextWrongAnswer+wordsInContextWrongAnswer2;
        long expressionOfIdeasRightAnswerNew=expressionOfIdeasRightAnswer+expressionOfIdeasRightAnswer2;
        long expressionOfIdeasOutOfNew=expressionOfIdeasOutOf+expressionOfIdeasOutOf2;
        long expressionOfIdeasWrongAnswerNew=expressionOfIdeasWrongAnswer+expressionOfIdeasWrongAnswer2;
        long commandOfEvidenceRightAnswerNew=commandOfEvidenceRightAnswer+commandOfEvidenceRightAnswer2;
        long commandOfEvidenceOutOfNew=commandOfEvidenceOutOf+commandOfEvidenceOutOf2;
        long commandOfEvidenceWrongAnswerNew=commandOfEvidenceWrongAnswer+commandOfEvidenceWrongAnswer2;

        long averageReadingRightAnswerNew1=averageReadingRightAnswer1+averageReadingRightAnswer21;
        long averageReadingOutOfNew1=averageReadingOutOf1+averageReadingOutOf21;
        long averageReadingWrongAnswerNew1=averageReadingWrongAnswer1+averageReadingWrongAnswer21;
        long wordsInContextRightAnswerNew1=wordsInContextRightAnswer1+wordsInContextRightAnswer21;
        long wordsInContextOutOfNew1=wordsInContextOutOf1+wordsInContextOutOf21;
        long wordsInContextWrongAnswerNew1=wordsInContextWrongAnswer1+wordsInContextWrongAnswer21;
        long expressionOfIdeasRightAnswerNew1=expressionOfIdeasRightAnswer1+expressionOfIdeasRightAnswer21;
        long expressionOfIdeasOutOfNew1=expressionOfIdeasOutOf1+expressionOfIdeasOutOf21;
        long expressionOfIdeasWrongAnswerNew1=expressionOfIdeasWrongAnswer1+expressionOfIdeasWrongAnswer21;
        long commandOfEvidenceRightAnswerNew1=commandOfEvidenceRightAnswer1+commandOfEvidenceRightAnswer21;
        long commandOfEvidenceOutOfNew1=commandOfEvidenceOutOf1+commandOfEvidenceOutOf21;
        long commandOfEvidenceWrongAnswerNew1=commandOfEvidenceWrongAnswer1+commandOfEvidenceWrongAnswer21;

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("right_answer",averageReadingRightAnswerNew);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("out_of",averageReadingOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("average")
                .put("wrong_answer",averageReadingWrongAnswerNew);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .put("right_answer",wordsInContextRightAnswerNew);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .put("out_of",wordsInContextOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("words_in_context")
                .put("wrong_answer",wordsInContextWrongAnswerNew);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .put("right_answer",expressionOfIdeasRightAnswerNew);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .put("out_of",expressionOfIdeasOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("expression_of_Ideas")
                .put("wrong_answer",expressionOfIdeasWrongAnswerNew);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .put("right_answer",commandOfEvidenceRightAnswerNew);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .put("out_of",commandOfEvidenceOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("first_section")
                .getJSONObject("command_of_evidence")
                .put("wrong_answer",commandOfEvidenceWrongAnswerNew);

        ////
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("right_answer",averageReadingRightAnswerNew1);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("out_of",averageReadingOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("average")
                .put("wrong_answer",averageReadingWrongAnswerNew1);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .put("right_answer",wordsInContextRightAnswerNew1);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .put("out_of",wordsInContextOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("words_in_context")
                .put("wrong_answer",wordsInContextWrongAnswerNew1);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .put("right_answer",expressionOfIdeasRightAnswerNew1);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .put("out_of",expressionOfIdeasOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("expression_of_Ideas")
                .put("wrong_answer",expressionOfIdeasWrongAnswerNew1);

        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .put("right_answer",commandOfEvidenceRightAnswerNew1);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .put("out_of",commandOfEvidenceOutOf);
        newJsonObject.getJSONObject("Reading_and_Writing_subscores")
                .getJSONObject("second_section")
                .getJSONObject("command_of_evidence")
                .put("wrong_answer",commandOfEvidenceWrongAnswerNew1);
        for (String key : newJsonObject.keySet()) {
            // Add the key-value pair to the HashMap
            dashboardData.put(key, newJsonObject.get(key));
        }

        return dashboardData;
    }

    public Object getPracticeTestAccumulatedResponse(String userId){
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference2 = zgjUfirestore.collection("practiceTestUserResponse").document(userId);
        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();
        long correctNum= 0;
        HashMap<String, Object> accumulatedResponse=new HashMap<>();
        try {
            DocumentSnapshot documentSnapshot = future2.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                 accumulatedResponse = new HashMap<>((Map) document);

//                correctNum= (Long) linkedHashMap.get("correct");
//                System.out.println(correctNum);

            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return accumulatedResponse;
    }

    public Long getPracticeTestRealTimeScore(String userId){
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference2 = zgjUfirestore.collection("practiceTestRealTimeScore").document(userId);
        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();
        long correctNum= 0;
        try {
            DocumentSnapshot documentSnapshot = future2.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                document = documentSnapshot.toObject(Object.class);

                HashMap<String, Object> linkedHashMap = new HashMap<>((Map) document);

                correctNum= (Long) linkedHashMap.get("correct");
                System.out.println(correctNum);

            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return correctNum;
    }
    public String changePassword(String email, String password, String otp) throws ExecutionException, InterruptedException {
        //otp then new password


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
    public void udpdatePracticeTestRealTimeScore(Long correctNum, String userId) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        long temp1= getPracticeTestRealTimeScore(userId);
        //System.out.println(correctNum);
        //System.out.println(temp1);
       // DocumentReference documentReference3 = zgjUfirestore.collection("rawDiagnosticScore").document(userId);
       // ApiFuture<WriteResult> future = documentReference3.update("correct",correctNum+temp1);
       // ApiFuture<DocumentSnapshot> future3 = documentReference3.get();
       // WriteResult result = future.get();
        //System.out.println("Update time : " + result.getUpdateTime());
        ///

       // CollectionReference documentReference = zgjUfirestore.collection("rawDiagnosticScore");
        HashMap<String, Object>updated= new HashMap<>();
        updated.put("correct",correctNum+temp1);
        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("practiceTestRealTimeScore").document(userId).set(updated);
        try {
            System.out.println( collectionApifuture.get().getUpdateTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
//        Query query = documentReference.whereEqualTo("email", userId);
//        ApiFuture<QuerySnapshot> future = query.get();
//        QuerySnapshot querySnapshot = future.get();

//        if (!querySnapshot.isEmpty()) {
//
//            // document exists
//            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
//            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
//
//            if (document.exists()) {
//
//                docRef.update("score", temp1+correctNum);
//
//                System.out.println("Password Changed successfully!");
//            } else {
//                System.out.println("user not found!");
//            }
//        try {
//
//
//
//
//            DocumentSnapshot documentSnapshot = future3.get();
//
//
//            Object document;
//            if (documentSnapshot.exists()) {
//
//                // document exists//
//               //
//                HashMap<String,Long>updated= new HashMap<>();
//
//
//                updated.put("correct",temp2);
//                System.out.println(updated);
//
//                documentReference3.update("correct",temp2);
//
//                document = documentSnapshot.toObject(Object.class);
//
//            }
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
       // System.out.println("updated");
    }

        public Object registerPracticeTest(Object test) {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        Test test1= (Test)test;
        HashMap<String, Object> hashMapData = (HashMap<String, Object>) test;
        String testIdd = hashMapData.keySet().iterator().next();
        String test_Id = (String) hashMapData.get(testIdd);

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("practicetestResult").document(test_Id).set(hashMapData);


        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
