package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;

import static java.security.AccessController.getContext;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerTilte,registerMessage;
    private EditText registerName,registerEmail,registerPassword,registerPhone,registerAge,registerAdd1;
    private ImageView registerImage;
    private Spinner registerBloodGroup,registerGender;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private  String bloodGroup,gender;
    private CheckBox donorButton;
    private String Donor="false",img="https://firebasestorage.googleapis.com/v0/b/bloodbankproject-7a027.appspot.com/o/default_user1.png?alt=media&token=6a290860-2c88-4a7e-a644-a92b418d90a4";
    private static final int GALLERY_REQUEST=1;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are You Sure You Want To Exit");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RegisterActivity.super.onBackPressed();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerName=(EditText)findViewById(R.id.registername);
        registerEmail=(EditText)findViewById(R.id.registeremail);
        registerPassword=(EditText)findViewById(R.id.registerpassword);
        registerPhone=(EditText)findViewById(R.id.registerphone);
        registerAge=(EditText)findViewById(R.id.registerage);
        registerAdd1=(EditText)findViewById(R.id.registeradd1);

        registerBloodGroup=(Spinner)findViewById(R.id.registerbloodgroup);
        registerGender=(Spinner)findViewById(R.id.registergender);
        donorButton=(CheckBox) findViewById(R.id.donorbutton);



        registerMessage=(TextView)findViewById(R.id.registermessage);
        registerButton=(Button)findViewById(R.id.registerbutton);


        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing Up....");

        donorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donorButton.isChecked())
                    Donor="true";
            }
        });




        registerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(RegisterActivity.this,R.array.bloodgroups,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerBloodGroup.setAdapter(adapter);

        registerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(RegisterActivity.this,R.array.gender,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerGender.setAdapter(adapter1);

        registerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



     /*   registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
*/





        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                progressDialog.show();

                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                if(!validate())
                {
                    Toast.makeText(RegisterActivity.this,"Fill In Your Details",Toast.LENGTH_LONG).show();
                }

                else
                {

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                sendUserData();

                               Toast.makeText(RegisterActivity.this, "Registeration Successfull", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, HomePage.class));
                                progressDialog.dismiss();
                                finish();


                            } else {

                              Toast.makeText(RegisterActivity.this, "Registeration Failed", Toast.LENGTH_LONG).show();


                            }

                        }
                    });

                }
            }
        });









    }

    private void sendUserData() {




        /*StorageReference filepath=storageReference.child("Profile_Pic").child(imageUri.getLastPathSegment());
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                downloadImage = taskSnapshot.getDownloadUrl();


            }
        });*/


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());

        UserProfile userProfile=new UserProfile(registerEmail.getText().toString().trim(),
                registerName.getText().toString().trim(),
                registerPhone.getText().toString().trim(),
                bloodGroup,
                gender,
                registerAge.getText().toString().trim(),
                registerAdd1.getText().toString().trim(),
                Donor.trim()
                ,img,"0","0"
                );

        myRef.setValue(userProfile);


    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK)
       {


           imageUri=data.getData();

           CropImage.activity(imageUri)
                   .setGuidelines(CropImageView.Guidelines.ON)
                   .setAspectRatio(1,1)
                   .start(this);

       }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                registerImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

   }


   */

    public boolean validate(){

        boolean valid=true;


        if(registerEmail.getText().toString().trim().isEmpty()){
            registerEmail.setError("Please enter Your Email");
            valid=false;

        }
        if(registerName.getText().toString().trim().isEmpty())
        {
            registerName.setError("Please Enter Your Name");
            valid=false;
        }
         if(registerPhone.getText().toString().trim().isEmpty()||registerPhone.getText().toString().trim().length()!=10) {
             registerPhone.setError("Please Enter Valid Phone No");
             valid = false;
         }
        if(bloodGroup.equals("Blood Group")) {
             Toast.makeText(RegisterActivity.this,"Select Correct Blood Group",Toast.LENGTH_SHORT).show();
             valid = false;
         }
        if(gender.equals("Gender")) {
            Toast.makeText(RegisterActivity.this,"Select Gender",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(registerAge.getText().toString().trim().isEmpty())
        {
            registerAge.setError("Please Enter Your Age");
            valid=false;
        }
        if(registerPassword.getText().toString().trim().isEmpty())
        {
            registerPassword.setError("Please Enter Your Password ( Min 6 characters)");
        }
        if(registerAdd1.getText().toString().trim().isEmpty())
        {
            registerAdd1.setError("Please Enter Your Address");
            valid=false;
        }


        return valid;


    }
}
