//This class is used to create a user object to pass to Firebase

package com.example.medicheck;

public class User {

    String name, age, email, gender, diabetes, heart;

    public User(){

    }

    public User(String name, String age, String email, String gender, String diabetes, String heart){
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.diabetes = diabetes;
        this.heart = heart;
    }
}
