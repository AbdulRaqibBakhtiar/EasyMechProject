package com.example.easymechproject;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChatApp extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessages> adapter;


    FloatingActionButton Fab;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_app);

        displayChatMessage();

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_messages);
        String get_user = FirebaseAuth.getInstance().getCurrentUser().getUid();




        Fab = (FloatingActionButton)findViewById(R.id.fab);

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputs = (EditText)findViewById(R.id.input_message);
                String message = inputs.getText().toString();
                String get_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //FirebaseDatabase.getInstance().getReference().child("Drivers").child(get_user).child("Messages").push().setValue(new ChatMessages(message,FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                FirebaseDatabase.getInstance().getReference().child("Mechanics").child("Messages").push().setValue(new ChatMessages(message,FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                inputs.setText("");
            }
        });

    }

    private void displayChatMessage() {

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_messages);

        String get_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Drivers/"+get_user+"/Messages");
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Mechanics/Messages");
        adapter = new FirebaseListAdapter<ChatMessages>(this,ChatMessages.class,R.layout.message_list_item,Ref) {
            @Override
            protected void populateView(View v, ChatMessages model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView)v.findViewById(R.id.messages_user);
                messageUser = (TextView)v.findViewById(R.id.message_user);
                messageTime = (TextView)v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("HH:mm",model.getMessageTime()));
            }
        };
        listOfMessage.setAdapter(adapter);
    }
}
