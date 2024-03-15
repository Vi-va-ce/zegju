package com.example.zegeju.Domain.PracticeTests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Responses extends ArrayList<Object> {
    private String question_id;
    private String response;

}
