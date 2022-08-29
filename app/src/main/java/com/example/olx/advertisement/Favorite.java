package com.example.olx.advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.olx.R;
import com.example.olx.fragments.FragmentNavigation;

//@TODO favorite

public class Favorite extends AppCompatActivity {

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
    }

    //@add favorite view
}