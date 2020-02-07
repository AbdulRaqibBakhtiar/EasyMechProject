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
import java.util.List;
import java.util.Locale;

public class AdapterList extends BaseAdapter{

    private  Context mContext;
    ArrayList<Mechanics> easyMechList;
    //String titles, diss;
    LayoutInflater inflater;
    List<Mechanics> easyMechMechanics;

    public AdapterList(Context context, List<Mechanics> easyMechMechanics) {
        this.mContext = context;
        this.easyMechMechanics = easyMechMechanics;
        inflater = LayoutInflater.from(context);
        this.easyMechList = new ArrayList<Mechanics>();
        this.easyMechList.addAll(easyMechMechanics);
    }

    public class ViewHolder{
        TextView title, describe;
        ImageView image;
    }
    @Override
    public int getCount() {
        return easyMechMechanics.size();
    }

    @Override
    public Object getItem(int position) {
        return easyMechMechanics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_layout,null);

            holder.title = view.findViewById(R.id.titles);
            holder.describe = view.findViewById(R.id.description);
            holder.image = view.findViewById(R.id.images);

            view.setTag(holder);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String titles = holder.title.getText().toString();
                    String diss = holder.describe.getText().toString();
                    for(int i=0;i<getCount();i++){
                        if(position==i){
                            Intent int2 = new Intent(mContext,Mechanic_Profile_Options.class);
                            int2.putExtra("title",titles);
                            int2.putExtra("describe",diss);
                           // int2.putExtra("images",image);
                            mContext.startActivity(int2);
                        }
                    }
                }
            });


        }
        else {
            holder = (ViewHolder)view.getTag();
        }


        holder.title.setText(easyMechMechanics.get(position).getTitle());
        holder.describe.setText(easyMechMechanics.get(position).getDescription());
        holder.image.setImageResource(easyMechMechanics.get(position).getImages());

        return view;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        easyMechMechanics.clear();
        if(charText.length()==0){
            easyMechMechanics.addAll(easyMechList);

        }
        else {
            for(Mechanics mechanic : easyMechList){
                if(mechanic.getTitle().toLowerCase(Locale.getDefault()).contains(charText)){
                    easyMechMechanics.add(mechanic);
                }
            }
        }
        notifyDataSetChanged();

    }

}
