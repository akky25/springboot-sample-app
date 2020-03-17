package com.example.demo.login.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * SignupForm
 */
@Data
public class SignupForm {

    private String userId;
    private String password;
    private String userName;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    private String age;
    private boolean marriage;
}