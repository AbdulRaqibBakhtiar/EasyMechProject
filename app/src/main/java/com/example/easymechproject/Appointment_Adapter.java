package com.example.easymechproject;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.AppointmentViewHolder> {

    Context mCtx;
    List<DataSetFire> appointsList;
    DatabaseReference easyMechRemover, easyMechCompletor, getEasyMechCancel, getEasyMechDriver,easyMechRef3;
    FirebaseAuth easyMechAuth;
    FirebaseUser easyMechCurrentUser;

    public Appointment_Adapter(Context mCtx, List<DataSetFire> appointsList) {
        this.mCtx = mCtx;
        this.appointsList = appointsList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.appointments_view_holder, parent, false);
        AppointmentViewHolder appointmentViewHolder = new AppointmentViewHolder(view);

        return appointmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        DataSetFire dataSetFire = appointsList.get(position);

        holder.D_name.setText(dataSetFire.getDriver_Name());
        holder.D_num.setText(dataSetFire.getDriver_Phone());
        holder.Date.setText(dataSetFire.getAppointment_Date());
        holder.Time.setText(dataSetFire.getAppointment_Time());
        holder.Service.setText(dataSetFire.getExtra_Services());
        holder.Cost.setText("Rs. "+dataSetFire.getTotal_Cost());
        holder.id.setText(dataSetFire.getCar_Plate());

        holder.list_service = dataSetFire.getRequired_Service();
        holder.list_phone = dataSetFire.getDriver_Phone();
        holder.list_email = dataSetFire.getDriver_Email();
        holder.list_plate = dataSetFire.getCar_Plate();
        holder.list_model = dataSetFire.getCar_Model();
        holder.list_address = dataSetFire.getDriver_Address();
        holder.list_status = dataSetFire.getStatus();

        holder.list_name = dataSetFire.getDriver_Name();
        holder.list_num = dataSetFire.getDriver_Phone();
        holder.list_date = dataSetFire.getAppointment_Date();
        holder.list_time = dataSetFire.getAppointment_Time();
        holder.list_extra = dataSetFire.getExtra_Services();
        holder.list_cost = dataSetFire.getTotal_Cost();


    }

    @Override
    public int getItemCount() {
        return appointsList.size();
    }

    public class AppointmentViewHolder extends  RecyclerView.ViewHolder{

        TextView D_name, D_num, Date, Time, Service, Cost, id;
        TextView Accept, Reject, Cancel;

        String list_name;
        String list_num;
        String list_date;
        String list_time;
        String list_extra;
        String list_cost;
        String list_service;
        String list_phone;
        String list_email;
        String list_plate;
        String list_model;
        String list_address;
        String list_status;

        String Service_Name;


        public AppointmentViewHolder(@NonNull final View itemView) {
            super(itemView);

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

            easyMechRemover = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
            easyMechRemover.keepSynced(true);
            easyMechCompletor = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
            easyMechCompletor.keepSynced(true);
            getEasyMechCancel = FirebaseDatabase.getInstance().getReference("Service Centers/"+Service_Name+"/Appointments");
            getEasyMechDriver = FirebaseDatabase.getInstance().getReference("Drivers");


            D_name =(TextView)itemView.findViewById(R.id.d_name);
            D_num = (TextView)itemView.findViewById(R.id.d_number);
            Date = (TextView)itemView.findViewById(R.id.d_date);
            Time = (TextView)itemView.findViewById(R.id.d_time);
            Service = (TextView)itemView.findViewById(R.id.d_service);
            Cost = (TextView)itemView.findViewById(R.id.d_cost);
            id = (TextView)itemView.findViewById(R.id.d_id);



            Accept = (TextView)itemView.findViewById(R.id.d_accept);
            Reject = (TextView)itemView.findViewById(R.id.d_reject);
            Cancel = (TextView)itemView.findViewById(R.id.d_cancel);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent appoinemtns = new Intent(mCtx,Appointment_Holder.class);

                    appoinemtns.putExtra("name", list_name);
                    appoinemtns.putExtra("address",list_address);
                    appoinemtns.putExtra("number",list_num);
                    appoinemtns.putExtra("date",list_date);
                    appoinemtns.putExtra("time",list_time);
                    appoinemtns.putExtra("extra",list_extra);
                    appoinemtns.putExtra("cost", list_cost);
                    appoinemtns.putExtra("service",list_service);
                    appoinemtns.putExtra("phone",list_phone);
                    appoinemtns.putExtra("email",list_email);
                    appoinemtns.putExtra("plate",list_plate);
                    appoinemtns.putExtra("model",list_model);
                    appoinemtns.putExtra("status",list_status);
                    appoinemtns.putExtra("Service_Center",Service_Name);
                    mCtx.startActivity(appoinemtns);
                }
            });

            Reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String isd = id.getText().toString();
                    final String datess = list_date;

                    String user_id = easyMechAuth.getCurrentUser().getUid();
                    easyMechRef3 = FirebaseDatabase.getInstance().getReference().child("Admin").child("User_Appointments").child("Rejected_Cancelled").child(user_id).push();
                    easyMechRef3.child("Service Center").setValue(Service_Name);
                    easyMechRef3.child("Appointment_Date").setValue(list_date);
                    easyMechRef3.child("Appointment_Time").setValue(list_time);
                    easyMechRef3.child("Total_Cost").setValue(list_cost);
                    easyMechRef3.child("Status").setValue("Rejected");

                    easyMechRemover.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(final DataSnapshot childs : dataSnapshot.getChildren()){
                                final String key = childs.getKey();
                                final String field = dataSnapshot.child(key).child("Car_Plate").getValue().toString();
                                if(field.equals(isd)){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                    builder.setCancelable(true);
                                    builder.setTitle("Reject This Request");
                                    builder.setMessage("Are you sure?\nRejecting Client requests will have negative nmpact on your reviews!");
                                    builder.setPositiveButton("Reject Anyway",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    getEasyMechCancel.child(key).child("Status").setValue("Cancelled");
                                                    final String user_id = dataSnapshot.child(key).child("driver_id").getValue().toString();
                                                    getEasyMechDriver.child(user_id).child("Appointments_Lists").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot driver: dataSnapshot.getChildren()){
                                                                final String key2 = driver.getKey();
                                                                final String date2 = dataSnapshot.child(key2).child("Appointment_Date").getValue().toString();

                                                                if(date2.equals(datess)){
                                                                    Toast.makeText(mCtx,"The Appointment has been cancelled!",Toast.LENGTH_LONG).show();
                                                                    getEasyMechDriver.child(user_id).child("Appointments_Lists").child(key2).child("Status").setValue("Rejected");
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

            Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String plate2 = list_plate;
                    final String datess = list_date;

                    String user_id = easyMechAuth.getCurrentUser().getUid();
                    easyMechRef3 = FirebaseDatabase.getInstance().getReference().child("Admin").child("User_Appointments").child("Accepted").child(user_id).push();
                    easyMechRef3.child("Service Center").setValue(Service_Name);
                    easyMechRef3.child("Appointment_Date").setValue(list_date);
                    easyMechRef3.child("Appointment_Time").setValue(list_time);
                    easyMechRef3.child("Total_Cost").setValue(list_cost);
                    easyMechRef3.child("Status").setValue("Accepted");

                    easyMechCompletor.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(final DataSnapshot childs : dataSnapshot.getChildren()){
                                final String key = childs.getKey();
                                final String field = dataSnapshot.child(key).child("Car_Plate").getValue().toString();
                                if(field.equals(plate2)){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                    builder.setCancelable(true);
                                    builder.setTitle("Accept Appointment");
                                    builder.setMessage("Accept the appointment ?");
                                    builder.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    getEasyMechCancel.child(key).child("Status").setValue("Accepted");
                                                    final String user_id = dataSnapshot.child(key).child("driver_id").getValue().toString();
                                                    getEasyMechDriver.child(user_id).child("Appointments_Lists").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot driver: dataSnapshot.getChildren()){
                                                                final String key2 = driver.getKey();
                                                                final String date2 = dataSnapshot.child(key2).child("Appointment_Date").getValue().toString();

                                                                if(date2.equals(datess)){
                                                                    Toast.makeText(mCtx,"You have accepted the appointmet!",Toast.LENGTH_LONG).show();
                                                                    getEasyMechDriver.child(user_id).child("Appointments_Lists").child(key2).child("Status").setValue("Accpeted");
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
}
