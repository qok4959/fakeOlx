package com.example.olx.advertisement;

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

import com.example.olx.adapters.TextAdapter;
import com.example.olx.R;
import com.example.olx.usefulClasses.ObjConversion;

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

        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjConversion androidPacket = ObjConversion.fromJson(objAsJson);

        imgLinks = androidPacket.data.getLinks();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        TextAdapter customAdapter = new TextAdapter((ArrayList<String>) imgLinks, AddImage.this);
        recyclerView.setAdapter(customAdapter);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(AddImage.this, AddAdvertisement.class);
//                i.putStringArrayListExtra("list", imgLinks);
//                startActivity(i);
                Log.d("imageViewTitle", androidPacket.data.getTitle());
                androidPacket.data.setLinks(imgLinks);


                Log.d("bundleGetClass",getCallingActivity().getClassName());

                Intent i;
                if (getCallingActivity().getClassName().contains("AddAdvertisement"))
                    i = new Intent(AddImage.this, AddAdvertisement.class);
                else
                    i = new Intent(AddImage.this, EditAdvertisement.class);

                ObjConversion androidPacket2 = new ObjConversion(androidPacket.data);
                String objAsJson = androidPacket2.toJson();
                i.putExtra("my_obj", objAsJson);
                startActivity(i);

            }
        });

        btnAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LinksImgTest", imgLinks.toString());
                if (validate()) {
                    imgLinks.add(link.getText().toString());
                    Log.d("LinksImg", imgLinks.toString());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    TextAdapter customAdapter = new TextAdapter((ArrayList<String>) imgLinks, AddImage.this);
                    recyclerView.setAdapter(customAdapter);
                    link.setText("");
                }
            }
        });
    }


    public boolean validate() {

        if (link.getText().toString().isEmpty()) {
            link.setError("link cannot be empty");
            link.requestFocus();
            return false;
        }
        return true;
    }


}