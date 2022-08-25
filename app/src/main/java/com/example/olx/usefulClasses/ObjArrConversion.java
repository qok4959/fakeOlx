package com.example.olx.usefulClasses;


import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjArrConversion {

    public ArrayList<AdvertisementData> data;


    //constructor
    public ObjArrConversion(ArrayList<AdvertisementData> data2) {
        data = data2;
    }
    // other fields ....

    public static ObjArrConversion fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ObjArrConversion.class);
    }

    // You can add those functions as LiveTemplate !
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}