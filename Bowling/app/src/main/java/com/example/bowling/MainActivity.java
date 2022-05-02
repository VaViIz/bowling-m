package com.example.bowling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;

    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = findViewById(R.id.frontImage);

        i.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
                i.startAnimation(animation);
            }
        });

    }

    public void login(View view) {


        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}