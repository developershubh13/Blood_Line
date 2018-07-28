package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText loginName,loginPassword;

    private TextView loginMessage;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder alertBuider=new AlertDialog.Builder(this);
        alertBuider.setMessage("Are You Sure You Want To Exit");

        alertBuider.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });alertBuider.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.super.onBackPressed();

            }
        });

        alertBuider.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();


        loginName=(EditText)findViewById(R.id.loginname);
        loginPassword=(EditText)findViewById(R.id.loginpassword);

        loginMessage=(TextView)findViewById(R.id.loginmessage);
        loginButton=(Button)findViewById(R.id.loginbutton);

        progressDialog=new ProgressDialog(this);



        loginMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });



        if(user!=null){

            startActivity(new Intent(MainActivity.this,HomePage.class));
            finish();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=loginName.getText().toString().trim();
                String password=loginPassword.getText().toString().trim();


                 if(email.isEmpty()||password.isEmpty())
                 {
                     Toast.makeText(MainActivity.this,"Fill In Your Details",Toast.LENGTH_LONG).show();
                 }
                else {
                     progressDialog.setMessage("Signing In...");
                     progressDialog.show();

                     progressDialog.setCanceledOnTouchOutside(false);
                     progressDialog.setCancelable(false);


                     firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             if (task.isSuccessful()) {

                                 progressDialog.dismiss();
                                 Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                                 startActivity(new Intent(MainActivity.this, HomePage.class));
                                 finish();

                             } else {
                                 progressDialog.dismiss();
                                 Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                             }


                         }
                     });


                 }



            }
        });





    }




}
