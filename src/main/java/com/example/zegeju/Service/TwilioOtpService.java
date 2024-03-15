//package com.example.zegeju.Service;
//
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.*;
//import com.google.firebase.cloud.FirestoreClient;
//import com.twilio.Twilio;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import com.twilio.rest.api.v2010.account.Message;
//
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class TwilioOtpService {
//
//    @Value("${firebase.apiKey}")
//    private String firebaseApiKey="AIzaSyArBtsAD7LO_M-nKKH1YGzKoNRoB2yze98";
//    private static final String API_KEY = "AIzaSyArBtsAD7LO_M-nKKH1YGzKoNRoB2yze98";
//    private static final String AUTH_API_URL = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + API_KEY;
//    @Value("${twilio.accountSid}")
//    private String ACCOUNT_SID;
//
//    @Value("${twilio.authToken}")
//    private String AUTH_TOKEN;
//
//    @Value("${twilio.phoneNumber}")
//    private String phoneNumber;
//
////    public void sendOtp(String phoneNumber, String otp) throws FirebaseAuthException {
//////        RestTemplate restTemplate = new RestTemplate();
//////        HttpHeaders headers = new HttpHeaders();
//////        headers.setContentType(MediaType.APPLICATION_JSON);
//////        headers.setBearerAuth(firebaseApiKey);
//////
//////        // Set up the request body with the phone number and OTP
//////        String requestBody = "{\"phoneNumber\": \"" + phoneNumber + "\", \"code\": \"" + otp + "\"}";
//////
//////        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//////
//////        try {
//////            // Make a POST request to send OTP
//////            ResponseEntity<String> response = restTemplate.exchange(
/////,
//////                    HttpMethod.POST,
//////                    entity,
//////                    String.class
//////            );
//////
//////            // Handle the response
//////            if (response.getStatusCode() == HttpStatus.OK) {
//////                // OTP sent successfully
//////                System.out.println("OTP sent to " + phoneNumber);
//////            } else {
//////                // Error occurred during OTP sending
//////                System.out.println("Error sending OTP: " + response.getBody());
//////            }
//////        } catch (HttpClientErrorException e) {
//////            // Handle HTTP client errors
//////            System.out.println("HTTP client error: " + e.getRawStatusCode() + " - " + e.getStatusText());
//////        } catch (Exception e) {
//////            // Handle other exceptions
//////            System.out.println("Error sending OTP: " + e.getMessage());
//////        }
////
////
////            MediaType mediaType = MediaType.parseMediaType("application/json");
////
////            String requestBody = "{\"phoneNumber\":\"" + phoneNumber + "\",\"otp\":\"" + otp + "\",\"recaptchaToken\":\"YOUR_RECAPTCHA_TOKEN\"}";
////
////            RequestBody body = RequestBody.create(mediaType, requestBody);
////
////            Request request = new Request.Builder()
////                    .url(AUTH_API_URL)
////                    .post(body)
////                    .addHeader("Content-Type", "application/json")
////                    .build();
////
////            Call call = client.newCall(request);
////            Response response = call.execute();
////
////            if (response.isSuccessful()) {
////                System.out.println("OTP sent successfully");
////            } else {
////                System.out.println("Failed to send OTP. Error: " + response.body().string());
////            }
////
//
////public void sendSms(PhoneAuthRequest phone) {
////
////    PhoneAuthProvider.getInstance().verifyPhoneNumber(
////            phone.getPhoneNumber(),
////            60L,
////            TimeUnit.SECONDS,
////            Executors.newScheduledThreadPool(1),
////            callbacks
////    );
////
////}
//    public void sendOTP(String phone_Number, String otp) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
////        Message message = Message.creator(
////                        new com.twilio.type.PhoneNumber("+14159352345"),
////                        new com.twilio.type.PhoneNumber("+14158141829"),
////                        "Where's Wallace?")
////                .create();
//        String messageBody = "Your OTP is: " + otp;
//        System.out.println(phone_Number);
//        Message message = Message.creator(new com.twilio.type.PhoneNumber(phone_Number), new com.twilio.type.PhoneNumber(phoneNumber), messageBody).create();
//
//        System.out.println(message.getSid());
//
//        // Create a JSON payload with the phone number
//
//    }
////
//    public String verifyPhoneNumber(String verificationCode) {
//
//        Firestore zgjUfirestore = FirestoreClient.getFirestore();
//        CollectionReference documentReference = zgjUfirestore.collection("student_user");
//        Query query = documentReference.whereEqualTo("otp", verificationCode);
//        ApiFuture<QuerySnapshot> future = query.get();
//        try {
//            QuerySnapshot querySnapshot = future.get();
//
//
//            if (!querySnapshot.isEmpty()) {
//
//                DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
//
//                ApiFuture<WriteResult> deleteResult = docRef.delete();
//
//                deleteResult.get();
//
//                return "User Successfully verified";
//            }
//
//            return "Otp verification failed";
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}