package com.example.cinemabookingapplication;

public class Snack {
    public int id;
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

    public Snack(int id, String name, double price, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = 0;
    }
}