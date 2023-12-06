package com.example.zegeju.Domain.Student;

import com.example.zegeju.Domain.Student.Student;
import lombok.Getter;
import  lombok.Setter;

@Getter
@Setter
public class StudentProfile {
    private int id;
    private Student student;
    private String gradeLevel;
    private String school;
    private String goals;


}
