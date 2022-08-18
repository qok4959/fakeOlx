package com.example.olx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.olx.advertisement.AdvertisementDetails;
import com.example.olx.advertisement.YourAdvertisements;
import com.example.olx.profile.Register;
import com.example.olx.profile.UserPanel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private TextView register, forgotPassword;
    String email, password;
    EditText editTxtEmail, editTxtPassword;
    TextView forgotPswdEditTxt;

    Button btnLogin;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        register = findViewById(R.id.textViewRegister);
        editTxtEmail = findViewById(R.id.editTextEmailAddress);
        editTxtPassword = findViewById(R.id.editTextPassword);
        forgotPassword = findViewById(R.id.textViewForgot);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        googleBtn = findViewById(R.id.googleImageView);
        btnLogin = findViewById(R.id.buttonLogin);

        mAuth =  FirebaseAuth.getInstance();


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTxtEmail.getText().toString().trim().toLowerCase();
                if (emailValidation()){
                    mAuth.sendPasswordResetEmail(email);
                    Toast.makeText(getApplicationContext(), "email with password reset form has been sent", Toast.LENGTH_LONG).show();
                }
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(mAuth);
            }
        });
//
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }



    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
//                navigateToUserPanelActivity();
                finish();
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void navigateToUserPanelActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this, UserPanel.class);
        startActivity(intent);
    }

    public void login(FirebaseAuth mAuth){

        if (validate()){
            email = editTxtEmail.getText().toString().trim().toLowerCase();
            password = editTxtPassword.getText().toString().trim();
            Log.d("emailPassword:", email + "\t" + password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signingLog", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Authentication passed.",
                                        Toast.LENGTH_SHORT).show();
                                navigateToUserPanelActivity();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signingLog", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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

        return true;
    }


    public boolean passwordValidation(){

        password = editTxtPassword.getText().toString().trim();

        if (password.isEmpty()) {
            editTxtPassword.setError("password cannot be empty");
            editTxtPassword.requestFocus();
            return false;
        }

        if (password.length() < 8) {
            editTxtPassword.setError("password must have >=8 characters");
            editTxtPassword.requestFocus();
            return false;
        }
        return true;
    }


    public boolean validate(){
        if (emailValidation()){
            if (passwordValidation()) {
                return true;
            }
        }
        return false;
    }
}