package com.example.zegeju.Domain.PerformanceRecords;

import java.util.HashMap;

public class Score {
    private String user_id;
    private HashMap<String,Object>sat_score;

    private HashMap <String,Object>sat_total_score;/// contains the sat-total score, with string id of Sat test number
    private HashMap <String,Object> math_section_score;/// contains the SAT score of the math section with math calculator and no calculator sections
    private HashMap <String,Object>english_section_Score;//// contains the SAT score of english section with the reading and  writing sections

    private  HashMap<String,Object> math_calculator_section;
    private  HashMap<String,Object> math_no_calculator_section;

    private  HashMap<String ,Object> reading_section_Score;
    private  HashMap<String ,Object> writing_section_Score;
}
