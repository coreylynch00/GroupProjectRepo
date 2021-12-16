package com.example.medicheck;

public class HeartDisease {

    public String age, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, ts;

    public HeartDisease(){

    }

    public HeartDisease(String age, String cp, String trestbps, String chol, String fbs, String restecg, String thalach, String exang, String oldpeak, String slope, String ca, String thal, String ts){
        this.age = age;
        this.cp = cp;
        this.trestbps = trestbps;
        this.chol = chol;
        this.fbs = fbs;
        this.restecg = restecg;
        this.thalach = thalach;
        this.exang = exang;
        this.oldpeak = oldpeak;
        this.slope = slope;
        this.ca = ca;
        this.thal = thal;
        this.ts = ts;
    }
}
