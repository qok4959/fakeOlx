package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.olx.fragments.FragmentNavigation;

public class YourAdvertisements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_avertisements);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }
    }
}