package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.olx.Home;
import com.example.olx.R;
import com.example.olx.adapters.AdvertisementAdapter;
import com.example.olx.adapters.AdvertisementFullAdapter;
import com.example.olx.fragments.FragmentNavigation;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjArrConversion;
import com.example.olx.usefulClasses.ObjConversion;

import java.util.ArrayList;

public class Filters extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnFilters, btnCheap, btnExpensive;
    String sortPrice="none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjArrConversion androidPacket = ObjArrConversion.fromJson(objAsJson);


        btnFilters = findViewById(R.id.buttonFilters);

        recyclerView = findViewById(R.id.recyclerViewAdvertisementFull);

        if (androidPacket.data.size()==0){
            Log.d("co jest", "nothing to show");
        }
        else{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            AdvertisementFullAdapter customAdapter = new AdvertisementFullAdapter((ArrayList<AdvertisementData>)androidPacket.data, Filters.this);
            recyclerView.setAdapter(customAdapter);
        }




        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Filters.this, DetailedFilters.class);
                ObjArrConversion androidPacket2 = new ObjArrConversion(androidPacket.data);
                String objAsJson = androidPacket2.toJson();
                i.putExtra("my_obj", objAsJson);
                startActivity(i);
            }
        });



    }
}