package com.example.easymechproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Appointment_Holder extends AppCompatActivity {

    TextView Name, Address, Number, Date, Time, Extra, Cost, Service, Phone, Email, Plate, Model, Status;
    Toolbar toolbar;
    TextView completer;
    DatabaseReference easyMechCompletor, getEasyMechCancel, getEasyMechDriver, easyMechRef3;
    FirebaseAuth easyMechAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment__holder);


        Name = (TextView)findViewById(R.id.client_name);
        Address = (TextView)findViewById(R.id.client_address);
        Number = (TextView)findViewById(R.id.client_number);
        Date = (TextView)findViewById(R.id.appoint_date);
        Time = (TextView)findViewById(R.id.appoint_time);
        Extra = (TextView)findViewById(R.id.appoint_extra);
        Cost = (TextView)findViewById(R.id.appoint_cost);
        Service = (TextView)findViewById(R.id.appoint_service);
        Phone = (TextView)findViewById(R.id.client_number);
        Email = (TextView)findViewById(R.id.client_email);
        Plate = (TextView)findViewById(R.id.vehicle_plate);
        Model = (TextView)findViewById(R.id.vehicle_model);
        Status = (TextView)findViewById(R.id.appoint_status);

        easyMechAuth = FirebaseAuth.getInstance();


        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String number = getIntent().getStringExtra("number");
        final String date = getIntent().getStringExtra("date");
        final String time = getIntent().getStringExtra("time");
        String extra = getIntent().getStringExtra("extra");
        final String cost = getIntent().getStringExtra("cost");
        String service = getIntent().getStringExtra("service");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        final String plate = getIntent().getStringExtra("plate");
        String model = getIntent().getStringExtra("model");
        final String status = getIntent().getStringExtra("status");
        final String Service_Name = getIntent().getStringExtra("Service_Center");

        easyMechCompletor = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
        easyMechCompletor.keepSynced(true);
        getEasyMechCancel = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
        getEasyMechDriver = FirebaseDatabase.getInstance().getReference("Drivers");

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        toolbar.setTitle(name +"'s Appointment");
        setSupportActionBar(toolbar);

        Name.setText(name);
        Address.setText(address);
        Number.setText(number);
        Date.setText(date);
        Time.setText(time);
        Extra.setText(extra);
        Cost.setText("Rs. "+cost);
        Service.setText(service);
        Phone.setText(phone);
        Email.setText(email);
        Plate.setText(plate);
        Model.setText(model);
        Status.setText(status);

        completer = findViewById(R.id.completor);
        completer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String plate2 = plate;
                final String datess = date;

                String user_id = easyMechAuth.getCurrentUser().getUid();
                easyMechRef3 = FirebaseDatabase.getInstance().getReference().child("Admin").child("User_Appointments").child("Completed").child(user_id).push();
                easyMechRef3.child("Service Center").setValue(Service_Name);
                easyMechRef3.child("Appointment_Date").setValue(date);
                easyMechRef3.child("Appointment_Time").setValue(time);
                easyMechRef3.child("Total_Cost").setValue(cost);
                easyMechRef3.child("Status").setValue("Completed");

                easyMechCompletor.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        for(final DataSnapshot childs : dataSnapshot.getChildren()){
                            final String key = childs.getKey();
                            final String field = dataSnapshot.child(key).child("Car_Plate").getValue().toString();
                            if(field.equals(plate2)){

                                AlertDialog.Builder builder = new AlertDialog.Builder(Appointment_Holder.this);
                                builder.setCancelable(true);
                                builder.setTitle("Set Service Completed!");
                                builder.setMessage("Is the appointment completed successfully?");
                                builder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                getEasyMechCancel.child(key).child("Status").setValue("Completed");
                                                final String user_id = dataSnapshot.child(key).child("driver_id").getValue().toString();
                                                getEasyMechDriver.child(user_id).child("Appointments_Lists").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot driver: dataSnapshot.getChildren()){
                                                            final String key2 = driver.getKey();
                                                            final String date2 = dataSnapshot.child(key2).child("Appointment_Date").getValue().toString();

                                                            if(date2.equals(datess)){
                                                                Toast.makeText(Appointment_Holder.this,"Request Completed!",Toast.LENGTH_LONG).show();
                                                                getEasyMechDriver.child(user_id).child("Appointments_Lists").child(key2).child("Status").setValue("Completed");
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        });
                                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
