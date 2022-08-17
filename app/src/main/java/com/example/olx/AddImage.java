package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.olx.Adapters.CustomAdapter;

import java.util.ArrayList;

public class AddImage extends AppCompatActivity {

    Button btnAddAdvertisement, btnAddLink;
    ArrayList<String> imgLinks;
    EditText link;
    RecyclerView recyclerView;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        Log.d("LinksImgTest", "onCreateMethod");

        imgLinks = new ArrayList<String>();

        imageViewBack = findViewById(R.id.ImageViewBackFromAddLinks);
        btnAddLink = findViewById(R.id.btnAddLinkTest2);
        recyclerView = findViewById(R.id.recyclerViewLinks);
        link = findViewById(R.id.editTextAddLink);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter customAdapter = new CustomAdapter((ArrayList<String>)imgLinks, AddImage.this);
        recyclerView.setAdapter(customAdapter);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddImage.this, AddAdvertisement.class);
                i.putStringArrayListExtra("list", imgLinks);
                startActivity(i);
            }
        });

        btnAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LinksImgTest", imgLinks.toString());
                if (validate()){
                    imgLinks.add(link.getText().toString());
                    Log.d("LinksImg", imgLinks.toString());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    CustomAdapter customAdapter = new CustomAdapter((ArrayList<String>)imgLinks, AddImage.this);
                    recyclerView.setAdapter(customAdapter);
                }
            }
        });
    }


    public boolean validate(){

        if (link.getText().toString().isEmpty()) {
            link.setError("link cannot be empty");
            link.requestFocus();
            return false;
        }
        return true;
    }




}