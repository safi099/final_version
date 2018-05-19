package com.noumanch.selalf.model;

/**
 * Created by macy on 12/3/17.
 */

public class User {

    String lastname,firstname,email,phone,id;

    public User() {
    }

    public User(String lastname, String firstname, String email, String phone, String id) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
