package com.essensol.techmeq.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.essensol.techmeq.R;

public class Toolbar extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    public android.support.v7.widget.Toolbar toolbar;

    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar=findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





        toggle.setDrawerIndicatorEnabled(true);


        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


//        toggle.setHomeAsUpIndicator(R.drawable.add);



        getSupportActionBar().setDisplayHomeAsUpEnabled(false);






        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                final String appPackageName = getPackageName();

                int id = item.getItemId();

                switch(id) {
                    case R.id.home:
                        Intent intent = new Intent(Toolbar.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.voucher:
                        intent = new Intent(Toolbar.this, ExpenceVoucher.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.sales:
                        intent = new Intent(Toolbar.this, SalesActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.reports:
                        intent = new Intent(Toolbar.this, Reports.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.logout:
                        intent = new Intent(Toolbar.this, Login.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();

                    default:
                }



                return false;
            }
        });





    }



}
