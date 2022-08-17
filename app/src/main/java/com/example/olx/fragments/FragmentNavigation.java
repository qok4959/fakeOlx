package com.example.olx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.olx.AddAdvertisement;
import com.example.olx.Favorite;
import com.example.olx.R;
import com.example.olx.Register;
import com.example.olx.UserPanel;
import com.example.olx.YourAdvertisements;

public class FragmentNavigation extends Fragment {
    ImageView home, favorite,add,profile;
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


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), YourAdvertisements.class));
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Favorite.class));
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