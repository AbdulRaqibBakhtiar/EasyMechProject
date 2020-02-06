package com.example.easymechproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterList extends BaseAdapter{

    private  Context context;
    ArrayList<Mechanics> mechanics;
    String titles, diss;
    LayoutInflater inflater;

    public AdapterList(Context context, ArrayList<Mechanics> mechanics) {
        this.context = context;
        this.mechanics = mechanics;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mechanics.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        if(view==null){
            view = inflater.inflate(R.layout.listview_layout,null);
        }

        view = LayoutInflater.from(context).inflate(R.layout.listview_layout, parent, false);

        final TextView title = view.findViewById(R.id.titles);
        final TextView describe = view.findViewById(R.id.description);

        title.setText(mechanics.get(position).getTitle());
        describe.setText(mechanics.get(position).getDescription());
        final int imges = mechanics.get(position).getImages();


      ImageView image;
        image= view.findViewById(R.id.images);
        image.setImageDrawable(view.getResources().getDrawable(imges));


        //image.setImageResource(mechanics.get(position).getImages());


        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                titles = title.getText().toString();
                diss = describe.getText().toString();
                for(int i=0;i<getCount();i++){
                    if(position==i){
                        Intent int2 = new Intent(context,Mechanic_Profile_Options.class);
                        int2.putExtra("title",titles);
                        int2.putExtra("describe",diss);
                        int2.putExtra("images",imges);
                        context.startActivity(int2);
                    }
                }
            }
        });

        return view;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mechanics.clear();
        if(charText.length()==0){
            mechanics.addAll(mechanics);

        }
        else {
            for(Mechanics mechanic : mechanics){
                if(mechanic.getTitle().toLowerCase(Locale.getDefault()).contains(charText)){
                    mechanics.add(mechanic);
                }
            }
        }
        notifyDataSetChanged();

    }

}
