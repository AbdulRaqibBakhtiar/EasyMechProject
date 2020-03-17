package com.example.easymechproject;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.AppointmentViewHolder> {

    Context mCtx;
    List<Note_Collector> appointsList;
    DatabaseReference easyMechRemover, easyMechCanceller;
    FirebaseAuth easyMechAuth;

    private final String CHANNEL_ID = "inbox_style_notification";
    private final int NOTIFICATION_ID = 04;

    public Notification_Adapter(Context mCtx, List<Note_Collector> appointsList) {
        this.mCtx = mCtx;
        this.appointsList = appointsList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.note_view_holder, parent, false);
        AppointmentViewHolder appointmentViewHolder = new AppointmentViewHolder(view);

        return appointmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Note_Collector notes = appointsList.get(position);


        holder.Date.setText(notes.getAppointment_Date());
        holder.Time.setText(notes.getAppointment_Time());
        holder.Service.setText(notes.getRequired_Service());
        holder.Cost.setText("Rs. "+notes.getTotal_Cost());
        holder.Extra_service.setText(notes.getExtra_Services());
        holder.D_status.setText(notes.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointsList.size();
    }

    public class AppointmentViewHolder extends  RecyclerView.ViewHolder{

        TextView D_status, Date, Time, Service, Cost, Extra_service;
        ImageView img;
        TextView Cancel;


        private void createNotificationChannel(){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                CharSequence name = "InboxStyleNotification Notification";
                String description = "Include all the InboxStyleNotification notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationChannel.setDescription(description);

                NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(mCtx.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        public AppointmentViewHolder(@NonNull final View itemView) {
            super(itemView);

            easyMechAuth = FirebaseAuth.getInstance();
            String user_id = easyMechAuth.getCurrentUser().getUid();

            easyMechRemover = FirebaseDatabase.getInstance().getReference("Drivers/"+user_id+"/Appointments_Lists");
            easyMechRemover.keepSynced(true);
            easyMechCanceller = FirebaseDatabase.getInstance().getReference("Drivers/"+user_id+"/Appointments_Lists");
            easyMechCanceller.keepSynced(true);



            D_status =(TextView)itemView.findViewById(R.id.d_status);
            Date = (TextView)itemView.findViewById(R.id.d_date);
            Time = (TextView)itemView.findViewById(R.id.d_time);
            Service = (TextView)itemView.findViewById(R.id.d_service);
            Cost = (TextView)itemView.findViewById(R.id.d_cost);
            Extra_service = (TextView)itemView.findViewById(R.id.d_extra);



            Cancel = (TextView)itemView.findViewById(R.id.d_cancel);

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String date = Date.getText().toString();
                    final String service = Service.getText().toString();
                    easyMechRemover.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(final DataSnapshot childs : dataSnapshot.getChildren()){
                                final String key = childs.getKey();
                                final String app_date = dataSnapshot.child(key).child("Appointment_Date").getValue().toString();

                                if(app_date.equals(date)){
                                    if(dataSnapshot.child(key).child("Status").getValue().equals("Cancelled")){
                                        Toast.makeText(mCtx,"Cancelled "+date,Toast.LENGTH_LONG).show();
                                    }


                                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                    builder.setCancelable(true);
                                    builder.setTitle("Cancel This Request");
                                    builder.setMessage("Are you sure?\nThis appointment will be cancelled!");
                                    builder.setPositiveButton("Cancel Anyway",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    easyMechCanceller.child(key).child("Status").setValue("Cancelled").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(mCtx, "Request Completed!", Toast.LENGTH_LONG).show();

                                                                createNotificationChannel();
                                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx, CHANNEL_ID);
                                                                builder.setSmallIcon(R.drawable.easymech_icon);
                                                                builder.setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(),R.drawable.easymech_icon));
                                                                builder.setContentTitle("Your appointment has been cancelled!");
                                                                builder.setStyle(new NotificationCompat.InboxStyle()
                                                                        .addLine(" Your appointment for service request: "+service+" has been cancelled!")
                                                                        .addLine("For reporting problems and complains feel free to contact us")
                                                                        .setSummaryText("EasyMech Team")
                                                                );
                                                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mCtx);
                                                                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                                                            }
                                                            else{
                                                                Toast.makeText(mCtx, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                    Toast.makeText(mCtx,"The appointment is cancelled!",Toast.LENGTH_LONG).show();
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
