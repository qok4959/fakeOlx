package com.example.olx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.olx.advertisement.AddAdvertisement;
import com.example.olx.advertisement.DetailedFilters;
import com.example.olx.advertisement.Favorite;
import com.example.olx.Home;
import com.example.olx.R;
import com.example.olx.model.UserModel;
import com.example.olx.profile.UserPanel;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjArrConversion;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class FragmentNavigation extends Fragment {

    FirebaseAuth mAuth;
    AdvertisementData allAdvertisements;
    ArrayList<AdvertisementData> favoriteData;
    UserModel userModel;

    ImageView home, favorite, add, profile;

    public FragmentNavigation() {
        super(R.layout.fragment_navigation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        home = getView().findViewById(R.id.imgViewHome);
        favorite = getView().findViewById(R.id.imgViewFavorite);
        add = getView().findViewById(R.id.imgViewAdd);
        profile = getView().findViewById(R.id.imgViewProfile);


        favoriteData = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        userModel = new UserModel();
        allAdvertisements = new AdvertisementData();
        allAdvertisements.fetchAllData();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Home.class));
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Favorite.class));


                AdvertisementData tempAd = new AdvertisementData();
                for (Map<String, Object> i : allAdvertisements.getModel().getAllData()) {

                    tempAd = tempAd.assignData(i);
                    for (String j : userModel.getFavorites()) {
                        if (j.equals(tempAd.id))
                            favoriteData.add(tempAd);
                    }
                }

                Log.d("favorite_advs_count", String.valueOf(favoriteData.size()));

                Intent i = new Intent(getActivity(), Favorite.class);
                ObjArrConversion androidPacket2 = new ObjArrConversion(favoriteData, userModel);
                String objAsJson = androidPacket2.toJson();
                i.putExtra("my_obj", objAsJson);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddAdvertisement.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserPanel.class));
            }
        });
    }
}