package com.ccs.testapp;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class Item {
    private int id;
    private int number;
    private int quantity;
    private double price;
    private double cost;
    private String imageURL;
    private String name;
    private String itemKey;


    public Item(int id, String name, int number, int quantity, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
        this.cost = quantity * price;
    }
    public Item() {
        // Пустой конструктор без аргументов, необходимый для Firebase
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.cost = quantity * price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemKey() {
        return itemKey;
    }
}
