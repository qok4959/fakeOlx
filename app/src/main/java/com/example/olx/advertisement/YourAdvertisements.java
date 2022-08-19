package com.example.olx.advertisement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.olx.R;
import com.example.olx.adapters.AdvertisementAdapter;
import com.example.olx.adapters.TextAdapter;
import com.example.olx.fragments.FragmentNavigation;
import com.example.olx.model.AdvertisementModel;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.AndroidPacket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class YourAdvertisements extends AppCompatActivity {

    RecyclerView recyclerView;

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
        final String TAG = "loggingTest";
        AdvertisementModel model = new AdvertisementModel();
        recyclerView = findViewById(R.id.recyclerViewYourAdvertisement);

        ArrayList<AdvertisementData> advertisementData = new ArrayList<>();
        Button buttonTestAdvertisement = (findViewById(R.id.buttonTestAdvertisement));


        buttonTestAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Map<String,Object> i : model.getData()){
                    advertisementData.add(new AdvertisementData().asignData(i));
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                AdvertisementAdapter customAdapter = new AdvertisementAdapter((ArrayList<AdvertisementData>)advertisementData, YourAdvertisements.this);
                recyclerView.setAdapter(customAdapter);

                buttonTestAdvertisement.setVisibility(View.GONE);
            }
        });
//        Log.d(TAG, model.getData().get(0).toString());

    }
}