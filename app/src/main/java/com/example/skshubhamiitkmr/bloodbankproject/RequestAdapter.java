package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skshubhamiitkmr on 20-04-2018.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>{

    private Context context;
    private ArrayList<String> requestName,requestEmail,requestPhone,requestAdd,requestAge,requestGender,requestImage,requestSeekerUnits,requestBlood
            ,requestSeekerBlood;

    public RequestAdapter() {
    }


    public RequestAdapter(Context context
            ,ArrayList<String> requestName,ArrayList<String> requestEmail,ArrayList<String> requestPhone,
                          ArrayList<String> requestAdd,ArrayList<String> requestAge,ArrayList<String> requestGender,
                          ArrayList<String> requestImage,ArrayList<String> requestBlood,
                          ArrayList<String> requestSeekerUnits,
                          ArrayList<String> requestSeekerBlood)


    {
        this.context=context;
        this.requestName=requestName;
        this.requestEmail=requestEmail;
        this.requestPhone=requestPhone;
        this.requestAdd=requestAdd;
        this.requestAge=requestAge;
        this.requestGender=requestGender;
        this.requestImage=requestImage;

        this.requestBlood=requestBlood;
        this.requestSeekerUnits=requestSeekerUnits;
        this.requestSeekerBlood=requestSeekerBlood;


    }







    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.request_list,parent,false);

        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {


        holder.Name.setText(requestName.get(position));
        holder.Email.setText(requestEmail.get(position));
        holder.Phone.setText(requestPhone.get(position));
        holder.Add.setText(requestAdd.get(position));
        holder.Age.setText(requestAge.get(position));
        holder.Gender.setText(requestGender.get(position));
        holder.SeekerUnits.setText(requestSeekerUnits.get(position));
        holder.SeekerBlood.setText(requestSeekerBlood.get(position));
        holder.Blood.setText(requestBlood.get(position));

        GlideApp
                .with(context)
                .load(requestImage.get(position))
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.Image);



    }

    @Override
    public int getItemCount()
    {
        return requestName.size();
    }





    class RequestViewHolder extends RecyclerView.ViewHolder{


        private TextView Name,Email,Phone,Add,Age,Gender,SeekerUnits,SeekerBlood,Blood;
        private CircleImageView Image;




        public RequestViewHolder(View itemView) {
            super(itemView);

            Name=(TextView) itemView.findViewById(R.id.seeker_name);
            Email=(TextView) itemView.findViewById(R.id.seeker_email);
            Phone=(TextView) itemView.findViewById(R.id.seeker_phone);
            Add=(TextView) itemView.findViewById(R.id.seeker_add);
            Age=(TextView) itemView.findViewById(R.id.seeker_age);
            Gender=(TextView) itemView.findViewById(R.id.seeker_gender);
            SeekerUnits=(TextView) itemView.findViewById(R.id.seeker_req_units);
            SeekerBlood=(TextView) itemView.findViewById(R.id.seeker_req_blood);
            Blood=(TextView) itemView.findViewById(R.id.seeker_blood);
           Image=(CircleImageView) itemView.findViewById(R.id.seeker_image);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                    View mView=View.inflate(context,R.layout.donors_dialog, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog=mBuilder.create();
                    dialog.show();
                    //  dialog.getWindow().setLayout(900sp,1000sp);


                    TextView name=(TextView) mView.findViewById(R.id.dialog_name);
                    TextView phone=(TextView) mView.findViewById(R.id.dialog_phone);
                    TextView email=(TextView) mView.findViewById(R.id.dialog_email);
                    TextView address=(TextView) mView.findViewById(R.id.dialog_add);
                    TextView gender=(TextView) mView.findViewById(R.id.dialog_gender);
                    TextView age=(TextView) mView.findViewById(R.id.dialog_age);

                    ImageView map=(ImageView) mView.findViewById(R.id.dialog_map);
                    ImageView call=(ImageView) mView.findViewById(R.id.dialog_call);
                    ImageView gmail=(ImageView) mView.findViewById(R.id.dialog_gmail);
                    CircleImageView image=(CircleImageView) mView.findViewById(R.id.dialog_image);





                    phone.setText(Phone.getText().toString().trim());
                    address.setText("Address: "+Add.getText().toString().trim());
                    name.setText(Name.getText().toString().trim());
                    email.setText(Email.getText().toString().trim());
                    gender.setText(Gender.getText().toString().trim());
                    age.setText(Age.getText().toString().trim());
                    image.setImageDrawable(Image.getDrawable());




                    map.setOnClickListener(new View.OnClickListener() {
                        @Override

                        public void onClick(View v) {

                            String str=Add.getText().toString().trim();
                            str.replace(' ','+');


                            String add="google.navigation:q=";
                            Toast.makeText(v.getContext(),add+str,Toast.LENGTH_SHORT).show();



                            Uri mapUri=Uri.parse(add+str);
                            Intent intent =new Intent(Intent.ACTION_VIEW,mapUri);
                            intent.setPackage("com.google.android.apps.maps");
                            v.getContext().startActivity(intent);


                        }
                    });



                    gmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            Intent intent =new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_EMAIL,new String[] {Email.getText().toString().trim()});
                            intent.setType("message/rfc822");
                            v.getContext().startActivity(Intent.createChooser(intent,"Choose An Email Client"));
                        }
                    });

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            Intent intent =new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+Phone.getText().toString()));
                            v.getContext().startActivity(intent);


                        }
                    });








                }
            });

        }
    }


}