package com.example.easymechproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UploadProfileImage extends AppCompatActivity {


    private DatabaseReference easyMechDriverRef;
    private FirebaseAuth easyMechAuth;

    private DatabaseReference easyMechDBRef, reference;
    private FirebaseUser easyMechCurrentUser;


    private TextView upDate, userEmail;
    private EditText userName, userNumber;
    private CircleImageView imageView;
    public String photo_name;
    private Spinner spinner;

    public String username, useremail, usernumber;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference, mStorageRef;

    private DatabaseReference easyMechRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_image);

        spinner = (Spinner)findViewById(R.id.mobile_spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,CountryData.countryISO));

        upDate = findViewById(R.id.update_user_details);
        imageView = findViewById(R.id.user_profile_image);



        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();
        easyMechDriverRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(easyMechCurrentUser.getUid());

        easyMechDBRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(easyMechCurrentUser.getUid());
        easyMechDBRef.keepSynced(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        final StorageReference ref = storageReference.child("Users/"+uid+"/"+"profile_photo.jpg");



        easyMechDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userEmail = (TextView) findViewById(R.id.my_email);
                userName = (EditText)findViewById(R.id.my_name);
                userNumber = (EditText)findViewById(R.id.my_number);


                String name = dataSnapshot.child("name").getValue().toString();
                //final String image = dataSnapshot.child("image").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                final String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String phone = dataSnapshot.child("mobile").getValue().toString();


                if(thumb_image.equals("profile_photo")){
                    Glide.with(UploadProfileImage.this)
                            .using(new FirebaseImageLoader()) // Cannot resolve method 'using
                            .load(ref)
                            .into(imageView);
                }

                userName.setText(name);
                userNumber.setText(phone);
                userEmail.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UploadProfileImage.this,"Error Loading User Details",Toast.LENGTH_LONG).show();
            }
        });



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        upDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                easyMechRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(uid);

                userName = (EditText)findViewById(R.id.my_name);
                userNumber = (EditText)findViewById(R.id.my_number);

                username = userName.getText().toString();
                usernumber = userNumber.getText().toString();

                //photo_name = UUID.randomUUID().toString();

                easyMechRef.child("name").setValue(username);
                easyMechRef.child("mobile").setValue(usernumber);
                easyMechRef.child("thumb_image").setValue("profile_photo");


                uploadImage();

            }
        });
    }

    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    private void uploadImage()
    {
        if (filePath != null) {

            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            storageReference = FirebaseStorage.getInstance().getReference();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = currentUser.getUid();
            final StorageReference ref
                    = storageReference
                    .child(
                            "Users/"+uid+"/"
                                    + "profile_photo.jpg");

            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(UploadProfileImage.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    Toast.makeText(UploadProfileImage.this,"Profile Updated Successfully!",Toast.LENGTH_LONG).show();;
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast
                                    .makeText(UploadProfileImage.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null
        && data.getData() !=null){
            filePath = data.getData();
            Glide.with(this).load(filePath).into(imageView);
            imageView.setImageURI(filePath);
        }
    }
}