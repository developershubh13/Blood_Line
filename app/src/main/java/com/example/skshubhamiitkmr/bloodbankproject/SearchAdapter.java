package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skshubhamiitkmr on 02-04-2018.
 */




public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SerachViewHolder>{

    Context context;

    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> phoneList;
    ArrayList<String> checkdonorList;
    ArrayList<String> ageList;
    ArrayList<String> genderList;
    ArrayList<String> bloodgroupList;
    ArrayList<String> mapList;
    ArrayList<String> imageList;

    class SerachViewHolder extends RecyclerView.ViewHolder{

        private TextView listName,listEmail,listPhone,listMap,listAge,listGender,listBloodGroup;

        private CircleImageView listImage;



        public SerachViewHolder(View itemView) {
            super(itemView);

            listName=(TextView) itemView.findViewById(R.id.listname);
            listEmail=(TextView) itemView.findViewById(R.id.listemail);
            listPhone=(TextView) itemView.findViewById(R.id.listphone);
            listMap=(TextView) itemView.findViewById(R.id.listmap);


            listAge=(TextView) itemView.findViewById(R.id.listage);
            listGender=(TextView) itemView.findViewById(R.id.listgender);
            listBloodGroup=(TextView) itemView.findViewById(R.id.listbloodgroup);

            listImage=(CircleImageView) itemView.findViewById(R.id.listimage);



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





                    phone.setText(listPhone.getText().toString().trim());
                    address.setText("Address: "+listMap.getText().toString().trim());
                    name.setText(listName.getText().toString().trim());
                    email.setText(listEmail.getText().toString().trim());
                    gender.setText(listGender.getText().toString().trim());
                    age.setText(listAge.getText().toString().trim());
                    image.setImageDrawable(listImage.getDrawable());




                    map.setOnClickListener(new View.OnClickListener() {
                        @Override

                        public void onClick(View v) {

                            String str=listMap.getText().toString().trim();
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
                            intent.putExtra(Intent.EXTRA_EMAIL,new String[] {listEmail.getText().toString().trim()});
                            intent.setType("message/rfc822");
                            v.getContext().startActivity(Intent.createChooser(intent,"Choose An Email Client"));
                        }
                    });

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            Intent intent =new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+listPhone.getText().toString()));
                            v.getContext().startActivity(intent);


                        }
                    });








                }
            });

        }
    }
public SearchAdapter()
{

}

    public SearchAdapter(Context context, ArrayList<String> nameList, ArrayList<String> emailList, ArrayList<String> phoneList,
                         ArrayList<String> checkdonorList,ArrayList<String> mapList,ArrayList<String> ageList
                           ,ArrayList<String> genderList,ArrayList<String> bloodgroupList,ArrayList<String> imageList) {
        this.context = context;
        this.nameList = nameList;
        this.emailList = emailList;
        this.phoneList = phoneList;

        this.mapList=mapList;

        this.ageList=ageList;
        this.genderList=genderList;
        this.bloodgroupList=bloodgroupList;
        this.imageList=imageList;

    }

    @Override
    public SearchAdapter.SerachViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.serach_list,parent,false);

        return  new SearchAdapter.SerachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SerachViewHolder holder, int position) {

        holder.listName.setText(nameList.get(position));
       holder.listEmail.setText(emailList.get(position));
       holder.listPhone.setText(phoneList.get(position));
       holder.listMap.setText(mapList.get(position));

       holder.listAge.setText(ageList.get(position));
        holder.listGender.setText(genderList.get(position));
        holder.listBloodGroup.setText(bloodgroupList.get(position));
//        holder.listImage.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/bloodbankproject-7a027.appspot.com/o/default_user1.png?alt=media&token=6a290860-2c88-4a7e-a644-a92b418d90a4"));


         GlideApp.
                with(context)
                .load(imageList.get(position))
                 .transition(DrawableTransitionOptions.withCrossFade(1000))
                 .centerCrop()
                 .into(holder.listImage);


        }


    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
