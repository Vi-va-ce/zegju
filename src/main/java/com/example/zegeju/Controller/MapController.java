package com.example.zegeju.Controller;

import com.example.zegeju.Service.JwtTokenGenerator;
import com.example.zegeju.Service.MapService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v5/student")
public class MapController {
    @Autowired
    private MapService mapService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @GetMapping("/getMappData")
    // @RequestBody Map<String,Object> data
    public Object putTestt() {
        //user email and statusId

        String userId="welde.gesesse@gmail.com";
        //String statusId= (String) data.get("status");

        return mapService.getMapData(userId);
    }


    @GetMapping("/getMapData")
    // @RequestBody Map<String,Object> data
    public Object putTest(HttpServletRequest request) {
        //user email and statusId
        String authorizationHeader = request.getHeader("Authorization");
        String acccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            acccessToken  = authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        else {
            System.out.println("NO AUTHORIZATION");
        }
        String decrpt=jwtTokenGenerator.decryptToken(acccessToken);
        if(decrpt.equals(jwtTokenGenerator.decryptToken(acccessToken))){
            String email=jwtTokenGenerator.decryptAccessToken(acccessToken);

            String userId=email;
            System.out.println(userId);
            return mapService.getMapData(userId);
        //String statusId= (String) data.get("status");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token")+","+jwtTokenGenerator.extractClaims(acccessToken).getExpiration().getTime();
        }

    }
}
