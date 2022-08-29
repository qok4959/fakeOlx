package com.example.olx.advertisement;

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

//@TODO favorite

public class Favorite extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }


        //@add favorite view
        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString("my_obj");
        ObjArrConversion androidPacket = ObjArrConversion.fromJson(objAsJson);


        recyclerView = findViewById(R.id.recyclerViewFavorite);
        info = findViewById(R.id.textViewFilteredAdvertisementsInfo2);

        if (androidPacket.data.size() == 0) {
            Log.d("co jest", "nothing to show");
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            AdvertisementFullAdapter customAdapter = new AdvertisementFullAdapter((ArrayList<AdvertisementData>) androidPacket.data, androidPacket.userModel, Favorite.this);
            recyclerView.setAdapter(customAdapter);
        }


        if (androidPacket.data.size() == 0) {
            Log.d("co jest", "nothing to show");
            info.setVisibility(View.VISIBLE);
        } else {
            info.setVisibility(View.INVISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            AdvertisementFullAdapter customAdapter = new AdvertisementFullAdapter((ArrayList<AdvertisementData>) androidPacket.data, androidPacket.userModel, Favorite.this);
            recyclerView.setAdapter(customAdapter);
        }


    }


}