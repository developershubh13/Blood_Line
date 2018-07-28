package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by skshubhamiitkmr on 15-04-2018.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private  View view;

    private ImageView call,maps,email;

    private ViewPager viewPager;
    private LinearLayout sliderDotsPanel;
    private int dataCount;
    private ImageView[] dots;




    ViewFlipper viewFlipper;





    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.home_fragment,container,false);






        viewFlipper=(ViewFlipper) view.findViewById(R.id.view_flipper);

        call=(ImageView) view.findViewById(R.id.home_call);
        email=(ImageView) view.findViewById(R.id.home_email);
        maps=(ImageView) view.findViewById(R.id.home_maps);




        int images[] ={R.drawable.new1,R.drawable.new2,R.drawable.new3};





        for(int image:images)
        {
            flipperImages(image);
        }










        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"7004691916"));
                v.getContext().startActivity(intent);




            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"developershubh13@gmail.com"});
                intent.setType("message/rfc822");
                v.getContext().startActivity(Intent.createChooser(intent,"Choose An Email Client"));
            }
        });


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String add="google.navigation:q=Blood+Bank+Building,Near+DC+Office,Dhatkidih,Jamshedpur";



                Uri mapUri=Uri.parse(add);
                Intent intent =new Intent(Intent.ACTION_VIEW,mapUri);
                intent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(intent);




            }
        });



        return view;
    }



    public void flipperImages(int image){
        ImageView imageView=new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);


        viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_out_right);
        viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_in_left);

    }

}
