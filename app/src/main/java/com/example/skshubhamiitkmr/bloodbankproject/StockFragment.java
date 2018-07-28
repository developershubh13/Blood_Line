package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skshubhamiitkmr on 14-03-2018.
 */

public class StockFragment extends Fragment {


    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference refrence =database.getReference();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    ArrayList<Inventory> inventoryList;





    public int sa1=0,sa2=0,sb1=0,sb2=0,so1=0,so2=0,sab1=0,sab2=0;
    ListView listView;

    View view;

    public StockFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_stock_fragment,container,false);




        listView=(ListView) view.findViewById(R.id.listview);

        inventoryList=new ArrayList<>();


        refrence.addListenerForSingleValueEvent(new ValueEventListener() {




            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                  int a1=0,a2=0,b1=0,b2=0,o1=0,o2=0,ab1=0,ab2=0;


                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    String uid=snapshot.getKey();
                    String donorCheck=snapshot.child("checkDonor").getValue(String.class);
                    String donorBloodGroup=snapshot.child("registerBloodGroup").getValue(String.class);

                    if(uid.equals("stock"))
                    {

                         sa1=snapshot.child("A+").getValue(Integer.class);
                         sa2=snapshot.child("A-").getValue(Integer.class);
                         sb1=snapshot.child("B+").getValue(Integer.class);
                         sb2=snapshot.child("B-").getValue(Integer.class);
                         so1=snapshot.child("O+").getValue(Integer.class);
                         so2=snapshot.child("O-").getValue(Integer.class);
                         sab1=snapshot.child("AB+").getValue(Integer.class);
                         sab2=snapshot.child("AB-").getValue(Integer.class);



                    }
                    else
                    {

                    if(donorCheck.equals("true")){

                        if(donorBloodGroup.equals("A+"))
                            a1=a1+1;
                       else if(donorBloodGroup.equals("A-"))
                            a2=a2+1;
                        else if(donorBloodGroup.equals("B+"))
                            b1=b1+1;
                        else if(donorBloodGroup.equals("B-"))
                            b2=b2+1;
                        else if(donorBloodGroup.equals("O+"))
                            o1=o1+1;
                        else if(donorBloodGroup.equals("O-"))
                            o2=o2+1;
                        else if(donorBloodGroup.equals("AB+"))
                            ab1=ab1+1;
                        else if(donorBloodGroup.equals("AB-"))
                            ab2=ab2+1;
                    }
                    }

                }


                Inventory inv1=new Inventory("A+",sa1+" ",a1+" ");
                Inventory inv2=new Inventory("A-",sa2+" ",a2+" ");
                Inventory inv3=new Inventory("B+",sb1+" ",b1+" ");
                Inventory inv4=new Inventory("B-",sb2+" ",b2+" ");
                Inventory inv5=new Inventory("O+",so1+" ",o1+" ");
                Inventory inv6=new Inventory("O-",so2+" ",o2+" ");
                Inventory inv7=new Inventory("AB+",sab1+" ",ab1+" ");
                Inventory inv8=new Inventory("AB-",sab2+" ",ab2+" ");


                inventoryList.add(inv1);
                inventoryList.add(inv2);
                inventoryList.add(inv3);
                inventoryList.add(inv4);
                inventoryList.add(inv5);
                inventoryList.add(inv6);
                inventoryList.add(inv7);
                inventoryList.add(inv8);







                InventoryAdapter adapter=new InventoryAdapter(getActivity(),R.layout.list_adapter,inventoryList);
                listView.setAdapter(adapter);





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;


    }

}
