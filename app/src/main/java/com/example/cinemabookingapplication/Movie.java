package com.example.cinemabookingapplication;

public class Movie {
    public String name, details, trailer;
    public int image;
    public boolean isComingSoon;

    public Movie(String name, String details, String trailer, int image, boolean isComingSoon) {
        this.name = name;
        this.details = details;
        this.trailer = trailer;
        this.image = image;
        this.isComingSoon = isComingSoon;
    }
}