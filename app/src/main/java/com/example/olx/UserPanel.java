package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserPanel extends AppCompatActivity {

    Button btnAddAdvertisement, btnYourAdvertisement, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);


        btnAddAdvertisement = findViewById(R.id.btnAddAdvertisement);
        btnYourAdvertisement = findViewById(R.id.btnMyAdvertisements);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPanel.this, AddAdvertisement.class));
            }
        });

        btnYourAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPanel.this, YourAvertisements.class));
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UserPanel.this, MainActivity.class));
            }
        });
    }
}