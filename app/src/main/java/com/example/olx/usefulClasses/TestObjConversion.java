package com.example.olx.usefulClasses;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class TestObjConversion {


    public ArrayList<HashMap<String,Object>> map;


    public TestObjConversion() {

    }

    //constructor
    public TestObjConversion(ArrayList<HashMap<String, Object>> map) {
        this.map = map;
    }

    // other fields ....

    public static TestObjConversion fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TestObjConversion.class);
    }

    // You can add those functions as LiveTemplate !
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
