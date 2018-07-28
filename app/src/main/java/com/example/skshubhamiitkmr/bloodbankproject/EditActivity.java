package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    private EditText editName,editEmail,editAge,editPhone,editAdd,editPassword;
    private Spinner editGender,editBloodGroup;
    private CheckBox editCheckDonor;
    private Button update,upload;
    private String bloodgroup,gender;
    private String Donor="false",downloadString=null,editblood,editunits;
    private ImageView editImage;
    private String fetchImage;



    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private StorageReference storageRefrence;
    private Uri filepath,downloadUri;
    private ProgressDialog progressDialog;
    private static final int PICK_IMAGE=1;

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Data Not Saved");
        builder.setMessage("Are You Sure You Want To Exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
                EditActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //EditActivity.super.onBackPressed();
            }
        });
        builder.show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editName=(EditText)findViewById(R.id.edit_name);
        editAge=(EditText)findViewById(R.id.edit_age);
        editPhone=(EditText)findViewById(R.id.edit_phone);
        editAdd=(EditText)findViewById(R.id.edit_add);
        editGender=(Spinner)findViewById(R.id.edit_gender);
        editBloodGroup=(Spinner)findViewById(R.id.edit_bloodgroup);
        editCheckDonor=(CheckBox) findViewById(R.id.edit_checkdonor);
        update=(Button)findViewById(R.id.update);
        upload=(Button) findViewById(R.id.image_button);
        editImage=(ImageView)findViewById(R.id.edit_image);
        editEmail=(EditText)findViewById(R.id.edit_email);


        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(firebaseAuth.getCurrentUser().getUid());
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        progressDialog=new ProgressDialog(this);
        storageRefrence= FirebaseStorage.getInstance().getReference();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);


              }
        });





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filepath!=null)
                {
                    progressDialog.setTitle("Uploading..");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);





                    StorageReference myRef=storageRefrence.child(firebaseAuth.getCurrentUser().getUid());

                    myRef.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                                    progressDialog.dismiss();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                    downloadUri=taskSnapshot.getDownloadUrl();
                                    downloadString=downloadUri.toString();



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            Toast.makeText(getApplicationContext(),"Uploading Failed.",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(((int)progress)+" % Uploaded");

                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                        }
                    });

                }else
                {

                    Toast.makeText(getApplicationContext(),"No File Selected",Toast.LENGTH_SHORT).show();

                }



            }
        });






        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);


                editEmail.setText(userProfile.getRegisterEmail());
                editName.setText(userProfile.getRegisterName());
                editAge.setText(userProfile.getRegisterAge());
                editPhone.setText(userProfile.getRegisterPhone());
                //editBloodGroup.setSelection(1);
                //editGender.setText(userProfile.getRegisterGender());
                editAdd.setText(userProfile.getRegisterAdd1());
                //editCheckDonor.setText(userProfile.getCheckDonor());
                fetchImage=userProfile.getRegisterImage();

                editblood=userProfile.getRegisterBlood();
                editunits=userProfile.getRegisterUnits();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(view.getContext(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });


        editCheckDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editCheckDonor.isChecked())
                    Donor="true";
            }
        });




        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(EditActivity.this,R.array.bloodgroups,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editBloodGroup.setAdapter(adapter);

        editBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodgroup=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(EditActivity.this,R.array.gender,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGender.setAdapter(adapter1);

        editGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bloodgroup.equals("Blood Group")) {
                    Toast.makeText(EditActivity.this,"Select Correct Blood Group",Toast.LENGTH_SHORT).show();

                }
                 else if(gender.equals("Gender")) {
                    Toast.makeText(EditActivity.this,"Select Gender",Toast.LENGTH_SHORT).show();

                }else {


                    if(downloadString!=null)
                    {
                        fetchImage=downloadString;
                    }

                    if(validate()) {
                        UserProfile userProfile = new UserProfile(editEmail.getText().toString().trim(),
                                editName.getText().toString().trim(),
                                editPhone.getText().toString().trim(),
                                bloodgroup,
                                gender,
                                editAge.getText().toString().trim(),
                                editAdd.getText().toString().trim(),
                                Donor, fetchImage,editblood,editunits

                        );

                        databaseReference.setValue(userProfile);

                        // firebaseUser.updatePassword(editPassword.getText().toString().trim());


                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();


                        finish();
                    }
                }
            }

        });







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            filepath=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                editImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    public boolean validate(){

        boolean valid=true;

        if(editName.getText().toString().trim().isEmpty())
        {
            editName.setError("Please Enter Your Name");
            valid=false;
        }
        if(editPhone.getText().toString().trim().isEmpty()||editPhone.getText().toString().trim().length()!=10) {
            editPhone.setError("Please Enter Valid Phone No");
            valid = false;
        }

        if(editAge.getText().toString().trim().isEmpty())
        {
            editAge.setError("Please Enter Your Age");
            valid=false;
        }

        if(editAdd.getText().toString().trim().isEmpty())
        {
            editAdd.setError("Please Enter Your Address");
            valid=false;
        }


        return valid;


    }
}
