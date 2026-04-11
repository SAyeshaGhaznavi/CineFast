package com.example.cinemabookingapplication;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    public String name;
    public String details;
    public String trailer;
    public int posterResId;
    public boolean isComingSoon;
    public List<String> bookedSeats;

    public Movie(String name, String details, String trailer, int posterResId, boolean isComingSoon) {
        this.name = name;
        this.details = details;
        this.trailer = trailer;
        this.posterResId = posterResId;
        this.isComingSoon = isComingSoon;
        this.bookedSeats = new ArrayList<>();
        initializeBookedSeats();
    }

    private void initializeBookedSeats() {
        if (isComingSoon) {
            // Coming Soon movies: No booked seats
            bookedSeats.clear();
        } else {
            bookedSeats.add("B1");
            bookedSeats.add("B6");
            bookedSeats.add("B7");
            bookedSeats.add("D2");
            bookedSeats.add("D3");
            bookedSeats.add("E6");
            bookedSeats.add("E7");

            if (name.equals("The Dark Knight")) {
                bookedSeats.add("C1");
                bookedSeats.add("C2");
            } else if (name.equals("Inception")) {
                bookedSeats.add("A1");
                bookedSeats.add("A8");
            } else if (name.equals("Interstellar")) {
                bookedSeats.add("E1");
                bookedSeats.add("E8");
            }
        }
    }

    // Method to update booked seats (when user books)
    public void addBookedSeat(String seat) {
        bookedSeats.add(seat);
    }

    // Check if a seat is booked
    public boolean isSeatBooked(String seat) {
        return bookedSeats.contains(seat);
    }
}