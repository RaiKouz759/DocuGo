package com.example.centralapp;

import com.google.gson.annotations.SerializedName;
public class DataObject {

    @SerializedName("name")
    private String name;
    public DataObject(){}
    public DataObject(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}