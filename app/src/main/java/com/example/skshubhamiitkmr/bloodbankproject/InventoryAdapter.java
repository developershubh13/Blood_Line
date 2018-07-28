package com.example.skshubhamiitkmr.bloodbankproject;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skshubhamiitkmr on 04-04-2018.
 */

public class InventoryAdapter extends ArrayAdapter<Inventory> {

    private Context mcontext;
    int mresource;
    //ArrayList<Inventory> inve


    public InventoryAdapter(@NonNull Context context, int resource, @NonNull List<Inventory> objects) {
        super(context, resource, objects);

        mcontext=context;
        mresource=resource;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        String group=getItem(position).getGroup();
        String stock=getItem(position).getStock();
        String donor=getItem(position).getDonor();


        Inventory inventory=new Inventory(group,stock,donor);

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresource,parent,false);


        TextView grouptext=(TextView) convertView.findViewById(R.id.first_text);
        TextView stocktext=(TextView) convertView.findViewById(R.id.second_text);
        TextView donortext=(TextView) convertView.findViewById(R.id.third_text);


        grouptext.setText(group);
        stocktext.setText(stock);
        donortext.setText(donor);



        if(position%2==0)
        {
            convertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }else
        {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }





        return convertView;
    }
}
