package com.example.easymechproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mechanic_Profile_Options extends AppCompatActivity {
    private TextView call_up;
    private TextView search_loc;
    TextView titless, desccs, phone, emails, user_address , pro_name, pro_email;
    FloatingActionButton chat_here;
    private DatabaseReference easyMechMechanicRef, easyMechMechanicsRef;
    String address, email, phones, descriptions, name, titles;
    ImageView bookerr;
    private ConstraintLayout backer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titles = getIntent().getStringExtra("title");
        String emailss = getIntent().getStringExtra("email");
       // int imgg = getIntent().getIntExtra("images",imgg);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__profile__options);

        titless = (TextView)findViewById(R.id.user_account_name);
        desccs = (TextView)findViewById(R.id.descriptionsss);
        phone = (TextView)findViewById(R.id.mechanic_phone1);
        emails = (TextView)findViewById(R.id.email_field);
        user_address = (TextView)findViewById(R.id.user_addresses);
        pro_email = (TextView)findViewById(R.id.profile_email);
        pro_name = (TextView)findViewById(R.id.profile_name);
        backer = (ConstraintLayout)findViewById(R.id.my_layout);

        if(titles.equals("Auto Car Experts")){
            backer.setBackgroundResource(R.drawable.auto_experts);
        }
        else if(titles.equals("HYDER KHAN MOTOR Garage")){
            backer.setBackgroundResource(R.drawable.bavarati_auto);
        }
        else if(titles.equals("Sharayu Toyota")){
            backer.setBackgroundResource(R.drawable.sharya_toyota);
        }
        else if(titles.equals("Auto Pro")){
            backer.setBackgroundResource(R.drawable.auto_pro);
        }
        else if(titles.equals("Alcon Hyundai")){
            backer.setBackgroundResource(R.drawable.alcon_hyndai);
        }
        else if(titles.equals("Bavaria Motors Pvt Ltd")){
            backer.setBackgroundResource(R.drawable.haider_khan);
        }


        easyMechMechanicRef = FirebaseDatabase.getInstance().getReference().child("Service Centers").child(titles);
        easyMechMechanicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                address = dataSnapshot.child("address").getValue().toString();
                email = dataSnapshot.child("email").getValue().toString();
                phones = dataSnapshot.child("phone").getValue().toString();
                descriptions = dataSnapshot.child("description").getValue().toString();
                name = dataSnapshot.getValue().toString();
                //name = dataSnapshot.child("name").getValue().toString();
                //................................................................................................................

                //String image = dataSnapshot.child("thumb_image").getValue().toString();
                //Picasso.get().load(image).placeholder(R.drawable.default_profile).into(profilePhoto);

                pro_name.setText(titles);
                pro_email.setText(email);

                titless.setText(titles);
                desccs.setText(descriptions);
                user_address.setText(address);
                phone.setText(phones);
                emails.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chat_here = (FloatingActionButton) findViewById(R.id.account_chat_button);
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
        Intent appointment = new Intent(Mechanic_Profile_Options.this, Appointments.class);
        appointment.putExtra("M_name",titles);

        startActivity(new Intent(appointment));
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
