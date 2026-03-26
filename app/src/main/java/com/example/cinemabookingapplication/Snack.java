package com.example.cinemabookingapplication;

public class Snack {
    public String name;
    public double price;
    public int image;
    public int quantity;

    public Snack(String name, double price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = 0;
    }
}