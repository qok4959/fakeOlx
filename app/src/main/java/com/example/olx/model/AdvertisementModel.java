package com.example.olx.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvertisementModel {


    String category, description, email, location, name, phoneNumber, price, title;
    ArrayList<String> links;
    final String TAG;
//    QueryDocumentSnapshot data;
    ArrayList<Map<String,Object>> data;

 
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    public AdvertisementModel() {
        TAG = "loggingResult";
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail().toString();
        data = new ArrayList<Map<String,Object>>();
       retrieveData();

//       while (data.size()<1) {
////           Log.d("loggingEnd", "waiting...");
//       }
//       Log.d("loggingEnd", String.valueOf(data.size()));

    }


    public void retrieveData(){
        db.collection("advertisements")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                data.add(document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }


    public ArrayList<Map<String,Object>>getData() {
        return data;
    }

    public void setData(ArrayList<Map<String,Object>>data) {
        this.data = data;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }
}
