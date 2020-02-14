package com.example.easymechproject;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int OPEN_TIME_OUT = 5000;

    FirebaseAuth easyMechAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                easyMechAuth = FirebaseAuth.getInstance();

                if(easyMechAuth.getCurrentUser() != null) {

                    Toast.makeText(MainActivity.this, "Welcome Back!",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Services_LIsts.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {

                    Intent logIn = new Intent(MainActivity.this, Base_Home.class);
                    startActivity(logIn);
                    finish();
                }
            }
        },OPEN_TIME_OUT);


    }
}