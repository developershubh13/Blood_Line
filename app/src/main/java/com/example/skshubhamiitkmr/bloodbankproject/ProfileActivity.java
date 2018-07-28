package com.example.skshubhamiitkmr.bloodbankproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {





    private FirebaseAuth firebaseAuth;

    FirebaseDatabase database;




    DatabaseReference databaseReference;
    private TextView profileName,profileEmail,profilePhone,profileBloodGroup,profileAge,profileGender,profileAdd1;
    private CircleImageView profileImage;
    // private ImageView profileImage;
    private Button edit;
    private String profimage;

    View view;
    private ValueEventListener mListener;





    public ProfileActivity()
    {

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(firebaseAuth.getCurrentUser().getUid());



        profileName=(TextView)findViewById(R.id.profilename);
        profileEmail=(TextView)findViewById(R.id.profileemail);
        profilePhone=(TextView)findViewById(R.id.profilephone);
        profileAge=(TextView)findViewById(R.id.profileage);
        profileBloodGroup=(TextView)findViewById(R.id.profilebloodgroup);
        profileGender=(TextView)findViewById(R.id.profilegender);
        profileAdd1=(TextView)findViewById(R.id.profileaddress);
        edit=(Button)findViewById(R.id.edit);
        profileImage=(CircleImageView)findViewById(R.id.profileimage);
        //  profileImage=(ImageView) view.findViewById(R.id.profileimage);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);


                profileName.setText(userProfile.getRegisterName());
                profileEmail.setText(userProfile.getRegisterEmail());
                profilePhone.setText(userProfile.getRegisterPhone());
                profileAge.setText(userProfile.getRegisterAge());
                profileBloodGroup.setText(userProfile.getRegisterBloodGroup());
                profileGender.setText(userProfile.getRegisterGender());
                profileAdd1.setText(userProfile.getRegisterAdd1());

                profimage=userProfile.getRegisterImage();
                GlideApp.with(ProfileActivity.this)
                        .load(profimage)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .apply(RequestOptions.circleCropTransform())
                        .centerCrop()
                        .into(profileImage);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(view.getContext(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this,EditActivity.class));
            }
        });













    }
}
