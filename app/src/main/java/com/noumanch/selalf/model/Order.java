package com.noumanch.selalf.model;

import java.util.ArrayList;

/**
 * Created by macy on 11/24/17.
 */

public class Order {


    String id, delivery_date;
    ArrayList<Product> products;

    public Order(String id, String delivery_date, ArrayList<Product> products) {
        this.id = id;
        this.delivery_date = delivery_date;
        this.products = products;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", delivery_date='" + delivery_date + '\'' +
                ", products=" + products +
                '}';
    }
}
