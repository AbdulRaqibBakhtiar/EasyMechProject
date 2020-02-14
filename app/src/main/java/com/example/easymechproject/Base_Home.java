package com.example.easymechproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Base_Home extends AppCompatActivity{



    private EditText email;
    private EditText pass_word;
    private Button _login;
    public int counter = 5;
    public static String emails, pass;
    private FirebaseAuth easyMechAuth;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    private DatabaseReference easyMechDriverRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base__home);

        progressDialog = new ProgressDialog(Base_Home.this);

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        email = (EditText)findViewById(R.id._username);
        pass_word = (EditText)findViewById(R.id._password);
        _login = (Button)findViewById(R.id.login_btn);

        easyMechDriverRef = FirebaseDatabase.getInstance().getReference().child("Users");
        easyMechAuth = FirebaseAuth.getInstance();


        _login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                progressDialog.setTitle("Signing in...");
                progressDialog.show();

                emails = email.getText().toString();
                pass = pass_word.getText().toString();

                if(emails.equals("") || emails.matches("[0-9a-zA-Z._-]+@[a-z]+\\.+[a-z]+")==false){
                    email.setError("Enter the Username!");
                    progressDialog.dismiss();
                    email.requestFocus();
                    return;
                }
                else if(pass.equals("")||pass.length()<8){
                    pass_word.setError("Enter the password!");
                    pass_word.requestFocus();
                    progressDialog.dismiss();
                    return;
                }
                loginUser(emails, pass);


            }
        });
    }

    public void sign_up_here(View v){
        TextView tv = (TextView) findViewById(R.id.sign_up_link);
        Intent int1 = new Intent(Base_Home.this, Sign_up_activity.class);
        startActivity(int1);
    }

    public void forgot_password_here(View v){
        TextView fp = (TextView) findViewById(R.id.fogrgotten_pass);
        startActivity(new Intent(Base_Home.this, Fargot_Password.class));
    }

    private void loginUser(String email, String password) {
        easyMechAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Login Successfull!.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Base_Home.this,Services_LIsts.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    //establishUI(null);
                    Toast.makeText(Base_Home.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }


}
