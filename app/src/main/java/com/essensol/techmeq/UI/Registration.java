package com.essensol.techmeq.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.essensol.techmeq.Api.ApiConfig;
import com.essensol.techmeq.Api.ApiService;
import com.essensol.techmeq.Api.CheckUsernameResponse;
import com.essensol.techmeq.Api.RegisterResponse;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.CompanyMaster_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.Entity.CompanyMaster;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.lidroid.xutils.db.annotation.NotNull;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements InternetConnectivityListener {

    LinearLayout reg;
    TextInputEditText Cname, cperson, mob, email, address, trn, username, pword;
    TextInputLayout  cnameHint,cpersonHint,mobHint,emailhint,addressHint,trnHint,userHint,pwordHint;
    private static int CompId;

    private ApiService apiService;

    private ProgressDialog mDialog;

    private View view;

    InternetAvailabilityChecker mInternetAvailabilityChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg = findViewById(R.id.reg);

        InternetAvailabilityChecker.init(this);

        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);


        cnameHint = findViewById(R.id.cnameHint);
        cpersonHint = findViewById(R.id.cpersonHint);
        mobHint = findViewById(R.id.mobHint);
        addressHint = findViewById(R.id.addressHint);
        emailhint = findViewById(R.id.emailHint);
        userHint = findViewById(R.id.unameHint);
        pwordHint = findViewById(R.id.pwordHint);
        trnHint = findViewById(R.id.trnHint);
        view = findViewById(R.id.view);


        Cname = findViewById(R.id.Cpname);
        cperson = findViewById(R.id.cperson);
        mob = findViewById(R.id.mob);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        trn = findViewById(R.id.trn);
        username = findViewById(R.id.username);
        pword = findViewById(R.id.pword);
        mDialog=new ProgressDialog(this);
        mDialog.setTitle("Registration");
        mDialog.setMessage("Adding User Details...");

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!username.getText().toString().equalsIgnoreCase(""))
                {
                    CheckUsername(username.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                username.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


            }
        });


        apiService= ApiConfig.getClient().create(ApiService.class);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name=Cname.getText().toString();
               String Cperson=cperson.getText().toString();
               String Mob =mob.getText().toString();
               String Email =email.getText().toString();
               String Address=address.getText().toString();
               String Trn =trn.getText().toString();
               String Username=username.getText().toString();
               String Pword =pword.getText().toString();

                if(name.equalsIgnoreCase(""))
                {
                    cnameHint.setError("Field Required");
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }
               else if(Cperson.equalsIgnoreCase(""))
                {
                    cpersonHint.setError("Field Required");
                    cnameHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }
                else if(Mob.equalsIgnoreCase(""))
                {
                    mobHint.setError("Field Required");
                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }
                else if(Email.equalsIgnoreCase(""))
                {
                    emailhint.setError("Field Required");

                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }
                else if(Address.equalsIgnoreCase(""))
                {
                    addressHint.setError("Field Required");

                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }
               else if(Trn.equalsIgnoreCase(""))
                {
                    trnHint.setError("Field Required");

                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    userHint.setError(null);
                    pwordHint.setError(null);
                }

                else if(Username.equalsIgnoreCase(""))
                {
                    userHint.setError("Field Required");

                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    pwordHint.setError(null);
                }
                else if(Pword.equalsIgnoreCase(""))
                {
                    pwordHint.setError("Field Required");

                    cnameHint.setError(null);
                    cpersonHint.setError(null);
                    mobHint.setError(null);
                    emailhint.setError(null);
                    addressHint.setError(null);
                    trnHint.setError(null);
                    userHint.setError(null);
                }
                else {
//
//                    Log.e("Else part","");

                    OnlineRegistration();

          mDialog.show();

                }




            }
        });


    }

    private void AddComp() {
        CompanyMaster master = new CompanyMaster("Code", Cname.getText().toString().trim(), cperson.getText().toString().trim(),
                address.getText().toString().trim(), mob.getText().toString().trim(), mob.getText().toString().trim()
                , email.getText().toString().trim(), "Web", "TaxType", "Country", trn.getText().toString().trim(), true);


        try {
            String status = new RegAsync(OfflineDb.getInstance(this).companyMaster_dao()).execute(master).get();

            if (status.equalsIgnoreCase("Completed")) {

                Date curdate = Calendar.getInstance().getTime();
                SimpleDateFormat sf =new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

                Users users = new Users(CompId,0, username.getText().toString().trim(), pword.getText().toString().trim(),curdate,false);

                String stat = new RegAsync.AddUserAsync(OfflineDb.getInstance(this).user_dao()).execute(users).get();

                if (stat.equalsIgnoreCase("Completed")) {

                    mDialog.dismiss();

                    SharedPreferences paidStatus=getSharedPreferences("LogDetails",MODE_PRIVATE);
                    SharedPreferences.Editor editor =paidStatus.edit();
                    editor.putString("RegDate",sf.format(curdate));
                    editor.putBoolean("PaidSatus",false);
                    editor.apply();


                    Toast.makeText(Registration.this,"Registration Successful",Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

        if(!isConnected)
        {
            Snackbar.make(view,"No Network Connectivity",Snackbar.LENGTH_INDEFINITE).show();

        }
        else {
            Snackbar.make(view,"Connected",Snackbar.LENGTH_SHORT).show();

        }

    }


    private static class RegAsync extends AsyncTask<CompanyMaster, Void, String> {

        private CompanyMaster_DAO master_dao;


        public RegAsync(CompanyMaster_DAO master_dao) {
            this.master_dao = master_dao;
        }

        @Override
        protected String doInBackground(CompanyMaster... companyMasters) {


            master_dao.AddCompany(companyMasters[0]);

            CompId = master_dao.GetCompanyId();


            return "Completed";
        }


        private static class AddUserAsync extends AsyncTask<Users, Void, String> {

            private User_DAO user_dao;


            public AddUserAsync(User_DAO user_dao) {
                this.user_dao = user_dao;
            }

            @Override
            protected String doInBackground(Users... users) {


                user_dao.AddUser(users[0]);

//                CompId=master_dao.GetCompanyId();


                return "Completed";
            }
        }

    }



    private void OnlineRegistration(){

        Log.e("Checking Call","");

        apiService.Register(0,Cname.getText().toString(),cperson.getText().toString(),address.getText().toString()
        ,mob.getText().toString(),mob.getText().toString(),mob.getText().toString(),email.getText().toString(),"0","Android App",
        "Billing App","null",true,username.getText().toString(),pword.getText().toString()).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                Log.d("onResponse","Registration.java "+response.code());

                if(response.isSuccessful()&&response.code()==200)
                {
                    if(response.body()!=null&&response.body().getCode().equalsIgnoreCase("0"))
                    {
                        String respResult="";

                        ArrayList<RegisterResponse.Result>results=response.body().getResults();

                        for(int i=0;i<results.size();i++)
                        {
                            respResult=results.get(i).getResult();

                            SharedPreferences paidStatus=getSharedPreferences("LogDetails",MODE_PRIVATE);
                            SharedPreferences.Editor editor =paidStatus.edit();
                            editor.putString("ClientCode",results.get(i).getClientCode());
                            editor.apply();


                        }
                        Log.d("respResult","Registration.java "+respResult);
                        if(respResult.equalsIgnoreCase("1"))
                        {

                            AddComp();
                        }
                        else {

                            mDialog.dismiss();

                            Toast.makeText(Registration.this,"Username Already Exists",Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {

                        mDialog.dismiss();

                        Toast.makeText(Registration.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDialog.dismiss();

                    Log.d("ErrorCodeeee","Registration.java "+response.code());
                }


            }

            @Override
            public void onFailure(Call<RegisterResponse> call,Throwable t) {

                mDialog.dismiss();

                Toast.makeText(Registration.this,"Something went Wrong",Toast.LENGTH_SHORT).show();


                if(t.getMessage()!=null)
                Log.d("onFailure","Registration.java "+t.getMessage());
            }
        });
    }


    private void CheckUsername(String text)
    {
        Log.e("Checking Call",""+text);

        apiService.checkexisting(text).enqueue(new Callback<CheckUsernameResponse>() {
            @Override
            public void onResponse(Call<CheckUsernameResponse> call, Response<CheckUsernameResponse> response) {

                if(response.isSuccessful()&&response.code()==200)
                {
                    if(response.body()!=null&&response.body().getCode().equalsIgnoreCase("0"))
                    {
                        String respResult="";

                        ArrayList<CheckUsernameResponse.Result>results=response.body().getResults();

                        for(int i=0;i<results.size();i++)
                        {
                            respResult=results.get(i).getResult();

                        }
                        Log.d("respResult","Registration.java "+respResult);
                        if(respResult.equalsIgnoreCase("1"))
                        {
                            username.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.available, 0);

                        }
                        else {
                            username.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.not_available, 0);

                        }
                    }
                    else {
//
//                        mDialog.dismiss();
//
//                        Toast.makeText(Registration.this,"Username Already Exists",Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<CheckUsernameResponse> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInternetAvailabilityChecker
                .removeInternetConnectivityChangeListener(this);
    }
}
