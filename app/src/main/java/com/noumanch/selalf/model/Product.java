package com.noumanch.selalf.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by macy on 11/19/17.
 */

public class Product implements Parcelable {

    String name,id,price;
    ArrayList<String > images= new ArrayList<>();
    String quantity;
    String giftText;
    int imagDrawableForCAtegory;
    ArrayList<String> id_product_attribute=new ArrayList<>();

    public Product() {
    }

    public Product(String name, String id, String price, ArrayList<String > image) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.images = image;

    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getId_product_attribute() {
        return id_product_attribute;
    }

    public void setId_product_attribute(ArrayList<String> id_product_attribute) {
        this.id_product_attribute = id_product_attribute;
    }


    public int getImagDrawableForCAtegory() {
        return imagDrawableForCAtegory;
    }

    public void setImagDrawableForCAtegory(int imagDrawableForCAtegory) {
        this.imagDrawableForCAtegory = imagDrawableForCAtegory;
    }

    public Product(String name, String id, String price, String  image) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = image;
        //this.images = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getImage() {
        return images;
    }

    public void setImage(ArrayList<String > image) {
        this.images = image;
    }


    public String getGiftText() {
        return giftText;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", images=" + images +
                ", quantity='" + quantity + '\'' +
                ", id_product_attribute='" + id_product_attribute + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.price);
        dest.writeStringList(this.images);
        dest.writeString(this.quantity);
        dest.writeString(this.giftText);
        dest.writeStringList(this.id_product_attribute);
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.price = in.readString();
        this.images = in.createStringArrayList();
        this.quantity = in.readString();
        this.giftText = in.readString();
        this.id_product_attribute = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
