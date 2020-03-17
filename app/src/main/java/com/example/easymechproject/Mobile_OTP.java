package com.example.easymechproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Mobile_OTP extends AppCompatActivity {
    private Button _confirm;
    private String verificationID;
    private FirebaseAuth Verify;
    private EditText code_text;
    private String code;
    String name;
    private ProgressBar PB;
    ProgressDialog progressDialog;
    private final String CHANNEL_ID = "inbox_style_notification";
    private final int NOTIFICATION_ID = 04;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__otp);
        code_text =(EditText) findViewById(R.id.OTP_Con);
        PB = findViewById(R.id.progressBar);
        Verify = FirebaseAuth.getInstance();
        _confirm = (Button) findViewById(R.id.confirm_1);
        String phonenumber = getIntent().getStringExtra("phonenumber");
        name = getIntent().getStringExtra("name");
        send_OTP(phonenumber);


        _confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String code = code_text.getText().toString();
                if(code.isEmpty() || code.length()<6 ){
                    code_text.setError("Enter code...");;
                    code_text.requestFocus();
                    PB.setVisibility(View.VISIBLE);

                    return;
                }
                PB.setVisibility(View.VISIBLE);
               /* progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("It takes time virify your ");
                progressDialog.show();*/
                verifyCode(code);

            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credit = PhoneAuthProvider.getCredential(verificationID, code);
        SignIn(credit);
    }

    private void SignIn(PhoneAuthCredential credit) {
        Verify.signInWithCredential(credit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    createNotificationChannel();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.easymech_icon);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.easymech_icon));
                    builder.setContentTitle("Welcome to EasyMech!");
                    builder.setStyle(new NotificationCompat.InboxStyle()
                            .addLine(" Hi Mr. "+name+" welcome to EasyMech")
                            .addLine("EasyMech aims to provide you the best vehicle repairment and maintenance services")
                            .setSummaryText("Your EasyMech Team")
                    );
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Mobile_OTP.this);
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

                    Intent int2 = new Intent(Mobile_OTP.this, Base_Home.class);
                    int2.setFlags(int2.FLAG_ACTIVITY_NEW_TASK | int2.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(int2);

                }
                else{
                    Toast.makeText(Mobile_OTP.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void send_OTP(String number) {
        PB.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                code_text.setText(code);
                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Mobile_OTP.this, e.getMessage(),Toast.LENGTH_LONG).show();
            PB.setVisibility(View.GONE);
        }
    };

}