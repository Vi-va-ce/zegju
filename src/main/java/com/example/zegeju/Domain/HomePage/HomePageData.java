package com.example.zegeju.Domain.HomePage;

import java.util.HashMap;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
public class HomePageData {
    private HashMap<String,Object> homePageMap;
    private String email;
    private SatData satData;
    private MatricData matricData;
    private ToeflData toeflData;
    private IeltsData IeltsData;
    private GreData GreData;
    private GatData GatData;
}
