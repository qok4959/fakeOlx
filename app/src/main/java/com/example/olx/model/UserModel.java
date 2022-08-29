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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserModel {

    FirebaseFirestore db;
    String TAG = "retrieving user data";
    FirebaseAuth mAuth;
    String email, name, phoneNumber, surname,id;
    ArrayList<String> favorites;

    public UserModel() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        favorites = new ArrayList<String>();
        retrieveAllData();
    }



    public void retrieveAllData() {
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                HashMap<String, Object> tempHashMap = new HashMap<>();

                                tempHashMap = (HashMap<String, Object>) document.getData();
                                tempHashMap.put("id", document.getId());
                                ArrayList<String> emptyArr = new ArrayList<String>();
                                email = tempHashMap.getOrDefault("email", "default_email").toString();
                                name = tempHashMap.getOrDefault("name", "default_name").toString();
                                phoneNumber = tempHashMap.getOrDefault("phoneNumber", "default_phone_number").toString();
                                surname = tempHashMap.getOrDefault("surname", "default_surname").toString();
                                favorites = (ArrayList<String>) tempHashMap.getOrDefault("favorites", emptyArr);
                                id = tempHashMap.getOrDefault("id", "default_id").toString();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void toggleFavorite(String idAdvertisement){

        ArrayList<String> tempList = new ArrayList();
        Optional option = favorites.stream().filter(x->x.equals(idAdvertisement)).findAny();
        Log.d("option", option.toString());
        if (option.isPresent()){
            tempList = (ArrayList<String>) favorites.stream()
                    .filter(x-> !x.equals(idAdvertisement))
                    .collect(Collectors.toList());

            favorites=tempList;
        }
        else{
            favorites.add(idAdvertisement);
        }

        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("email", email);
        tempMap.put("name", name);
        tempMap.put("phoneNumber", phoneNumber);
        tempMap.put("surname", surname);
        tempMap.put("favorites", favorites);

        db.collection("users")
                .document(id).
                set(tempMap);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
}
