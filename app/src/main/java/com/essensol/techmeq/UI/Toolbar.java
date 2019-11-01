package com.essensol.techmeq.UI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.widget.Toast;

import com.essensol.techmeq.DialogFragments.AddProduct_fragment;
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

    LinearLayout addItem;

    ImageView bluetooth;

    private PService mPService = null;




    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ServiceConnection", "onServiceDisconnected() called");
            mPService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPService = PService.Stub.asInterface(service);
            Log.e("ServiceConnection", "onServiceDisconnected() called"+mPService);
        }
    }

//    private void connection() {
//        conn = new MainActivity.PrinterServiceConnection();
//        Intent intent = new Intent(this, PrinterPrintService.class);
//        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar=findViewById(R.id.toolbar);

        addItem=findViewById(R.id.user);

        bluetooth=findViewById(R.id.bluetooth);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm =getSupportFragmentManager();


                final AddProduct_fragment dialog= new AddProduct_fragment();

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
                        intent = new Intent(Toolbar.this, Reports.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;

//                    case R.id.reports:
//                        intent = new Intent(Toolbar.this, SalesActivity.class);
//                        startActivity(intent);
//                        drawerLayout.closeDrawers();
//                        break;

                    case R.id.taxreport:
                        intent = new Intent(Toolbar.this, TaxReport.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.logout:
                        intent = new Intent(Toolbar.this, Login.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;




                    default:
                }



                return false;
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            Toast.makeText(Toolbar.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void openPortDialogueClicked() {

        Log.e("ServiceConnection", "openPortDialogueClicked() called"+mPService);

        if (mPService == null) {
            Toast.makeText(this, "Print Service is not start, please check it", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("Test", "openPortConfigurationDialog ");
        Intent intent = new Intent(this, PrinterConnectDialog.class);
        boolean[] state = getConnectState();
        intent.putExtra(CONNECT_STATUS, state);
        this.startActivity(intent);
    }


    public boolean[] getConnectState() {
        boolean[] state = new boolean[PrinterPrintService.MAX_PRINTER_CNT];
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            state[i] = false;
        }
        for (int i = 0; i < PrinterPrintService.MAX_PRINTER_CNT; i++) {
            try {
                if (mPService.getPrinterConnectStatus(i) == PrinterDevice.STATE_CONNECTED) {
                    state[i] = true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return state;
    }



}
