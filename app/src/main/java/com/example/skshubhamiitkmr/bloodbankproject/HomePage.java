package com.example.skshubhamiitkmr.bloodbankproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {


  private TabLayout tabLayout;
  private AppBarLayout appBarLayout;
  private ViewPager viewPager;
  private FirebaseAuth firebaseAuth;






    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are You Sure you Want To Exit");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.profile:
                profile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {

  firebaseAuth.signOut();
   this.finish();
   startActivity(new Intent(HomePage.this,MainActivity.class));


    }


    private void profile(){
        startActivity(new Intent(HomePage.this,ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout=(TabLayout) findViewById(R.id.tablayout);
        viewPager=(ViewPager) findViewById(R.id.viewpager);

        firebaseAuth=FirebaseAuth.getInstance();

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new HomeFragment(),"Home");
        adapter.AddFragment(new StockFragment(),"Stock");
        adapter.AddFragment(new SearchFragment(),"Search");
        adapter.AddFragment(new RequestFragment(),"Request");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);














    }


}
