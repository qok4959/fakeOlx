package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.olx.R;
import com.example.olx.adapters.ImageAdapter;
import com.example.olx.fragments.FragmentNavigation;
import com.example.olx.usefulClasses.ObjConversion;

import java.util.ArrayList;

public class AdvertisementDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView title, price, description, contactDetails, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        title = findViewById(R.id.textViewAdvertisementDetailsTitle);
        price = findViewById(R.id.textViewAdvertisementDetailsPrice);
        description = findViewById(R.id.textViewAdvertisementDetailsDescription);
        contactDetails = findViewById(R.id.textViewAllContactDetails);
        location = findViewById(R.id.textViewAdvertisementDetailsLocation);

        recyclerView = findViewById(R.id.recyclerViewAdvertisementDetails);

        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjConversion androidPacket = ObjConversion.fromJson(objAsJson);


        title.setText(androidPacket.data.getTitle());
        price.setText(androidPacket.data.getPrice());
        description.setText(androidPacket.data.getDescription());
        contactDetails.setText(capitalize(androidPacket.data.getName())+
                " || " +androidPacket.data.getPhoneNumber()+
                " || " + androidPacket.data.getEmail());
        location.setText(androidPacket.data.getLocation());

        // Here you can use your Object
        Log.d("Gson", String.valueOf(androidPacket.data.getDescription()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageAdapter customAdapter = new ImageAdapter((ArrayList<String>)androidPacket.data.links, AdvertisementDetails.this);
        recyclerView.setAdapter(customAdapter);


    }

    public static String capitalize(String str)
    {
        if(str == null || str.length()<=1) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}