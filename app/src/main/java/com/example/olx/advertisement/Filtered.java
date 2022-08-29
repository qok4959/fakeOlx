package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.olx.R;
import com.example.olx.adapters.AdvertisementFullAdapter;
import com.example.olx.fragments.FragmentNavigation;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjArrConversion;

import java.util.ArrayList;


public class Filtered extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        recyclerView = findViewById(R.id.recyclerViewFiltered);
        info = findViewById(R.id.textViewFilteredAdvertisementsInfo);


        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjArrConversion androidPacket = ObjArrConversion.fromJson(objAsJson);


        if (androidPacket.data.size() == 0) {
            info.setVisibility(View.VISIBLE);
        } else {
            info.setVisibility(View.INVISIBLE);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        AdvertisementFullAdapter customAdapter = new AdvertisementFullAdapter((ArrayList<AdvertisementData>) androidPacket.data, androidPacket.userModel, Filtered.this);
        recyclerView.setAdapter(customAdapter);

    }
}


