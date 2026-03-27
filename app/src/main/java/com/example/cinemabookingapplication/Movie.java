package com.example.cinemabookingapplication;

public class Movie {
    public String name;
    public String details;
    public String trailer;
    public int posterResId; // Change from R.drawable.poster to this
    public boolean isComingSoon;

    public Movie(String name, String details, String trailer, int posterResId, boolean isComingSoon) {
        this.name = name;
        this.details = details;
        this.trailer = trailer;
        this.posterResId = posterResId;
        this.isComingSoon = isComingSoon;
    }
}