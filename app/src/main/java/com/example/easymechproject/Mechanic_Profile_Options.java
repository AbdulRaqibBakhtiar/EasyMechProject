package com.example.easymechproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Mechanic_Profile_Options extends AppCompatActivity {
    private TextView call_up;
    private TextView search_loc;
    TextView titless, desccs;
    Button call_here, chat_here;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String titles = getIntent().getStringExtra("title");
        String describes = getIntent().getStringExtra("describe");
       // int imgg = getIntent().getIntExtra("images",imgg);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__profile__options);

        titless = (TextView)findViewById(R.id.user_account_name);
        desccs = (TextView)findViewById(R.id.descriptionsss);

        titless.setText(titles);
        desccs.setText(describes);

        chat_here = (Button)findViewById(R.id.account_chat_button);
        chat_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mechanic_Profile_Options.this, ChatApp.class));
            }
        });


    }
    public void map_from_here(View v){
        search_loc = (TextView)findViewById(R.id.user_addresses);
        String LOCATING = search_loc.getText().toString();

        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+LOCATING);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void appoint_from_here(View v){
        startActivity(new Intent(Mechanic_Profile_Options.this, Appointments.class));
    }

    public void call_from_here(View v){
        call_up = (TextView) findViewById(R.id.mechanic_phone1);

        String TEL_NUM = call_up.getText().toString();
        Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", TEL_NUM, null));

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), "Please grant permission to call", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        else
        {
            startActivity(intentCall);
        }

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(Mechanic_Profile_Options.this, new String[]{Manifest.permission.CALL_PHONE},1);
    }
}
