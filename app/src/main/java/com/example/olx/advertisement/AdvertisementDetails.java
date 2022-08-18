package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.olx.R;
import com.example.olx.fragments.FragmentNavigation;

public class AdvertisementDetails extends AppCompatActivity {

    ImageView imageViewDetails;

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

        imageViewDetails = findViewById(R.id.imageViewDetails);


        Glide.with(this).load("https://image.ceneostatic.pl/data/products/66658852/i-toyota-yaris-ii-2008-90km-srebrny.jpg").into(imageViewDetails);
    }
}