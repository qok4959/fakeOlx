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
import com.example.olx.usefulClasses.TestObjConversion;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    String email, password;
    EditText editTxtEmail, editTxtPassword;
    TextView forgotPswdEditTxt;
    Button btnLogin;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    FirebaseAuth mAuth;
    private TextView register, forgotPassword;
//    https://i1.sndcdn.com/artworks-000502755237-wcaonb-t500x500.jpg
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
        gsc = GoogleSignIn.getClient(this, gso);
        googleBtn = findViewById(R.id.googleImageView);
        btnLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();

//        try {
////            saveToDb();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTxtEmail.getText().toString().trim().toLowerCase();
                if (emailValidation()) {
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


    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
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


    void navigateToUserPanelActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, UserPanel.class);
        startActivity(intent);
    }

    public void login(FirebaseAuth mAuth) {

        if (validate()) {
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


    public boolean passwordValidation() {

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


    public boolean validate() {
        if (emailValidation()) {
            if (passwordValidation()) {
                return true;
            }
        }
        return false;
    }
}


//    public void saveToDb() throws IOException {
//
////        HashMap<String, Object> advertisement = new HashMap<>();
////
////
////        String out = "{\"map\":[{\"date\":\"2022-08-30\",\"phoneNumber\":\"3213123213\",\"price\":\"80\",\"name\":\"artur\",\"description\":\"rekawice jak nowe\",\"links\":[\"https://a.allegroimg.com/original/112575/c2595eb244e29ccdae1b3de432a0/Rekawice-bokserskie-boks-treningowe-Allright-8oz\"],\"location\":\"łódzkie\",\"title\":\"rekawice bokserskie\",\"category\":\"Fashion\",\"email\":\"szpila@o2.pl\"},{\"date\":\"2022-08-30\",\"phoneNumber\":\"3213123213\",\"price\":\"150\",\"name\":\"artur\",\"description\":\"spodenki bokserskie nowe\",\"links\":[\"https://boks-sklep.pl/environment/cache/images/500_500_productGfx_5178/BSE-3570-green-hill-elite-red-1.jpg\",\"https://www.skilspo.com/2688-big_default/spodenki-bokserskie-elite-usa-czerwony-bialy-niebieski-venum.jpg\",\"https://a.allegroimg.com/original/1120a3/1a233f89458a9a94ba99bc30a269/Spodenki-Bokserskie-BENLEE-Rocky-Marciano-UNI-THAI\"],\"location\":\"łódzkie\",\"title\":\"spodenki bokserskie\",\"category\":\"Fashion\",\"email\":\"szpila@o2.pl\"},{\"date\":\"2022-08-30\",\"phoneNumber\":\"3213123213\",\"price\":\"80\",\"name\":\"artur\",\"description\":\"buty jak nowe\",\"links\":[\"https://dobrekimona.pl/userdata/public/gfx/28079.jpg\",\"https://static2.leone.pl/pol_pl_Leone1947-buty-bokserskie-VINTAGE-CL186-1667_6.jpg\"],\"location\":\"łódzkie\",\"title\":\"buty bokserskie\",\"category\":\"Fashion\",\"email\":\"szpila@o2.pl\"}]}";
////
////
////        Log.d("testJSON_", out);
//////        Bundle bundle = getIntent().getExtras();
//////        String objAsJson = bundle.getString("my_obj");
////        TestObjConversion androidPacket = TestObjConversion.fromJson(out);
////
////
//////        TestObjConversion conv = new TestObjConversion();
//////        conv.;
////
////
////        FirebaseFirestore db = FirebaseFirestore.getInstance();
////        // Add a new document with a generated ID
////        db.collection("advertisements")
////                .add(androidPacket.map.get(2))
////                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                    @Override
////                    public void onSuccess(DocumentReference documentReference) {
////                        Log.d("addingDocument:", "DocumentSnapshot added with ID: " + documentReference.getId());
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.w("addingDocument:", "Error adding document", e);
////                    }
////                });
////    }
//
//}