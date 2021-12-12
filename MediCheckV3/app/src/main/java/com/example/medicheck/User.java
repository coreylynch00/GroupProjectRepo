package com.example.medicheck;

public class User {

    public String name, dob, email, gender, diabetes, heart;

    public User(){

    }

    public User(String name, String dob, String email, String gender, String diabetes, String heart) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.diabetes = diabetes;
        this.heart = heart;
    }

}
