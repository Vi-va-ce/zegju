package com.example.zegeju.Service;

import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PracticeTestService {
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

    public HashMap<String, Object> generateRealTimeResult(HashMap<String,Object> response) {
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
