package com.example.easymechproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Fargot_Password extends AppCompatActivity {
    TextView btn_es, sign_up;
    TextInputEditText passworder;
    public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fargot__password);

        sign_up = (TextView)findViewById(R.id.home_page_login);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fargot_Password.this, Sign_up_activity.class));
            }
        });
        btn_es = (TextView) findViewById(R.id.forgot_clicker);
        passworder = (TextInputEditText) findViewById(R.id.pass_mail);
        email = passworder.getText().toString();

        btn_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = passworder.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Fargot_Password.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Fargot_Password.this, Base_Home.class));
                                } else {
                                    Toast.makeText(Fargot_Password.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
}
