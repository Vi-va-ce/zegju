package com.example.zegeju.Service;

import com.example.zegeju.Domain.HomePage.*;
import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class HomePageGenerator {
        ///messages

    @Autowired
        private PaymentDetails paymentDetailsReference;
    @Autowired
        private ButtonStatus buttonStatus;
        HashMap<String,String>testTypes=new HashMap<>();






        public String generateHomePageData(String email){
                HomePageData homePageData = new HomePageData();
                SatData satData= new SatData();
                ToeflData toeflData=new ToeflData();
                GatData gatData=new GatData();
                GreData greData=new GreData();
                IeltsData ieltsData = new IeltsData();
                MatricData matricData = new MatricData();


                PaymentDetails paymentDetails= new PaymentDetails();
                String buttonStatus1= new String();


                homePageData.setEmail(email);
                paymentDetails.setBasicPackage(false);
                paymentDetails.setTrialPackage(false);
                paymentDetails.setPremiumPackage(false);
                buttonStatus1=buttonStatus.getSEEPLANS();
                satData.setPaymentDetails(paymentDetails);
                satData.setButtonStatus(buttonStatus1);

                toeflData.setPaymentDetails(paymentDetails);
                toeflData.setButtonStatus(buttonStatus1);
                gatData.setPaymentDetails(paymentDetails);
                gatData.setButtonStatus(buttonStatus1);
                greData.setPaymentDetails(paymentDetails);
                greData.setButtonStatus(buttonStatus1);
                ieltsData.setPaymentDetails(paymentDetails);
                ieltsData.setButtonStatus(buttonStatus1);
                matricData.setPaymentDetails(paymentDetails);
                matricData.setButtonStatus(buttonStatus1);

                homePageData.setSatData(satData);
                homePageData.setToeflData(toeflData);
                homePageData.setGreData(greData);
                homePageData.setMatricData(matricData);
                homePageData.setIeltsData(ieltsData);
                homePageData.setGatData(gatData);



            Firestore zgjUfirestore = FirestoreClient.getFirestore();


            ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("homePageData").document(email).set(homePageData);
            try {
               return collectionApifuture.get().getUpdateTime().toString();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }


        public Object getHomePageData(String email) throws ExecutionException, InterruptedException {
            Firestore zgjUfirestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = zgjUfirestore.collection("homePageData").document(email);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot documentSnapshot = future.get();


            Object document;
            if (documentSnapshot.exists()) {

                // document exists

                document = documentSnapshot.toObject(Object.class);
                HashMap<String, Object> hashMapData = (HashMap<String, Object>) document;

                return hashMapData;


            }

            return "Error";

        }

        public Object updateHomepageFiled(String email,String typeOfTest, Object feildname) throws ExecutionException, InterruptedException {
            Firestore zgjUfirestore = FirestoreClient.getFirestore();
            CollectionReference documentReference = zgjUfirestore.collection("homePageData");
            Query query = documentReference.whereEqualTo("email", email);
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot querySnapshot = future.get();
            Student student;
            if (!querySnapshot.isEmpty()) {

                // document exists
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

                if (document.exists()) {

                    docRef.update("password", feildname);

                    System.out.println("User password updated successfully!");
                } else {
                    System.out.println("User not found!");
                }
                return null;
            }
            return null;
        };


        /// data what the home page needs








        ///genarateing the homepage
}
