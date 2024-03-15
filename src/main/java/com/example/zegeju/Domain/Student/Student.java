package com.example.zegeju.Domain.Student;

import lombok.Getter;
import  lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Student {
    private String educationStatus;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long phoneNumber;
    private int otp;
    private Date otpExpiry;
    private boolean verified;

}
