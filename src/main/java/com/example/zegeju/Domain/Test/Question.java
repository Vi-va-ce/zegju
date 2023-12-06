package com.example.zegeju.Domain.Test;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


@Setter
@Getter

public class Question {

    private String question_id;

    private String question_text;

    private HashMap<String,String> choice;

    private String sub_section;

}
