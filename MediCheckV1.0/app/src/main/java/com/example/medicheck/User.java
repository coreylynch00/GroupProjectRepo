//This class is used to create a user object to pass to Firebase

package com.example.medicheck;

public class User {

    String name, age, email;

    public User(){

    }

    public User(String name, String age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
