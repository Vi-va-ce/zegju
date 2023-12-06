package com.example.zegeju.Domain.Test;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionGroup {
    private String question_group_id;
    private List<QuestionType> question_types;
}
