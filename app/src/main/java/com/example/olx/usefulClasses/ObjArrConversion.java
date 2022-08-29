package com.example.olx.usefulClasses;


import com.example.olx.model.UserModel;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjArrConversion {

    public ArrayList<AdvertisementData> data;
    public UserModel userModel;

    //constructor
    public ObjArrConversion(ArrayList<AdvertisementData> data2) {
        data = data2;
        userModel = null;
    }

    public ObjArrConversion(ArrayList<AdvertisementData> data2, UserModel uModel) {
        data = data2;
        userModel = uModel;
        if (userModel != null){
            userModel.setDb(null);
            userModel.setmAuth(null);
        }


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