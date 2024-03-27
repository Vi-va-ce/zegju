package com.example.zegeju.Service;

import com.example.zegeju.Domain.PhoneRequest;
import com.example.zegeju.Domain.Student.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import jakarta.servlet.http.Cookie;
import org.springframework.http.*;
import com.google.firebase.cloud.FirestoreClient;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class   StudentService {
//    @Autowired
//    private TwilioOtpService twilioOtpService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private MapService mapService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private HomePageGenerator homePageGenerator;

    private final String apiKey="AIzaSyArBtsAD7LO_M-nKKH1YGzKoNRoB2yze98";

    public String createStudent(Student stud) throws ExecutionException, InterruptedException {
        System.out.println(stud);
        long phoneNumber=stud.getPhoneNumber();
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        int otp = Integer.parseInt(RandomStringUtils.randomNumeric(6));
        stud.setVerified(true);
        stud.setOtp(otp);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                stud.getPhone_no(),
//                60,
//                TimeUnit.SECONDS,
//                Executors.newScheduledThreadPool(1),
//                callbacks);
      //  twilioOtpService.sendOTP("+251"+String.valueOf(stud.getPhoneNumber()),String.valueOf(stud.getOtp()));
        //        FirebaseToken token = firebaseAuth.generatePhoneAuthCode(xphoneNumber);
//        String verificationId = token.getUid();

        //authenticatePhoneNumber("+251"+String.valueOf(stud.getPhoneNumber()),String.valueOf(stud.getOtp()) );
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        String email = stud.getEmail();
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Query query2 = documentReference.whereEqualTo("phone_no", phoneNumber);
        ApiFuture<QuerySnapshot> future2 = query2.get();
        QuerySnapshot querySnapshot2 = future2.get();
        Student student;
        if (querySnapshot.isEmpty()) {
            if (querySnapshot2.isEmpty()) {
                ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("student_user").document(stud.getFirstName()).set(stud);
                homePageGenerator.generateHomePageData(email);
                System.out.println(collectionApifuture.get().getUpdateTime().toString());
            }
            else {
                return "Sorry a user with this phone number already exists";
            }
            return "registered";
        }

        return "Sorry a user with this email already exists";
    }
//    import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.MediaType;5
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
public void authenticatePhoneNumber(String phoneNumber, String verificationCode) {
    RestTemplate restTemplate = new RestTemplate();

    //headers.set("Authorization", "Bearer AIzaSyArBtsAD7LO_M-nKKH1YGzKoNRoB2yze98");
    // Set request headers
    //the request body
    String requestBody = String.format("{\"phoneNumber\": \"%s\", \"code\": \"%s\"}", phoneNumber, verificationCode);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("User-Agent", "zegeju/1.0");



    UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("https://identitytoolkit.googleapis.com/v1/accounts:sendVerificationCode")
            .queryParam("key", apiKey);

    URI uri = builder.build().toUri();
    PhoneRequest request = new PhoneRequest();
    request.setRequestType("VERIFY_PHONE_NUMBER");
    request.setPhoneNumber(phoneNumber);
    String url = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + apiKey;
    //ResponseEntity<OobResponse> response = restTemplate.postForEntity(url, request, OobResponse.class);

    ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.POST,
            new HttpEntity<>(requestBody, headers),
            String.class);




    // Prepare


    // Make the reques
    if (response.getStatusCode().is2xxSuccessful()) {
        String responseBody = response.getBody();

    } else {
        // Handle the error response
    }
}


    public Student getStudent(String email) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student;
        if (querySnapshot.getDocuments().size() > 0) {

            // document exists

            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

            if (document.exists()) {

                // convert to object
                student = document.toObject(Student.class);
                return student;
            }

        }

        return null;
    }

    public String updatePassword(String email,String password) throws ExecutionException, InterruptedException {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student;
        if (!querySnapshot.isEmpty()) {

            // document exists
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

            if (document.exists()) {

                docRef.update("password", password);

                System.out.println("User password updated successfully!");
            } else {
                System.out.println("User not found!");
            }
            return null;
        }
        return null;
    }

    public String deleteStudent(String email) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();


        if (!querySnapshot.isEmpty()) {

            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

            ApiFuture<WriteResult> deleteResult = docRef.delete();

            deleteResult.get();

            return "User successfully deleted!";
        }

        return "no user found";

    }

    public Object logInStudent(String email,String password) throws ExecutionException, InterruptedException {

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        Query query = zgjUfirestore.collection("student_user")
                .whereEqualTo("email", email);

        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot results = future.get();

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
        HashMap<String,Object>mapMap=new HashMap<>();
        HashMap<String,Object>diagnosticMap=new HashMap<>();
        HashMap<String,Object>practiceMap=new HashMap<>();
        HashMap<String,Object>finalMap=new HashMap<>();
        diagnosticMap.put("WhereIsTheUser","MathCalculator");
        practiceMap.put("WhereIsTheUser","NotActivated");
        finalMap.put("WhereIsTheUser","NotActivated");

        //mapMap.put("user_id",email);
        mapMap.put("diagnosticTest",diagnosticMap);
        mapMap.put("practiceTest",practiceMap);
        mapMap.put("finalTest",finalMap);
        mapService.registerMapData(mapMap,email);
        if (results.isEmpty()) {
            return "No Student is found with the email";
        }

        DocumentSnapshot doc = results.getDocuments().get(0);
        String storedPassword = doc.getString("password");

        if (!storedPassword.equals(password)) {

            return "incorrect password" ;
        }
        else {

            String accessToken=jwtTokenGenerator.generateAccessToken(email);
            String refreshToken=jwtTokenGenerator.generateRefreshToken(email);
            Cookie refreshTokenCookie=new Cookie("refresh_token", refreshToken);
            Cookie accessTokenCookie = new Cookie("access_token", accessToken);

            refreshTokenCookie.setPath("/");
            accessTokenCookie.setPath("/");
            HashMap<String,Object>logInToken= new HashMap<>();
            logInToken.put("status","Log in success!");
            logInToken.put("access_token",accessToken);
            logInToken.put("refresh_token",refreshToken);

//            logInToken.put("expiration",jwtTokenGenerator.extractClaims(accessToken).getExpiration().getTime());

            return logInToken;
        }


//       return generateToken(email+password);
}
//    public static String generateToken(String subject){
//        final String SECRET_KEY = "ab1c23d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f67890a1b2c3d4e5f6789ab1c23d4e5f67890a1b2c3d4e5f67890";
//        final long EXPIRATION_TIME = 86400000;
//    // 24 hours
//        Date now = new Date();
//
//        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
//
//        return Jwts.builder()
//                .setSubject(subject)
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .compact();
//    }




    public String changePassword(long phoneNumber, String password, String otp) throws ExecutionException, InterruptedException {
            //otp then new password
        if(otp=="verified"){

            Firestore zgjUfirestore = FirestoreClient.getFirestore();
            CollectionReference documentReference = zgjUfirestore.collection("student_user");
            Query query = documentReference.whereEqualTo("phoneNumber", phoneNumber);
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot querySnapshot = future.get();
            Student student2;
            if (!querySnapshot.isEmpty()) {

                // document exists
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                Object temp= document.toObject(Object.class);
                //System.out.println(temp);
                DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

                if (document.exists()) {

                    docRef.update("password", password);
                    return ("Password Changed successfully!");

                } else {
                    return ("user not found!");
                }
            }
            return ("user not found!");
        }
        return ("user not Verified!");
    }

    public Object forgetPasswordUserCheck(String email, int phoneNumber) throws ExecutionException, InterruptedException {
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = zgjUfirestore.collection("student_user");
        Query query = documentReference.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Student student2;
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            if(document.exists()){
                student2 = document.toObject(Student.class);
                if(phoneNumber==student2.getPhoneNumber()){
                    return "user with that email and phonenumber exists";
                }
                else return "the phone number entered doesn't match";

            }
            else return "There is no user with this email. Please sign up!";
        }
        else return "There is no user with this email. Please sign up!";

    }

    public Object uploadVerificationImage(MultipartFile file,String email) throws IOException {


        // Upload the image to Firebase Firestore
        if (true){
             Map<String,Object>respponse=new HashMap<>();
             respponse.put("download link",imageService.upload(file));
             respponse.put("status","image Uploaded");
            return respponse;
        }
        String baseUrl = "http://www.zegju.com"; // Replace with your base URL

        Map<String, Object> data = new HashMap<>();
        data.put("filename", file.getOriginalFilename());
        data.put("contentType", file.getContentType());
        data.put("size", file.getSize());
        String fileUrl = baseUrl + "/uploads/" + file.getOriginalFilename();
        data.put("url",fileUrl ); // Placeholder for the image URL

        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        //HashMap<String,Object>questionCatagoryObj= (HashMap<String, Object>) ;
       // String testId= (String) questionCatagoryObj.get("test_id");

        ApiFuture<WriteResult> collectionApifuture = zgjUfirestore.collection("verificationImgs").document(email).set(data);
        try {
            return collectionApifuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
    public  Object getVerificationImg(String email){
        Firestore zgjUfirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference2 = zgjUfirestore.collection("verificationImgs").document(email);
        ApiFuture<DocumentSnapshot> future2 = documentReference2.get();
        MultipartFile img=null;
        try {
            DocumentSnapshot documentSnapshot = future2.get();
            Object document;
            if (documentSnapshot.exists()) {

                // document exists//
                img = documentSnapshot.toObject(MultipartFile.class);


                //correctNum= (Long) linkedHashMap.get("correct");

            }} catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(map);
        return img;
    }
//
}