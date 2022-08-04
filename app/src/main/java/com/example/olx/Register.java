package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText name, surname,email,phoneNumber,password2, password3;
    private Button register;
    String tempEmail, tempName, tempSurname, tempNumber, tempPswd2, tempPswd3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.editTextTextPersonName);
        surname = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextEmailAddress2);
        phoneNumber = findViewById(R.id.editTextPhone);
        password2 = findViewById(R.id.editTextTextPassword2);
        password3 = findViewById(R.id.editTextTextPassword3);


        register = findViewById(R.id.registerUser);
        register.setOnClickListener(this);
    }


    public void login(){
        mAuth.signInWithEmailAndPassword(tempEmail, tempPswd2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signingLog", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Authentication passed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signingLog", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void saveToDb(){

        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", tempName );
        user.put("surname", tempSurname);
        user.put("email", tempEmail);
        user.put("phoneNumber", tempNumber);


        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("addingDocument:", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addingDocument:", "Error adding document", e);
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerUser:
                registerUser();
                break;
        }
    }



    public void registerUser() {
        tempEmail = email.getText().toString().trim();
        tempName = name.getText().toString().trim();
        tempSurname = surname.getText().toString().trim();
        tempNumber = phoneNumber.getText().toString().trim();
        tempPswd2 = password2.getText().toString().trim();
        tempPswd3 = password3.getText().toString().trim();

        if (tempName.isEmpty()) {
            name.setError("name cannot be empty");
            name.requestFocus();
            return;
        }
        if (tempSurname.isEmpty()) {
            surname.setError("surname cannot be empty");
            surname.requestFocus();
            return;
        }
        if (tempEmail.isEmpty()) {
            email.setError("email cannot be empty");
            email.requestFocus();
            return;
        }
        if (tempNumber.isEmpty()) {
            phoneNumber.setError("number cannot be empty");
            phoneNumber.requestFocus();
            return;
        }
        if (tempPswd2.isEmpty()) {
            password2.setError("password cannot be empty");
            password2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()) {
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }

        if (tempPswd2.length() < 8) {
            password2.setError("password must have >=8 characters");
            password2.requestFocus();
            return;
        }

        if (!tempPswd2.equals(tempPswd3)) {
            password2.setError("passwords are not equal");
            password2.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(tempEmail, tempPswd2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("accountCreationStatus:", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Authentication Passed.",
                                    Toast.LENGTH_SHORT).show();

                            saveToDb();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("accountCreationStatus:", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void testDisplay(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String num = user.getPhoneNumber();

            Log.d("userData--",name+ " "+email+" "+ photoUrl+" "+num);
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }
}