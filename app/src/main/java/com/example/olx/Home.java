package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.olx.fragments.FragmentNavigation;

public class Home extends AppCompatActivity {

    ImageView automotive, electronics, furniture, services, jobs, fashion, music, realEstate;
    String output="empty";
    final String[] list = {"Automotive", "Electronics", "Furniture", "Services", "Jobs", "Fashion", "Music", "Real estate"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        automotive = findViewById(R.id.imageViewAutomotive);
        electronics = findViewById(R.id.imageViewElectronics);
        furniture = findViewById(R.id.imageViewFurniture);
        services = findViewById(R.id.imageViewServices);
        jobs = findViewById(R.id.imageViewJobs);
        fashion = findViewById(R.id.imageViewFashion);
        music = findViewById(R.id.imageViewMusic);
        realEstate = findViewById(R.id.imageViewRealEstate);

        View.OnClickListener imageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked?", "hello");
                switch(view.getId()){
                    case R.id.imageViewAutomotive:
                        output=list[0];
                        break;
                    case R.id.imageViewElectronics:
                        output=list[1];
                        break;
                    case R.id.imageViewFurniture:
                        output=list[2];
                        break;
                    case R.id.imageViewServices:
                        output=list[3];
                        break;
                    case R.id.imageViewJobs:
                        output=list[4];
                        break;
                    case R.id.imageViewFashion:
                        output=list[5];
                        break;
                    case R.id.imageViewMusic:
                        output=list[6];
                        break;
                    case R.id.imageViewRealEstate:
                        output=list[7];
                        break;
                }
                Toast.makeText(getApplicationContext(),output,Toast.LENGTH_SHORT).show();
            }
        };

        automotive.setOnClickListener(imageListener);
        electronics.setOnClickListener(imageListener);
        furniture.setOnClickListener(imageListener);
        services.setOnClickListener(imageListener);
        jobs.setOnClickListener(imageListener);
        fashion.setOnClickListener(imageListener);
        music.setOnClickListener(imageListener);
        realEstate.setOnClickListener(imageListener);
    }



}