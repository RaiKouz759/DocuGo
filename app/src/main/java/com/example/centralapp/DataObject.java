package com.example.centralapp;

import com.google.gson.annotations.SerializedName;
public class DataObject {

    @SerializedName("name")
    private String name;
    private int ID;
    private String Content;
    private int size;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


//    public DataObject(){}
//    public DataObject(String name) {
//        this.name = name;
//    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}