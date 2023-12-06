package com.example.zegeju.Domain.PerformanceRecords;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


@Setter
@Getter
public class Answer {

   private String testId;
   private HashMap<String,String> answers;

}
