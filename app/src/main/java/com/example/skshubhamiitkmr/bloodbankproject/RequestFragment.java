package com.example.skshubhamiitkmr.bloodbankproject;

import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skshubhamiitkmr on 16-04-2018.
 */

public class RequestFragment extends Fragment {


    FloatingActionButton fab;
    View view;

    private String bg;

    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView close;
    private Spinner bloodGroup;
    private EditText units;
    private Button submit;


    private RecyclerView recyclerView;



    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,refrenceRequest,refrenceRoot;
    private FirebaseUser firebaseUser;


    private ValueEventListener valueEventListener;

    private String SeekerBlood,SeekerReqBlood,SeekerReqUnits,SeekerName,SeekerEmail,
            SeekerPhone,SeekerAge,SeekerAdd,SeekerImage,SeekerGender;




    ArrayList<String> seekerName;
    ArrayList<String> seekerEmail;
    ArrayList<String> seekerPhone;
    ArrayList<String> seekerAge;
    ArrayList<String> seekerGender;
    ArrayList<String> seekerImage;

    ArrayList<String> seekerreqBlood;

    ArrayList<String> seekerreqUnits;
    ArrayList<String> seekerBlood;
    ArrayList<String> seekerAdd;



    public RequestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.request_fragment,container,false);


        bottomSheet=(LinearLayout) view.findViewById(R.id.bottomsheet);
        bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        close=(ImageView) view.findViewById(R.id.bottom_sheet_close);
        bloodGroup=(Spinner) view.findViewById(R.id.bottom_sheet_blood);
        units=(EditText) view.findViewById(R.id.bottom_sheet_units);
        submit=(Button) view.findViewById(R.id.bottom_sheet_submit);






        recyclerView=(RecyclerView) view.findViewById(R.id.donorsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();




        seekerName=new ArrayList<>();
        seekerEmail=new ArrayList<>();
        seekerPhone=new ArrayList<>();
        seekerAge=new ArrayList<>();
        seekerAdd=new ArrayList<>();
        seekerGender=new ArrayList<>();
        seekerreqUnits=new ArrayList<>();
        seekerreqBlood=new ArrayList<>();
        seekerImage=new ArrayList<>();
        seekerBlood=new ArrayList<>();





        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });






        fab=(FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
        });





        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bg=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    String uid=snapshot.getKey();
                    if(uid.equals("stock"))
                        continue;
                    SeekerName=snapshot.child("registerName").getValue(String.class);
                    SeekerEmail=snapshot.child("registerEmail").getValue(String.class);
                    SeekerPhone=snapshot.child("registerPhone").getValue(String.class);
                   // String donorCheck=snapshot.child("checkDonor").getValue(String.class);

                    SeekerBlood=snapshot.child("registerBloodGroup").getValue(String.class);

                    SeekerAdd=snapshot.child("registerAdd1").getValue(String.class);

                    SeekerImage=snapshot.child("registerImage").getValue(String.class);

                    SeekerAge=snapshot.child("registerAge").getValue(String.class);
                    SeekerGender=snapshot.child("registerGender").getValue(String.class);
                    SeekerReqBlood=snapshot.child("registerBlood").getValue(String.class);
                    SeekerReqUnits=snapshot.child("registerUnits").getValue(String.class);


                    Log.d("DEBUG",SeekerReqBlood);
                    Log.d("DEBUG",SeekerReqUnits
                    );



                    if(SeekerReqBlood.equals("0"))
                    {
                        continue;


                    }else
                    {
                        seekerName.add(SeekerName);
                        seekerEmail.add(SeekerEmail);
                        seekerPhone.add(SeekerPhone);
                        seekerAdd.add(SeekerAdd);
                        seekerAge.add(SeekerAge);
                        seekerImage.add(SeekerImage);
                        seekerGender.add(SeekerGender);
                        seekerBlood.add(SeekerBlood);

                        seekerreqBlood.add(SeekerReqBlood);
                        seekerreqUnits.add(SeekerReqUnits);


                    }





                }

                RequestAdapter adapter=new RequestAdapter(getActivity(),seekerName,seekerEmail,seekerPhone,seekerAdd,seekerAge
                 ,seekerGender,seekerImage,seekerBlood,seekerreqUnits,seekerreqBlood);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


















        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(bg.equals("Blood Group")||units.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(),"Enter Correct Request Parameters",Toast.LENGTH_SHORT).show();
                }else
                {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        String uid=snapshot.getKey();
                        if(uid.equals(firebaseAuth.getCurrentUser().getUid()))
                        {
                           databaseReference.child(uid).child("registerBlood").setValue(bg);

                            databaseReference.child(uid).child("registerUnits").setValue(units.getText().toString().trim());




                            Toast.makeText(getActivity(),"Request Submitted",Toast.LENGTH_SHORT).show();

                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }}
        });









       // RequestAdapter adapter=new RequestAdapter(getActivity(),)






        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.bloodgroups,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);

        return view;
    }
}
