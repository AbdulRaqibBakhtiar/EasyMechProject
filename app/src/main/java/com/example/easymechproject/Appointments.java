package com.example.easymechproject;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Appointments extends AppCompatActivity{

    EditText  choose_date, address, car_plate, choose_time, car_problem;
    AutoCompleteTextView car_Model;
    Button book_now, reset;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    public String Choose_Date, Choose_Time, Add_Address, Plate_No, Car_Model, Car_Problem;


    private static String Driver_name, Driver_Phone, Driver_Email;
    private final String CHANNEL_ID = "inbox_style_notification";
    private final int NOTIFICATION_ID = 04;
    RadioButton home_service, center_service;
    Toolbar toolbar;
    private DatabaseReference easyMechDriverRef;
    private FirebaseAuth easyMechAuth;
    private FirebaseUser easyMechCurrentUser;
    private DatabaseReference easyMechRef, easyMechRef2, easyMechRef3;
    public String Mechanic_Name, Services="";
    double Costs = 0;
    private CheckBox  check1, check2, check3, check4, check5, check6, check7, check8, check9;
    TextView cost, reset_label, costss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Mechanic_Name = getIntent().getStringExtra("M_name");

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        toolbar.setTitle("Set an Appointment");
        setSupportActionBar(toolbar);

        cost = (TextView)findViewById(R.id.Cost1);
        costss = (TextView)findViewById(R.id.TotalCost);
        reset_label = (TextView)findViewById(R.id.Cost2);
        //reset = (Button)findViewById(R.id.clearButton);

        car_Model = (AutoCompleteTextView) findViewById(R.id.input_car_model);
        car_plate = (EditText)findViewById(R.id.Car_Plate_Field);
        choose_date = (EditText)findViewById(R.id.choose_date);
        choose_time = (EditText)findViewById(R.id.choose_time);
        address = (EditText)findViewById(R.id.Address_Field);
        car_problem = (EditText)findViewById(R.id.tell_problem);

        check1 = (CheckBox)findViewById(R.id.checkBox);
        check2 = (CheckBox)findViewById(R.id.checkBox2);
        check3 = (CheckBox)findViewById(R.id.checkBox3);
        check4 = (CheckBox)findViewById(R.id.checkBox4);
        check5 = (CheckBox)findViewById(R.id.checkBox5);
        check6 = (CheckBox)findViewById(R.id.checkBox6);
        check7 = (CheckBox)findViewById(R.id.checkBox7);
        check8 = (CheckBox)findViewById(R.id.checkBox8);
        check9 = (CheckBox)findViewById(R.id.checkBox9);

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check1.isChecked()){
                    String data = "Air Filter";
                    Services+=data+", ";
                    double price = 200;
                    Costs+=price;
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);

                }
            }
        });
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check2.isChecked()){
                    String data = "Oil Filter";
                    Services+=data+", ";
                    double price = 400;
                    Costs+=price;
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check3.isChecked()){
                    String data = "Engine Oil";
                    Services+=data+", ";
                    double price = 600;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check4.isChecked()){
                    String data = "Wiper Fluid";
                    Services+=data+", ";
                    double price = 350;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check5.isChecked()){
                    String data = "Cabin Filter / AC Filter";
                    Services+=data+", ";
                    double price = 1200;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check6.isChecked()){
                    String data = "Car Wash";
                    Services+=data+", ";
                    double price = 550;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check7.isChecked()){
                    String data = "Interior Vacuuming";
                    Services+=data+", ";
                    double price = 350;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check8.isChecked()){
                    String data = "Throttle Body Cleaning";
                    Services+=data+", ";
                    double price = 1000;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });
        check9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check9.isChecked()){
                    String data = "Lost Keys";
                    Services+=data+", ";
                    double price = 100;
                    Costs+=price;
                    cost.setVisibility(View.VISIBLE);
                    costss.setVisibility(View.VISIBLE);
                    reset_label.setVisibility(View.VISIBLE);
                    cost.setText("Service Cost: "+price);
                    costss.setText("Total Cost: "+Costs);
                }
            }
        });

        reset_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Costs=0;
                cost.setText("Service Cost: 0.0   Total Cost: 0.0");
                if(check1.isChecked()){
                    check1.toggle();
                }
                if(check2.isChecked()){
                    check2.toggle();
                }
                if(check3.isChecked()){
                    check3.toggle();
                }
                if(check4.isChecked()){
                    check4.toggle();
                }
                if(check5.isChecked()){
                    check5.toggle();
                }
                if(check6.isChecked()){
                    check6.toggle();
                }
                if(check7.isChecked()){
                    check7.toggle();
                }
                if(check8.isChecked()){
                    check8.toggle();
                }
                if(check9.isChecked()){
                    check9.toggle();
                }
                if(Costs==0){
                    costss.setVisibility(View.GONE);
                    cost.setVisibility(View.GONE);
                }
            }
        });

        final ArrayList<String> myCarModels = new ArrayList<>();
        myCarModels.add("TOYOTA");
        myCarModels.add("Suzuki");
        myCarModels.add("Hyndai");
        myCarModels.add("Mercedes");
        myCarModels.add("TATA");
        myCarModels.add("Honda");
        myCarModels.add("Mistubishi");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, myCarModels);
        car_Model.setAdapter(adapter);


        home_service = (RadioButton)findViewById(R.id.HomeService);
        center_service = (RadioButton)findViewById(R.id.ServicesCenter);


        home_service.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(home_service.isChecked()){
                    center_service.setChecked(false);
                    address.setVisibility(View.VISIBLE);
                }
            }
        });

        center_service.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(center_service.isChecked()){
                    home_service.setChecked(false);
                    address.setVisibility(View.GONE);
                }
            }
        });


        book_now = (Button)findViewById(R.id.bookButton);

        //Date Picker Dialog
        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Appointments.this,onDateSetListener, year, month, day);
                datePickerDialog.setTitle("Choose a Date");
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                month = month+1;
                String date = day +"/"+month+"/"+year;
                choose_date.setText(date);
            }
        };

        choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        choose_time.setText(hourOfDay + ":"+minute);
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("Choose Time");
                timePickerDialog.show();
            }
        });

        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String total_cost = String.valueOf(Costs);
                if(total_cost.equals("")){
                    Toast.makeText(Appointments.this, "Please choose a service",Toast.LENGTH_LONG).show();
                    return;
                }
                Car_Model = car_Model.getText().toString();
                if(Car_Model.equals("")){
                    Toast.makeText(Appointments.this, "Enter your car model!",Toast.LENGTH_LONG).show();
                    return;
                }
                Choose_Date = choose_date.getText().toString();
                if(Choose_Date.equals("")){
                    Toast.makeText(Appointments.this, "Pick a date!",Toast.LENGTH_LONG).show();
                    return;
                }
                Add_Address = address.getText().toString();
                Plate_No = car_plate.getText().toString();
                if(Plate_No.equals("")){
                    Toast.makeText(Appointments.this, "Your car plate number?",Toast.LENGTH_LONG).show();
                    return;
                }
                Choose_Time = choose_time.getText().toString();
                if(Choose_Time.equals("")){
                    Toast.makeText(Appointments.this, "Set the time",Toast.LENGTH_LONG).show();
                    return;
                }
                Car_Problem = car_problem.getText().toString();
                if(Car_Problem.equals("")){
                    Toast.makeText(Appointments.this, "What's up with your car?",Toast.LENGTH_LONG).show();
                    return;
                }

                easyMechAuth = FirebaseAuth.getInstance();
                easyMechCurrentUser = easyMechAuth.getCurrentUser();
                easyMechDriverRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(easyMechCurrentUser.getUid());
                String user_id = easyMechAuth.getCurrentUser().getUid();


                easyMechDriverRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String driver_name = dataSnapshot.child("name").getValue().toString();
                        String driver_Email = dataSnapshot.child("email").getValue().toString();
                        String driver_Phone = dataSnapshot.child("mobile").getValue().toString();

                        Appointments.setDriverDetails(driver_name, driver_Phone, driver_Email);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Appointments.this,"Error Loading User Details",Toast.LENGTH_LONG).show();
                    }
                });



                easyMechRef = FirebaseDatabase.getInstance().getReference();
                easyMechRef2 = FirebaseDatabase.getInstance().getReference().child("Drivers").child(user_id).child("Appointments_Lists").push();
                easyMechRef2.child("Appointment_Date").setValue(Choose_Date);
                easyMechRef2.child("Appointment_Time").setValue(Choose_Time);
                easyMechRef2.child("Required_Service").setValue(Car_Problem);
                easyMechRef2.child("Total_Cost").setValue(total_cost);
                easyMechRef2.child("Status").setValue("Under Process");
                easyMechRef2.child("Extra_Services").setValue(Services);

                setMechName(Mechanic_Name);

                //easyMechAuth = FirebaseAuth.getInstance();
                //String user_id = easyMechAuth.getCurrentUser().getUid();
                easyMechRef3 = FirebaseDatabase.getInstance().getReference().child("Admin").child("User_Appointments").child("Under Process").child(user_id).push();
                easyMechRef3.child("Service Center").setValue(Mechanic_Name);
                easyMechRef3.child("Appointment_Date").setValue(Choose_Date);
                easyMechRef3.child("Appointment_Time").setValue(Choose_Time);
                easyMechRef3.child("Total_Cost").setValue(total_cost);



                easyMechDriverRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Driver_name = dataSnapshot.child("name").getValue().toString();
                        Driver_Email = dataSnapshot.child("email").getValue().toString();
                        Driver_Phone = dataSnapshot.child("mobile").getValue().toString();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Appointments.this,"Error Loading User Details",Toast.LENGTH_LONG).show();
                    }
                });

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("Driver_Name", Driver_name);
                userMap.put("Driver_Email", Driver_Email);
                userMap.put("Driver_Phone", Driver_Phone);
                userMap.put("Car_Model", Car_Model);
                userMap.put("Car_Plate", Plate_No);
                userMap.put("Driver_Address", Add_Address);
                userMap.put("Appointment_Date",Choose_Date);
                userMap.put("Appointment_Time", Choose_Time);
                userMap.put("Extra_Services",Services);
                userMap.put("Total_Cost", total_cost);
                userMap.put("Status", "Under Process");
                userMap.put("Required_Service",Car_Problem);
                userMap.put("driver_id",user_id);



                easyMechRef.child("Service Centers").child(Mechanic_Name).child("Appointments").push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Appointments.this, "Your Appointment Is Under Process!", Toast.LENGTH_LONG).show();

                            createNotificationChannel();
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                            builder.setSmallIcon(R.drawable.easymech_icon);
                            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.easymech_icon));
                            builder.setContentTitle("Hi Mr. "+Driver_name+ " Thanks For Choosing Our Services");
                            builder.setStyle(new NotificationCompat.InboxStyle()
                                    .addLine(" Your appointment is under processor for Car Model: "+Car_Model +"\n Plate Number: "+Plate_No).addLine("Date Of Appointment: "+Choose_Date+" at "+Choose_Time)
                                    .setSummaryText("You Will Be Notified Again Later!")
                            );
                            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Appointments.this);
                            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                            finish();
                        }
                        else{
                            Toast.makeText(Appointments.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });


                HashMap<String, String> DriverMap = new HashMap<>();
                userMap.put("Appointment_Date",Choose_Date);
                userMap.put("Appointment_Time", Choose_Time);
                userMap.put("Extra_Services", Services);
                userMap.put("Required_Service",Car_Problem);
                userMap.put("Total_Cost", total_cost);
                userMap.put("Status", "Under Process");



            }
        });

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "InboxStyleNotification Notification";
            String description = "Include all the InboxStyleNotification notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public String setMechName(String name){
        Mechanic_Name = name;
        return Mechanic_Name;
    }
    public String getMechName(){
        return Mechanic_Name;
    }
    public static void setDriverDetails(String name, String phone, String email){
        Driver_Phone = phone;
        Driver_name = name;
        Driver_Email = email;
    }
}




