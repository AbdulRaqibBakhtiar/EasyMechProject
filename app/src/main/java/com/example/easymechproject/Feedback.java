package com.example.easymechproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Feedback extends AppCompatActivity {

        private EditText mEditTextTo;
        private EditText mEditTextSubject;
        private EditText mEditTextMessage;
        TextView mTextView;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feedback);

            mTextView= findViewById(R.id.edit_text_to);
            mEditTextSubject = findViewById(R.id.edit_text_subject);
            mEditTextMessage = findViewById(R.id.edit_text_message);

            toolbar = (Toolbar) findViewById(R.id.tool_Bar);
            toolbar.setTitle("Give us a feedback");
            setSupportActionBar(toolbar);

            Button buttonSend = findViewById(R.id.button_send);
            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMail();
                }
            });
        }

        private void sendMail() {
            String recipientList = mTextView.getText().toString();
            String[] recipients = recipientList.split(",");

            String subject = mEditTextSubject.getText().toString();
            String message = mEditTextMessage.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an email client"));
        }
    }
