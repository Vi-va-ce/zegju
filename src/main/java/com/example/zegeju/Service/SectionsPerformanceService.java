package com.example.zegeju.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class SectionsPerformanceService {
    public Object generateResult(HashMap<String,Object> userResponse) throws ExecutionException, InterruptedException {
        //HashMap<String,Object>userResponse= (HashMap<String, Object>) userResponss;
        HashMap<String, Object> hashMapData = (HashMap<String, Object>) userResponse;
        //if (true) return (userResponse);
        Set<String> keys= hashMapData.keySet();
        List<String> keyList = new ArrayList<>(keys);
        String testid= keyList.get(0);//test_id


        HashMap<String,Object>userResponseSection= (HashMap<String, Object>) userResponse.get(testid);

        Set<String>keys2=userResponseSection.keySet();
        List<String> keyList2 = new ArrayList<>(keys2);
//        System.out.println(userResponseSection);
        String sectionId= keyList2.get(0);//section id
        HashMap<String,Object>responses= (HashMap<String, Object>) userResponseSection.get(sectionId);
        Set<String>keys3=responses.keySet();
//---------- data to be generated
        ArrayList<Object> sections= new ArrayList<>();// sections array
        ArrayList<String>sectionLists= new ArrayList<>();
        sectionLists.add("Reading_Section");//at index 0
        sectionLists.add("writing_Section");//at index 1

        sectionLists.add("math_Section");//at index 2
            ArrayList<Object>questionGroups= new ArrayList<>();
            ArrayList<Object>questionGroupLists= new ArrayList<>();
            questionGroupLists.add("MATH_CALCULATOR");
            questionGroupLists.add("MATH_NO_CALCULATOR");



        ArrayList<Object>userResponses=new ArrayList<>();
        String userResponsesid="user_responses";

        String responseId="response";
        String questionId="question_id";

        if (testid.contains("diagnostic")&& sectionId.contains("READING")){
            for(String elements:keys3){
                HashMap<String,Object>userResponsess=new HashMap<>();
                userResponsess.put(questionId,elements);
                userResponsess.put(responseId,responses.get(elements));
                userResponses.add(userResponsess);

                userResponsess=null;
            }
            HashMap<String,Object>userResponsessSection=new HashMap<>();
            HashMap<String,Object>userResponsessSection1=new HashMap<>();
            HashMap<String,Object>userResponsessSection2=new HashMap<>();
            HashMap<String,Object>userResponsessQuestionGroup=new HashMap<>();
            HashMap<String,Object>userResponsessQuestionGroup2=new HashMap<>();

            userResponsessSection.put(userResponsesid,userResponses);
            userResponsessSection.put("section_id","Reading_Section");
            sections.add(userResponsessSection);



            userResponsessSection1.put(userResponsesid,userResponses);
            userResponsessSection1.put("section_id","writing_Section");
            sections.add(userResponsessSection1);
            //System.out.println(sections);




            userResponsessQuestionGroup.put(userResponsesid,userResponses);
            userResponsessQuestionGroup.put("question_group_id","MATH_CALCULATOR");
            questionGroups.add(userResponsessQuestionGroup);
            //System.out.println(questionGroups);

            //System.out.println(userResponsessQuestionGroup);

            userResponsessQuestionGroup2.put("question_group_id","MATH_NO_CALCULATOR");
            userResponsessQuestionGroup2.put(userResponsesid,userResponses);
            questionGroups.add(userResponsessQuestionGroup2);


            HashMap<String,Object>userResponsessMath=new HashMap<>();
            userResponsessMath.put("question_groups",questionGroups);
            userResponsessMath.put("section_id","math_Section");

            sections.add(userResponsessMath);

            HashMap<String,Object>userResponsessTest=new HashMap<>();
            userResponsessTest.put("sections",sections);
            userResponsessTest.put("test_id","diagnostic_testOne");
            System.out.println(userResponsessTest);
            return userResponsessTest;
            ///email should be added here

        }
        else if(testid.contains("diagnostic") && sectionId.contains("WRITING")){

            for(String elements:keys3){
                HashMap<String,Object>userResponsess=new HashMap<>();
                userResponsess.put(questionId,elements);
                userResponsess.put(responseId,responses.get(elements));
                userResponses.add(userResponsess);
                userResponsess=null;
            }
            HashMap<String,Object>userResponsessSection=new HashMap<>();
            userResponsessSection.put(userResponsesid,userResponses);
            userResponsessSection.put("section_id",sectionLists.get(1));

            sections.add(userResponsessSection);
            HashMap<String,Object>userResponsessTest=new HashMap<>();
            userResponsessTest.put("section",userResponsessSection);

            userResponsessTest.put("test_id","diagnostic_testOne");
            return userResponsessTest;
        }




//
//
//        String sectionsKeyName="sections";
//            if (testid.contains("diagnostic")  ){
//                HashMap<String, Object> hashMapData2= (HashMap<String, Object>) userResponse.get(testid);
//                HashMap<String,Object>hashMapData3=(HashMap<String, Object>) hashMapData2.get("READING section");
//                return
////                Firestore zgjUfirestore = FirestoreClient.getFirestore();
////                ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("userResponses").document("diagnostic_testOne").set(userResponse);
////
////                try {
////                    return collectionApifuture.get().getUpdateTime().toString();
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                } catch (ExecutionException e) {
////                    throw new RuntimeException(e);
////                }
//            }
//
//            System.out.println(testid);


            /*
            {
  "test_id":"diagnostic_testOne",
  "sections":
             [
                    {
                      "section_id":"Reading_Section",
                       "user_responses":[
                              {"question_id":"ch_1",
                               "response":"choose_B"
                                },
                              { "question_id":"ch_2",
                               "response":"choose_A"
                                },
                               { "question_id":"ch_3",
                               "response":"choose_D"
                                },
                              { "question_id":"ch_4",
                               "response":"choose_C"
                                },
                               { "question_id":"ch_5",
                               "response":"choose_B"
                                }
                          ]
                      },
                     {
                      "section_id":"writing_Section",
                       "user_responses":[
                              {"question_id":"ch_1",
                               "response":"choose_B"
                                },
                              { "question_id":"ch_2",
                               "response":"choose_A"
                                },
                               { "question_id":"ch_3",
                               "response":"choose_D"
                                },
                              { "question_id":"ch_4",
                               "response":"choose_C"
                                },
                               { "question_id":"ch_5",
                               "response":"choose_B"
                                }
                          ]

                      },
                       {
                      "section_id":"math_Section",
                       "question_groups":[
                                          {
                                                  "question_group_id":"MATH_CALCULATOR",
                                                  "user_responses":[
                                                                   {"question_id":"ch_1",
                                                                    "response":"choose_B"
                                                                   },
                                                                  { "question_id":"ch_2",
                                                                    "response":"choose_A"
                                                                   },
                                                                  { "question_id":"ch_3",
                                                                    "response":"choose_D"
                                                                  },
                                                                 { "question_id":"ch_4",
                                                                   "response":"choose_C"
                                                                   },
                                                                { "question_id":"ch_5",
                                                                  "response":"choose_B"
                                                                 }
                                                              ]
                                           },
                                          {
                                                  "question_group_id":"MATH_NO_CALCULATOR",
                                                  "user_responses":[
                                                                   {"question_id":"ch_1",
                                                                    "response":"choose_B"
                                                                   },
                                                                  { "question_id":"ch_2",
                                                                    "response":"choose_A"
                                                                   },
                                                                  { "question_id":"ch_3",
                                                                    "response":"choose_D"
                                                                  },
                                                                 { "question_id":"ch_4",
                                                                   "response":"choose_C"
                                                                   },
                                                                { "question_id":"ch_5",
                                                                  "response":"choose_B"
                                                                 }
                                                              ]


                                           }
                                  ]


                      }
]
}
            */



    return null;
    }

}
