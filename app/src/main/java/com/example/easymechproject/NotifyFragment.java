package com.example.easymechproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotifyFragment extends Fragment {

    RecyclerView recyclerView;
    Notification_Adapter notification_adapter;
    List<Note_Collector> appointsList;
    private DatabaseReference easyMechRef;
    FirebaseAuth easyMechAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = rootView.findViewById(R.id.client_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appointsList = new ArrayList<>();

        easyMechAuth = FirebaseAuth.getInstance();
        String user_id = easyMechAuth.getCurrentUser().getUid();

        easyMechRef = FirebaseDatabase.getInstance().getReference("Drivers/"+user_id+"/Appointments_Lists");
        //................................................................................................................

        easyMechRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    appointsList.clear();
                    for(DataSnapshot appointment : dataSnapshot.getChildren()){
                        Note_Collector d = appointment.getValue(Note_Collector.class);
                        appointsList.add(d);
                    }

                    notification_adapter = new Notification_Adapter(getContext(), appointsList);
                    recyclerView.setAdapter(notification_adapter);
                }


                // appointsList.add(new Note_Collector(date,time,name,num,service,services,cost));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return rootView;
    }
}

