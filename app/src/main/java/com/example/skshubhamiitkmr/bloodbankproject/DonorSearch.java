package com.example.skshubhamiitkmr.bloodbankproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class DonorSearch extends AppCompatActivity {

    TextView donorBloodSearch;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference myref;
    FirebaseUser firebaseUser;


    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> phoneList;
    ArrayList<String> checkdonorList;
    ArrayList<String> mapList;
    ArrayList<String> ageList;
    ArrayList<String> genderList;
    ArrayList<String> bloodgroupList;
    ArrayList<String> imageList;

    SearchAdapter searchAdapter;


    String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_search);







        donorBloodSearch=(TextView) findViewById(R.id.donorbloodtext);

        Bundle bundle=getIntent().getExtras();
        message=bundle.getString("message");
        donorBloodSearch.setText(message);



        recyclerView=(RecyclerView) findViewById(R.id.donorsrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));



        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myref=database.getReference();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        nameList=new ArrayList<>();
        emailList=new ArrayList<>();
        phoneList=new ArrayList<>();
        checkdonorList=new ArrayList<>();
        mapList=new ArrayList<>();
        ageList=new ArrayList<>();
        genderList=new ArrayList<>();
        bloodgroupList=new ArrayList<>();
        imageList=new ArrayList<>();








        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    String uid=snapshot.getKey();
                    if(uid.equals("stock")||uid.equals("Request")||uid.equals(firebaseAuth.getCurrentUser().getUid()))
                        continue;
                    String donorName=snapshot.child("registerName").getValue(String.class);
                    String donorEmail=snapshot.child("registerEmail").getValue(String.class);
                    String donorPhone=snapshot.child("registerPhone").getValue(String.class);
                    String donorCheck=snapshot.child("checkDonor").getValue(String.class);
                    String donorBloodGroup=snapshot.child("registerBloodGroup").getValue(String.class);

                    String donorAdd=snapshot.child("registerAdd1").getValue(String.class);

                    String age=snapshot.child("registerAge").getValue(String.class);
                    String gender=snapshot.child("registerGender").getValue(String.class);
                    String bloodgroup=snapshot.child("registerBloodGroup").getValue(String.class);
                    String donorImage=snapshot.child("registerImage").getValue(String.class);






                    if(donorBloodGroup.equals(message)&&donorCheck.equals("true"))
                    {
                        nameList.add(donorName);
                        phoneList.add(donorPhone);
                        emailList.add(donorEmail);
                        mapList.add(donorAdd);

                        ageList.add(age);
                        genderList.add(gender);
                        bloodgroupList.add(bloodgroup);
                        imageList.add(donorImage);
                    }



                }

                searchAdapter=new SearchAdapter(DonorSearch.this,nameList,emailList,phoneList,checkdonorList,mapList,
                        ageList,genderList,bloodgroupList,imageList);
                if(searchAdapter.getItemCount()==0){
                    Toast.makeText(DonorSearch.this,"No Donors Found",Toast.LENGTH_SHORT).show();
                }
                else {
                    recyclerView.setAdapter(searchAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }





}
