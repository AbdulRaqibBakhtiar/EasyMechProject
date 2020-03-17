/*
package com.example.easymechproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

public class FAQ extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        TextView mTextView=findViewById(R.id.allTextView);


        StringBuilder stringBuilder=new StringBuilder();
        String message=
                "What is EasyMech?\n\n" +
                        "EasyMech is a network of technology-enabled car service centres, offering a seamless service experience at the convenience of a tap. With our highly skilled technicians, manufacturer recommended procedures and the promise of genuine spare parts, we are your best bet.\n\n\n" +
                        "Why should I choose EasyMech?\n\n" +
                        "EasyMech offers the best car services and solutions at fair and flexible prices. You end up saving up to 40% compared to what is charged at Authorised Service Centres and Multi-brand workshops\n\n\n" +
                        "How can you offer upto 40% savings on services?\n\n" +
                        "Our distinctive business model enables us to provide affordable car services. We achieve savings on labour costs, centralized bulk procurement of spare parts, no real-estate overheads, and adept operational excellence," +
                        " which are passed on straight to You- the Customer\n\n\n" +
                        "How is EasyMech different from other service platforms out there?\n\n" +
                        "Unlike other platforms, we do not work on a lead generation model. Uncompromised customer gratification is our idea of fulfilment, that is why we own the complete experience right from procurement of spare parts to" +
                        " quality control at our partner car service centres. Our Customer Representative will be on ground duty promptly reporting every development directly to you. EasyMech is your personal car service expert and partner rolled into one.\n\n\n" +
                        "Where can I book a car service with EasyMech?\n\n" + "You can book an EasyMech car service directly from our website or by downloading the exclusive Android App.\n\n\n" +
                        "How can I book a car service?\n\n" +
                        "We have made booking a car service as easy as 1-2-3. Just select you Car’s make, model and fuel type, select the type of car service you require, Choose your preferred time slot And Enjoy! We offer free pick-up and drop-in, so you don’t miss out the cherished moments with your loved ones.\n\n\n" +
                        "What if I am not available to drop my car?\n\n" +
                        "Don't Worry! We’ll take care of everything. We offer free pick-up and drop-in.\n\n\n" +
                        "Do I have to pay before the service?\n\n" +
                        "Not at all. From the booking to delivery, our priority at EasyMech keeps You and Your Car Service first. We will send you the bill once your car is serviced and inspected by our professionals. We offer flexible payment options for your ease. You can still prepay if you choose to.\n\n\n"
                ;

        stringBuilder.append(message);
        mTextView.setText(stringBuilder.toString());



    }
}
*/


package com.example.easymechproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FAQ extends AppCompatActivity {

    ListView faqViewer;
    ArrayList<FAQ_Viewer> list;
    FAQ_Adapter adapterList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        toolbar.setTitle("Frequently Asked Questions");
        setSupportActionBar(toolbar);

        faqViewer = findViewById(R.id.faq_list_view);

        listShow();

        adapterList = new FAQ_Adapter(this, list);
        faqViewer.setAdapter(adapterList);

        TextView questionButton=findViewById(R.id.sendQuestion);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FAQ.this, Send_FAQ.class));
            }
        });

    }



    private void listShow(){
        list = new ArrayList<FAQ_Viewer>();
        list.add(new FAQ_Viewer("What is EasyMech?",
                "EasyMech is a network of technology-enabled car service centres, offering a seamless service experience at the convenience of a tap. With our highly skilled technicians, manufacturer recommended procedures and the promise of genuine spare parts, we are your best bet."));

        list.add(new FAQ_Viewer("Why should I choose EasyMech?",
                "EasyMech offers the best car services and solutions at fair and flexible prices. You end up saving up to 40% " +
                        "compared to what is charged at Authorised Service Centres and Multi-brand workshops"));
        list.add(new FAQ_Viewer("How can you offer upto 40% savings on services?",
                "Our distinctive business model enables us to provide affordable car services. We achieve savings on labour costs," +
                        "centralized bulk procurement of spare parts, no real-estate overheads, and adept operational excellence,"+
                        "which are passed on straight to You- the Customer"));
        list.add(new FAQ_Viewer("How is EasyMech different from other service platforms out there?",
                "Unlike other platforms, we do not work on a lead generation model. Uncompromised customer gratification is our idea of fulfilment," +
                        "that is why we own the complete experience right from procurement of spare parts to"+
                        "quality control at our partner car service centres. Our Customer Representative will be on ground duty promptly" +
                        "reporting every development directly to you. EasyMech is your personal car service expert and partner rolled into one."));
        list.add(new FAQ_Viewer("Where can I book a car service with EasyMech?",
                "You can book an EasyMech car service directly from our website or by downloading the exclusive Android App."));
        list.add(new FAQ_Viewer("How can I book a car service?",
                "We have made booking a car service as easy as 1-2-3. Just select you Car’s make, model and fuel type," +
                        "select the type of car service you require, Choose your preferred time slot And Enjoy!" +
                        "We offer free pick-up and drop-in, so you don’t miss out the cherished moments with your loved ones."));
        list.add(new FAQ_Viewer("What if I am not available to drop my car?",
                "Don't Worry! We’ll take care of everything. We offer free pick-up and drop-in."));
        list.add(new FAQ_Viewer("Do I have to pay before the service?",
                "Not at all. From the booking to delivery, our priority at EasyMech keeps You and Your Car Service first." +
                        "We will send you the bill once your car is serviced and inspected by our professionals." +
                        "We offer flexible payment options for your ease. You can still prepay if you choose to."));

    }
}
