package com.noumanch.selalf.model;

/**
 * Created by macy on 11/25/17.
 */

public class GeneralHash {

    String id,name;

    public GeneralHash(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GeneralHash() {
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

    @Override
    public String toString() {
        return "GeneralHash{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
