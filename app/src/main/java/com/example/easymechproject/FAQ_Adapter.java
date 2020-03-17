package com.example.easymechproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FAQ_Adapter extends BaseAdapter {
    Context context;
    ArrayList<FAQ_Viewer> arrayList;

    public FAQ_Adapter(Context context, ArrayList<FAQ_Viewer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.faq_list_item, parent, false);

        TextView question = convertView.findViewById(R.id.q_title);
        TextView answer = convertView.findViewById(R.id.q_description);

        question.setText(arrayList.get(position).getTitle());
        answer.setText(arrayList.get(position).getContent());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "FAQ", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
