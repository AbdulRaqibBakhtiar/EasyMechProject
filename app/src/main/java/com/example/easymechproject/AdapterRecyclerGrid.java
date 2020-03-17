package com.example.easymechproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdapterRecyclerGrid extends RecyclerView.Adapter<AdapterRecyclerGrid.MyHolder> {

    Context context;
    ArrayList<Services_Resources> arrayList;


    public AdapterRecyclerGrid(Context context, ArrayList<Services_Resources> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.services_grid_view, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    public void onBindViewHolder(@NonNull MyHolder myHolder, int i){
        myHolder.titles.setText(arrayList.get(i).getTitle());
        myHolder.imag.setImageResource(arrayList.get(i).getImages());
        myHolder.descript.setText(arrayList.get(i).getDescript());
    }

    public int getItemCount(){
        return arrayList.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView titles;
        ImageView imag;
        TextView descript;
        private Context context;


        public MyHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();

            titles = itemView.findViewById(R.id.services_title);
            imag = itemView.findViewById(R.id.service_images);
            descript = itemView.findViewById(R.id.services_descript);

            itemView.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {

                    if(getAdapterPosition()==0){
                        context.startActivity(new Intent(context, MainMenu_View.class));
                    }
                    else if(getAdapterPosition()==1){
                        context.startActivity(new Intent(context, Batteriesss.class));
                    }
                    else if(getAdapterPosition()==2){
                        context.startActivity(new Intent(context, Engine_Transmisson.class));
                    }
                    else if(getAdapterPosition()==3){
                        context.startActivity(new Intent(context, Oil_Lube_Filter.class));
                    }
                    else if(getAdapterPosition()==4){
                        context.startActivity(new Intent(context, Paint_Denting.class));
                    }
                    else if(getAdapterPosition()==5){
                        context.startActivity(new Intent(context, Wheel_Tires.class));
                    }
                    else if(getAdapterPosition()==6) {
                        FirebaseAuth easyMechAuth = FirebaseAuth.getInstance();
                        FirebaseUser easyMechCurrentUser = easyMechAuth.getCurrentUser();
                        if(easyMechCurrentUser.getEmail().toString().equals("admin@email.com")){
                            context.startActivity(new Intent(context, Booking_Services.class));
                        }

                        easyMechAuth = FirebaseAuth.getInstance();
                        easyMechCurrentUser = easyMechAuth.getCurrentUser();
                        String service_email = easyMechCurrentUser.getEmail();
                        if(service_email.equals("autocarexperts@gmail.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                        else if(service_email.equals("hyderkhan.goa@gmail.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                        else if(service_email.equals("sharayutoyota@yahoo.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                        else if(service_email.equals("autopro@hotmail.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                        else if(service_email.equals("alconhyndai@gmail.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                        else if(service_email.equals("bavariamotors@gmail.com")){
                            context.startActivity(new Intent(context, Client_List.class));
                        }
                    }
                    else if(getAdapterPosition()==7){
                        context.startActivity(new Intent(context, Manage_Service_Centers.class));
                    }
                    else if(getAdapterPosition()==8){
                        context.startActivity(new Intent(context, Manage_System_Users.class));
                    }
                }
            });
        }
    }
}
