package com.example.olx.advertisement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olx.R;
import com.example.olx.fragments.FragmentNavigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddAdvertisement extends AppCompatActivity {


    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Button btnAddAdvertisement;
    EditText title, description, name, phoneNumber, price;
    ImageView img;
    String strTitle, strDescription, strName, strPhoneNumber, strPrice;
    Spinner dropdownCategories, dropdownLocations;

    Map<String, Object> advertisement;
    ArrayList<String> imgLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisement);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        advertisement = new HashMap<>();
        imgLinks = new ArrayList<>();

        img = findViewById(R.id.imgViewAddPhoto);
        btnAddAdvertisement = findViewById(R.id.btnAddAdvertisement);
        title = findViewById(R.id.editTxtAddTitle);
        description = findViewById(R.id.editTxtAddTitle);
        name = findViewById(R.id.editTxtAddName);
        phoneNumber = findViewById(R.id.editTxtAddPhoneNumber);
        price = findViewById(R.id.editTxtPrice);
        dropdownCategories = findViewById(R.id.spinnerCategories);
        dropdownLocations = findViewById(R.id.spinnerLocation);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgLinks=extras.getStringArrayList("list");
        }


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        String[] categories = new String[]{"Automotive", "Electronics", "Furniture", "Services", "Jobs", "Fashion", "Music", "Real estate"};
        String[] locations = new String[]{"dolnośląskie","kujawsko-pomorskie","lubelskie","lubuskie","łódzkie","małopolskie","mazowieckie","opolskie","podkarpackie","podlaskie","pomorskie","śląskie","świętokrzyskie","warmińsko-mazurskie","wielkopolskie","zachodniopomorskie"};

        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        dropdownCategories.setAdapter(adapterCategories);


        ArrayAdapter<String> adapterLocations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        dropdownLocations.setAdapter(adapterLocations);



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAdvertisement.this, AddImage.class);
                startActivity(intent);
            }
        });

        btnAddAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("logging_status", "btnAddAdvertisement.setOnClickListener");
                if (validation()){
                    saveToDb();
                    Toast.makeText(getApplicationContext(), "Advertisement has been added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddAdvertisement.this, YourAdvertisements.class);
                    startActivity(intent);
                }
            }
        });
    }


    public boolean validation(){

        strTitle = title.getText().toString().trim().toLowerCase();
        strDescription = description.getText().toString().trim().toLowerCase();
        strName = name.getText().toString().trim().toLowerCase();
        strPhoneNumber = phoneNumber.getText().toString().trim().toLowerCase();
        strPrice = price.getText().toString().trim();


        if (strName.isEmpty()) {
            name.setError("name. cannot be empty");
            name.requestFocus();
            return false;
        }

        else if (strTitle.isEmpty()) {
            title.setError("title. cannot be empty");
            title.requestFocus();
            return false;
        }

        else if (strDescription.isEmpty()) {
            description.setError("description. cannot be empty");
            description.requestFocus();
            return false;
        }

        else if (strPhoneNumber.isEmpty()) {
            phoneNumber.setError("phoneNumber cannot be empty");
            phoneNumber.requestFocus();
            return false;
        }

        else if (strPrice.isEmpty()) {
            price.setError("phoneNumber cannot be empty");
            price.requestFocus();
            return false;
        }

        return true;
    }


    public void saveToDb(){

        advertisement.put("name", strName);
        advertisement.put("phoneNumber", strPhoneNumber);
        advertisement.put("description", strDescription);
        advertisement.put("title", strTitle);
        advertisement.put("links", imgLinks);
        advertisement.put("email", mAuth.getCurrentUser().getEmail().toString());
        advertisement.put("price", strPrice);
        advertisement.put("category", dropdownCategories.getSelectedItem().toString());
        advertisement.put("location", dropdownLocations.getSelectedItem().toString());



        // Add a new document with a generated ID
        db.collection("advertisements")
                .add(advertisement)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("addingDocument:", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addingDocument:", "Error adding document", e);
                    }
                });
    }

}