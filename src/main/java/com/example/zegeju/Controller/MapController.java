package com.example.zegeju.Controller;

import com.example.zegeju.Service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v5/student")
public class MapController {
    @Autowired
    private MapService mapService;

    @GetMapping("/getMapData")
    // @RequestBody Map<String,Object> data
    public Object putTest() {
        //user email and statusId

        String userId="welde.gesesse@gmail.com";
        //String statusId= (String) data.get("status");

        return mapService.getMapData(userId);
    }
}
