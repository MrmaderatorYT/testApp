package com.ccs.testapp;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class Item {
    private int id;
    private int number;
    private int quantity;
    private double price;
    private double cost;
    private Bitmap image;

    public Item(int id, int number, int quantity, double price, Bitmap image) {
        this.id = id;
        this.number = number;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.cost = quantity * price;
    }
    public Item() {
        // Пустой конструктор без аргументов
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return cost;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.cost = quantity * price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.cost = quantity * price;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Item fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Item.class);
    }
}
