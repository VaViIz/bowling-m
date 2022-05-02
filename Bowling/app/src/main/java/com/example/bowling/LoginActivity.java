package com.example.bowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = LoginActivity.class.getName();


    EditText emailAddressET;
    EditText passwordET;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressET = findViewById(R.id.editTextEmailAddress);
        passwordET = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String emailAddress = emailAddressET.getText().toString();
        String password = passwordET.getText().toString();

        //Log.i(LOG_TAG, "onCreate " +emailAddress);
        mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User loged in succesfully");
                    startBooking();
                }else{
                    Log.d(LOG_TAG, "User log in fail");
                    Toast.makeText(LoginActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void startBooking(){
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
        finish();
    }


    public void cancel(View view) {
        finish();
    }
}