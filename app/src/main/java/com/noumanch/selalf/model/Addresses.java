package com.noumanch.selalf.model;

import java.util.ArrayList;

/**
 * Created by macy on 11/24/17.
 */

public class Addresses {
    String id
    , addresses,lastname,firstname;
    boolean isSelected;

    public Addresses(String id, String addresses, String lastname, String firstname) {
        this.id = id;
        this.addresses = addresses;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public Addresses() {

    }

    @Override
    public String toString() {
        return "Addresses{" +
                "id='" + id + '\'' +
                ", addresses='" + addresses + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
}
