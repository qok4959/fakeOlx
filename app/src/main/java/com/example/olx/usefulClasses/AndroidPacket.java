package com.example.olx.usefulClasses;

import com.google.gson.Gson;

public class AndroidPacket {

    public AdvertisementData data;

    //constructor
    public AndroidPacket(AdvertisementData cName){
        data = cName;
    }
    // other fields ....


    // You can add those functions as LiveTemplate !
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static AndroidPacket fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AndroidPacket.class);
    }
}