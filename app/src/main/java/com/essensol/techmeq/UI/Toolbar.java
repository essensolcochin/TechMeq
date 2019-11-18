package com.essensol.techmeq.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.DialogFragments.AddCategoryFragment;
import com.essensol.techmeq.DialogFragments.AddProduct_fragment;
import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.POS_Printer_Util.PrinterConnectDialog;
import com.essensol.techmeq.R;
import com.printer.aidl.PService;
import com.printer.io.PrinterDevice;
import com.printer.service.PrinterPrintService;

import static com.essensol.techmeq.UI.MainActivity.CONNECT_STATUS;

public class Toolbar extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    public android.support.v7.widget.Toolbar toolbar;

    private NavigationView navigationView;

    LinearLayout addItem,add;

    ImageView bluetooth,search;

    private TextView companyname,headicon;

    private PService mPService = null;


    public android.support.v7.widget.Toolbar getToolbar()
    {
        return toolbar;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar=findViewById(R.id.toolbar);



        addItem=findViewById(R.id.user);

        bluetooth=findViewById(R.id.bluetooth);

        companyname=findViewById(R.id.companyname);


        SharedPreferences sp =getSharedPreferences("LogDetails",MODE_PRIVATE);

        companyname.setText(sp.getString("compname",""));


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#4d606f")));

//        Menu menu = navigationView.getMenu();
//
//        MenuItem tools= menu.findItem(R.id.sales);
//
//        tools.getTitle().


        View headerView = navigationView.getHeaderView(0);

        TextView uname = (TextView) headerView.findViewById(R.id.UserName);

        TextView cpName = (TextView) headerView.findViewById(R.id.CopmanyName);
        headicon = (TextView) headerView.findViewById(R.id.headicon);


        uname.setText(sp.getString("uname",""));
        cpName.setText(sp.getString("compname",""));
        String name =sp.getString("compname","");
        headicon.setText(name.substring(0,1));

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm =getSupportFragmentManager();


                final AddProduct_fragment dialog= new AddProduct_fragment();

                dialog.show(fm,"TAG");

            }
        });


                add = findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm =getSupportFragmentManager();


                final AddCategoryFragment dialog= new AddCategoryFragment();

                dialog.show(fm,"TAG");
            }
        });







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

                int id = item.getItemId();

                switch(id) {
                    case R.id.newInvoice:
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(Toolbar.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                    case R.id.voucher:
                        drawerLayout.closeDrawers();
                        intent = new Intent(Toolbar.this, ExpenceVoucher.class);
                        startActivity(intent);

                        break;

                    case R.id.sales:
                        drawerLayout.closeDrawers();
                        intent = new Intent(Toolbar.this, Reports.class);
                        startActivity(intent);

                        break;

//                    case R.id.reports:
//                        intent = new Intent(Toolbar.this, SalesActivity.class);
//                        startActivity(intent);
//                        drawerLayout.closeDrawers();
//                        break;

                    case R.id.taxreport:
                        drawerLayout.closeDrawers();
                        intent = new Intent(Toolbar.this, TaxReport.class);
                        startActivity(intent);

                        break;


                    case R.id.logout:
                        drawerLayout.closeDrawers();

                        SharedPreferences paidStatus=getSharedPreferences("LogDetails",MODE_PRIVATE);
                        SharedPreferences.Editor editor =paidStatus.edit();
                        editor.putBoolean("PaidSatus",false);
                        editor.putBoolean("isLogged",false);
                        editor.apply();
                        SharedPreferences logged=getSharedPreferences("PaidStatus",MODE_PRIVATE);
                        boolean log =logged.getBoolean("isLogged",false);

                        Log.e("isLogged","In Toolbar"+log);
                        intent = new Intent(Toolbar.this, Login.class);
                        startActivity(intent);
                        finish();

                        break;




                    default:
                }



                return false;
            }
        });





    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.add) {
//            Toast.makeText(Toolbar.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }





}
