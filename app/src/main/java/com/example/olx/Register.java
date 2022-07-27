package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText name, surname,email,phoneNumber,password2, password3;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.editTextTextPersonName);
        surname = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextEmailAddress2);
        phoneNumber = findViewById(R.id.editTextPhone);
        password2 = findViewById(R.id.editTextTextPassword2);
        password3 = findViewById(R.id.editTextTextPassword3);

        register = findViewById(R.id.registerUser);
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String tempEmail = email.getText().toString().trim();
        String tempName = name.getText().toString().trim();
        String tempSurname = surname.getText().toString().trim();
        String tempNumber = phoneNumber.getText().toString().trim();
        String tempPswd2 = password2.getText().toString().trim();
        String tempPswd3 = password3.getText().toString().trim();

        if (tempName.isEmpty()){
            name.setError("name cannot be empty");
            name.requestFocus();
            return;
        }
        if (tempSurname.isEmpty()){
            surname.setError("surname cannot be empty");
            surname.requestFocus();
            return;
        }
        if (tempEmail.isEmpty()){
            email.setError("email cannot be empty");
            email.requestFocus();
            return;
        }
        if (tempNumber.isEmpty()){
            phoneNumber.setError("number cannot be empty");
            phoneNumber.requestFocus();
            return;
        }
        if (tempPswd2.isEmpty()){
            password2.setError("password cannot be empty");
            password2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()) {
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }

        if (tempPswd2.length()<8){
            password2.setError("password must have >=8 characters");
            password2.requestFocus();
            return;
        }

        if (!tempPswd2.equals(tempPswd3)){
            password2.setError("passwords are not equal");
            password2.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(tempEmail, tempPswd2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(tempName, tempSurname, tempNumber, tempEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(Register.this, "user has been registered", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(Register.this, "Failed to register",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(Register.this, "Failed to register a user", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}