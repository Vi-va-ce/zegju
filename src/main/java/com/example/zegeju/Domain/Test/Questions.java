package com.example.zegeju.Domain.Test;

import java.util.List;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Questions {
    private String question_id;

    private String question_text;

    private List<Choice> choice;
    private String answer;

    private String sub_section;
}
;
