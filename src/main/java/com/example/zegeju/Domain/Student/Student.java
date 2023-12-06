package com.example.zegeju.Domain.Student;

import lombok.Getter;
import  lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Student {
    private String education_status;
    private String Fname;
    private String Lname;
    private String email;
    private String password;
    private int phone_no;
    private int otp;
    private Date otpExpiry;
    private boolean verified;

}
