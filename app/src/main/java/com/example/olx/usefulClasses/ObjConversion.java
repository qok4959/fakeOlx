package com.example.olx.usefulClasses;

import com.google.gson.Gson;

public class ObjConversion {

    public AdvertisementData data;


    public ObjConversion() {

    }

    //constructor
    public ObjConversion(AdvertisementData cName) {
        data = cName;
    }
    // other fields ....

    public static ObjConversion fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ObjConversion.class);
    }

    // You can add those functions as LiveTemplate !
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}