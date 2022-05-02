package com.example.bowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 99;

    EditText userNameET;
    EditText emailAddressET;
    EditText passwordET;
    EditText passwordAgainET;
    EditText phoneNumberET;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");

        if (secret_key != 99){
            finish();
        }

        userNameET = findViewById(R.id.editTextUserName);
        emailAddressET = findViewById(R.id.editTextEmailAddress);
        passwordET = findViewById(R.id.editTextPassword);
        passwordAgainET = findViewById(R.id.editTextPasswordAgain);
        phoneNumberET = findViewById(R.id.editTextPhoneNumber);


        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");

    }



    public void cancel(View view) {
        finish();
    }

    public void register(View view) {

        String userName  = userNameET.getText().toString();
        String emailAddress = emailAddressET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordAgain = passwordAgainET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();

        if(!password.equals(passwordAgain)){
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése" );
            return;
        }

        //startBooking();

        mAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User  cretaes succesfully");
                    startBooking();
                }else{
                    Log.d(LOG_TAG, "User was't cretaes succesfully");
                    Toast.makeText(RegisterActivity.this, "User was't cretaes succesfully: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void startBooking(){
        Intent intent = new Intent(this, BookingActivity.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }
}