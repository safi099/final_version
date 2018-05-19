package com.noumanch.selalf.model;

/**
 * Created by macy on 11/26/17.
 */

public class Country {

    String id,name,currencyId;

    public Country() {
    }

    public Country(String id, String name, String currencyId) {
        this.id = id;
        this.name = name;
        this.currencyId = currencyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currencyId='" + currencyId + '\'' +
                '}';
    }
}
