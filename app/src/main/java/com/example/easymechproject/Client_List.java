package com.example.easymechproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Client_List extends AppCompatActivity {

    RecyclerView recyclerView;
    Appointment_Adapter appointment_adapter;
    List<DataSetFire> noteList;
    private DatabaseReference easyMechRef;
    FirebaseAuth easyMechAuth;
    FirebaseUser easyMechCurrentUser;
    String Service_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__list);

        recyclerView = findViewById(R.id.client_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();
        String service_email = easyMechCurrentUser.getEmail();
        if(service_email.equals("autocarexperts@gmail.com")){
            Service_Name = "Auto Car Experts";
        }
        else if(service_email.equals("hyderkhan.goa@gmail.com")){
            Service_Name = "HYDER KHAN MOTOR Garage";
        }
        else if(service_email.equals("sharayutoyota@yahoo.com")){
            Service_Name = "Sharayu Toyota";
        }
        else if(service_email.equals("autopro@hotmail.com")){
            Service_Name = "Auto Pro";
        }
        else if(service_email.equals("alconhyndai@gmail.com")){
            Service_Name = "Alcon Hyundai";
        }
        else if(service_email.equals("bavariamotors@gmail.com")){
            Service_Name = "Bavaria Motors Pvt Ltd";
        }

        noteList = new ArrayList<>();

        easyMechRef = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
        //................................................................................................................

        easyMechRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    noteList.clear();
                    for(DataSnapshot appointment : dataSnapshot.getChildren()){
                        DataSetFire d = appointment.getValue(DataSetFire.class);
                        noteList.add(d);
                    }

                    appointment_adapter = new Appointment_Adapter(Client_List.this, noteList);
                    recyclerView.setAdapter(appointment_adapter);
                }


               // noteList.add(new DataSetFire(date,time,name,num,service,services,cost));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
