package com.example.easymechproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private static final int RESULT_OK = 1;
    private DatabaseReference easyMechDriverRef;
    private FirebaseAuth easyMechAuth;

    private DatabaseReference easyMechDBRef, reference;
    private FirebaseUser easyMechCurrentUser;

    CircleImageView profilePhoto;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    public Uri imageUri;
    private StorageTask uploadTask;

    private static final int GALLERY_PICK = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION = 1;
    private static final int READ_EXTERNAL_STORAGE = 1;
    Context context;



    private TextView user_name, user_email, user_phone, profile_names, profile_emails, profile_logout;
    private TextView user_logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        user_logout = (TextView)rootView.findViewById(R.id.user_update_key);
        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UploadProfileImage.class));
            }
        });

        profilePhoto = (CircleImageView) rootView.findViewById(R.id.profile_picture);

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), UploadProfileImage.class));
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        final StorageReference ref
                = storageReference
                .child(
                        "Users/"+uid+"/"
                                + "profile_photo.jpg");




        user_name = (TextView) rootView.findViewById(R.id.user_account_name);
//        user_email = (TextView) rootView.findViewById(R.id.user_email);
        user_phone = (TextView) rootView.findViewById(R.id.user_phone);
        profile_emails = (TextView) rootView.findViewById(R.id.profile_email);
        profile_names = (TextView) rootView.findViewById(R.id.profile_name);
        profile_logout = (TextView) rootView.findViewById(R.id.user_logout_key);



        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyMechAuth.getInstance()
                        .signOut();
                startActivity(new Intent(getContext(),Base_Home.class));
            }
        });

        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();
        easyMechDriverRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(easyMechCurrentUser.getUid());

        easyMechDBRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(easyMechCurrentUser.getUid());
        easyMechDBRef.keepSynced(true);

        easyMechDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                //final String image = dataSnapshot.child("image").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                final String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String phone = dataSnapshot.child("mobile").getValue().toString();


                if(thumb_image.equals("profile_photo")){

                    Glide.with(getContext())
                            .using(new FirebaseImageLoader()) // Cannot resolve method 'using
                            .load(ref)
                            .into(profilePhoto);
                }

               // String image = dataSnapshot.child("thumb_image").getValue().toString();

               // Picasso.get().load(image).placeholder(R.drawable.default_profile).into(profilePhoto);




                user_name.setText(name);
               // user_email.setText(email);
                user_phone.setText(phone);
                profile_names.setText(name);
                profile_emails.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error Loading User Details",Toast.LENGTH_LONG).show();
            }
        });

        


        return rootView;
    }
}
