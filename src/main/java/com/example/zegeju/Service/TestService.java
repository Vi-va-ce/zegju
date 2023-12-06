package com.example.zegeju.Service;

import com.example.zegeju.Domain.Test.*;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class TestService {

    public Object getTest(String test_id) throws ExecutionException, InterruptedException, IOException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
       DocumentReference documentReference = zgjUfirestore.collection("tests").document(test_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();


        Object document;
        if (documentSnapshot.exists()) {

            // document exists

            document = documentSnapshot.toObject(Object.class);
            HashMap<String, Object> hashMapData = (HashMap<String, Object>) document;

            return hashMapData;


        }

        return "no test is found";
    }

    public String putTest(){
        Test test = new Test();
        test.setTest_id("diagnostic_Test");
        QuestionGroup questionGroup= new QuestionGroup();
        questionGroup.setQuestion_group_id("MATH_CALCULATOR");
        Section section = new Section();
        section.setSection_id("Math_Section");
        QuestionType questionType = new QuestionType();
        questionType.setQuestion_type_id("Choose");

        Questions question = new Questions();
        question.setQuestion_id("ch_1");
        question.setQuestion_text("Solve for x: 2x + 3 = 7");
//        question.setChoose_A("x = 2");

//        question.setChoose_D("X=9");
        question.setSub_section("Algebra");

// Add the question to the question type
        questionType.getQuestions().add(question);

// Add the question type to the section
        questionGroup.getQuestion_types().add(questionType);
        section.getQuestionGroups().add(questionGroup);

// Add the section to the test
        test.getSections().add(section);


        System.out.println(test.getTest_id());

// Store the test object in Firestore
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference testsCollection = zgjUfirestore.collection("tests");
        DocumentReference testDocument = testsCollection.document(test.getTest_id());
        testDocument.set(test);



        return "success";
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


    public Object registerTest(Object test) {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        Test test1= (Test)test;
        HashMap<String, Object> hashMapData = (HashMap<String, Object>) test;
        String testIdd= hashMapData.keySet().iterator().next();
        String test_Id= (String) hashMapData.get(testIdd);

//        if(true){
//            return hashMapData;
//        }
//
//        String testId= test.getTest_id();
//        if (true){
//            return test;
//        }
//        List<Section>sectionList= test.getSections();
//        String section1 =sectionList.get(0).getSection_id();
//        List<QuestionGroup>questionGroups=sectionList.get(0).getQuestionGroups();
//
//        String questionGroupId1= questionGroups.get(0).getQuestion_group_id();
//        List<QuestionType>questionTypeList= questionGroups.get(0).getQuestion_types();
//
//        String questionTypeId1= questionTypeList.get(0).getQuestion_type_id();
//        List<Questions>questions=questionTypeList.get(0).getQuestions();
//
//        String question_id1= questions.get(0).getQuestion_id();
//        String question_text1= questions.get(0).getQuestion_text();
//        List<Choice>choiceList= questions.get(0).getChoice();
//        String sub_Section1= questions.get(0).getSub_section();
//
//        String choiceId1= choiceList.get(0).getChoose_id();
//        String choiceText1= choiceList.get(0).getChoose_text();
//        String choiceId2= choiceList.get(0).getChoose_id();
//        String choiceText2= choiceList.get(0).getChoose_text();
//        String choiceId3= choiceList.get(0).getChoose_id();
//        String choiceText3= choiceList.get(0).getChoose_text();
//        String choiceId4= choiceList.get(0).getChoose_id();
//        String choiceText4= choiceList.get(0).getChoose_text();
//
//
//
//        HashMap<String,String>choice= new HashMap<>();
//        choice.put(choiceId1,choiceText1);
//        choice.put(choiceId2,choiceText2);
//        choice.put(choiceId3,choiceText3);
//        choice.put(choiceId4,choiceText4);
//
//
//
//        Question question1= new Question();
//        question1.setQuestion_id(question_id1);
//        question1.setQuestion_text(question_text1);
//        question1.setChoice(choice);
//        question1.setSub_section(sub_Section1);
//
//        HashMap<String,Question> questionTypeData=new HashMap<>();
//        questionTypeData.put(questionTypeId1,question1);
//
//        HashMap<String,HashMap>questionGroupss=new HashMap<>();
//        questionGroupss.put(questionGroupId1,questionTypeData);
//
//        HashMap<String,HashMap>sectionData= new HashMap<>();
//        sectionData.put(section1,questionGroupss);
//
//        HashMap<String,HashMap>sections=new HashMap<>();
//        sections.put(testId,sectionData);
//        HashMap<String,HashMap>testData = new HashMap<>();
//
//        testData.put("1",sections);




//        mapNestedJsonToHashMap(hashMap, "", json);


//
//
//        HashMap<String,Object> testData= (HashMap<String, Object>) hashMapData.get("tests");
//
//        Test test1 = new Test();
//
//        test1.setTest_id((String) testData.get("test_id"));
//
//        List<Section> sections = new ArrayList<>();
//        HashMap<String, Object> sectionsData = (HashMap<String, Object>) testData.get("sections");
//        Section section = new Section();
//        section.setSection_id((String) sectionsData.get("section_id"));
//
//        List<QuestionGroup>questionGroups=new ArrayList<>();
//        QuestionGroup questionGroup=new QuestionGroup();
//        questionGroup.setQuestion_group_id("MATH_CALCULATOR");
//
//        List<QuestionType> questionTypes = new ArrayList<>();
//        HashMap<String, Object> questionTypesData = (HashMap<String, Object>) sectionsData.get("question_types");
//
//        QuestionType questionType = new QuestionType();
//        questionType.setQuestion_type_id((String) questionTypesData.get("question_type_id"));
//
//        List<Question> questions = new ArrayList<>();
//        HashMap<String, Object> questionsData = (HashMap<String, Object>) questionTypesData.get("questions");
//
//        Question question = new Question();
//        question.setQuestion_id((String) questionsData.get("question_id"));
//        question.setQuestion_text((String) questionsData.get("question_text"));
//        question.setChoose_A((String) questionsData.get("choose_A"));
//        question.setChoose_B((String) questionsData.get("choose_B"));
//        question.setChoose_C((String) questionsData.get("choose_C"));
//        question.setChoose_D((String) questionsData.get("choose_D"));
//        question.setSub_section((String) questionsData.get("sub_section"));
//
//        questions.add(question);
//        questionType.setQuestions(questions);
//        questionTypes.add(questionType);
//
//        questionGroup.setQuestion_types(questionTypes);
//        questionGroups.add(questionGroup);
//
//
//        section.setQuestionGroups(questionGroups);
//        sections.add(section);
//
//        test1.setSections(sections);
        String ttt="diagnosticTestOne";
        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("tests").document(test_Id).set(hashMapData);


        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}


