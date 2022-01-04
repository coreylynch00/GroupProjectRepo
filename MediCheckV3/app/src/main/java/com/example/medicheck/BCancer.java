package com.example.medicheck;

public class BCancer {

    public String radius, texture, perimeter, area, smoothness, ts;

    public BCancer(){

    }

    public BCancer(String radius, String texture, String perimeter, String area, String smoothness, String ts) {
        this.radius = radius;
        this.texture = texture;
        this.perimeter = perimeter;
        this.area = area;
        this.smoothness = smoothness;
        this.ts = ts;
    }
}
