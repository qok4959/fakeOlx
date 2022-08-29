package com.example.olx.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olx.MainActivity;
import com.example.olx.R;
import com.example.olx.fragments.FragmentNavigation;
import com.google.firebase.auth.FirebaseAuth;

public class Edit extends AppCompatActivity {


    FirebaseAuth mAuth;
    Button buttonChangePswd;
    EditText editTxtEmail;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentNavigation.class, null)
                    .commit();
        }

        editTxtEmail = findViewById(R.id.emailEditTxtChangePswd);
        mAuth = FirebaseAuth.getInstance();

        buttonChangePswd = findViewById(R.id.changePasswordBtn);

        buttonChangePswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailValidation()) {
                    mAuth.sendPasswordResetEmail(email);
                    Toast.makeText(getApplicationContext(), "email with password reset form has been sent", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }


    public boolean emailValidation() {
        email = editTxtEmail.getText().toString().trim().toLowerCase();
        if (email.isEmpty()) {
            editTxtEmail.setError("email cannot be empty");
            editTxtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTxtEmail.setError("Please provide a valid email");
            editTxtEmail.requestFocus();
            return false;
        }

        if (!email.equals(mAuth.getCurrentUser().getEmail())) {
            editTxtEmail.setError("email is different than logged in user");
            editTxtEmail.requestFocus();
            return false;
        }

        return true;
    }
}