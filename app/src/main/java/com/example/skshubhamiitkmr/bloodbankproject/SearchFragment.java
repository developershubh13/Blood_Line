package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skshubhamiitkmr on 14-03-2018.
 */

public class SearchFragment extends Fragment {


    View view;
    private TextView compatibility;

    float value[]={12.4f,12.6f,12.3f,12.7f,12.2f,12.8f,12.1f,12.9f};
    String BloodGroupName[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};


    public SearchFragment() {

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_search_fragment,container,false);

        compatibility=(TextView) view.findViewById(R.id.compatibility);


        compatibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(getLayoutInflater().inflate(R.layout.compatibility_layout
                        , null));
                dialog.show();

            }
        });



        setupPieChart();
        return view;
    }

    private void setupPieChart() {



        List<PieEntry> pieEntries=new ArrayList<>();
        for(int i=0;i<BloodGroupName.length;i++)
        {
            pieEntries.add(new PieEntry(value[i],BloodGroupName[i]));
        }



        ArrayList<Integer> colors=new ArrayList<>();
        int myColor1=Color.parseColor("#e53935");
        int myColor2=Color.parseColor("#e57373");


        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);



        PieDataSet  dataset =new PieDataSet(pieEntries," ");
        dataset.setColors(colors);
        dataset.setValueTextSize(0);
        dataset.setSliceSpace(2);
        PieData data =new PieData(dataset);


        PieChart chart=(PieChart) view.findViewById(R.id.chart);
        chart.setData(data);
        chart.setHoleRadius(45f);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setCenterText(" Select Blood Group  ");


        chart.setDrawEntryLabels(true);

        chart.animateY(3000);
        chart.invalidate();


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {



                int pos1=e.toString().indexOf("y: ");
                String group=e.toString().substring(pos1+3);

                for(int i=0;i<value.length;i++){
                    if(value[i]== Float.parseFloat(group)){
                        pos1=i;
                        break;
                    }
                }


                String BloodGroup=BloodGroupName[pos1];

                    Intent intent = new Intent(getActivity(), DonorSearch.class);
                    intent.putExtra("message", BloodGroup);
                    startActivity(intent);



            }

            @Override
            public void onNothingSelected() {

            }
        });


    }


}
