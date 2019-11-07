package com.essensol.techmeq.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.essensol.techmeq.Api.ApiConfig;
import com.essensol.techmeq.Api.ApiService;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    LinearLayout reg;
    TextInputEditText Cname, cperson, mob, email, address, trn, username, pword;
    TextInputLayout  cnameHint,cpersonHint,mobHint,emailhint,addressHint,trnHint,userHint,pwordHint;
    private static int CompId;

    private ApiService apiService;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg = findViewById(R.id.reg);


        cnameHint = findViewById(R.id.cnameHint);
        cpersonHint = findViewById(R.id.cpersonHint);
        mobHint = findViewById(R.id.mobHint);
        addressHint = findViewById(R.id.addressHint);
        userHint = findViewById(R.id.unameHint);
        pwordHint = findViewById(R.id.pwordHint);



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
                }
               else if(Cperson.equalsIgnoreCase(""))
                {
                    cpersonHint.setError("Field Required");
                }
                else if(Mob.equalsIgnoreCase(""))
                {
                    mobHint.setError("Field Required");
                }
                else if(Email.equalsIgnoreCase(""))
                {
                    emailhint.setError("Field Required");
                }
                else if(Address.equalsIgnoreCase(""))
                {
                    addressHint.setError("Field Required");
                }
               else if(Trn.equalsIgnoreCase(""))
                {
                    trnHint.setError("Field Required");
                }

                else if(Username.equalsIgnoreCase(""))
                {
                    userHint.setError("Field Required");
                }
                else if(Pword.equalsIgnoreCase(""))
                {
                    pwordHint.setError("Field Required");
                }
                else {

                    mDialog.show();

                    OnlineRegistration();

                }




            }
        });


    }

    private void AddComp() {
//        CompCode = compCode;
//        CompName = compName;
//        Contact_Person = contact_Person;
//        CompBuilding = compBuilding;
//        PhoneNo = phoneNo;
//        MobileNo = mobileNo;
//        Email = email;
//        Website = website;
//        TaxType = taxType;
//        Country = country;
//        this.TRN = TRN;
//        Status = status;


        CompanyMaster master = new CompanyMaster("Code", Cname.getText().toString().trim(), cperson.getText().toString().trim(),
                address.getText().toString().trim(), mob.getText().toString().trim(), mob.getText().toString().trim()
                , email.getText().toString().trim(), "Web", "TaxType", "Country", trn.getText().toString().trim(), true);


        try {
            String status = new RegAsync(OfflineDb.getInstance(this).companyMaster_dao()).execute(master).get();

            if (status.equalsIgnoreCase("Completed")) {
                Users users = new Users(CompId, username.getText().toString().trim(), pword.getText().toString().trim());

                String stat = new RegAsync.AddUserAsync(OfflineDb.getInstance(this).user_dao()).execute(users).get();

                if (stat.equalsIgnoreCase("Completed")) {

                    mDialog.dismiss();

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
        "Billing App","1234",true).enqueue(new Callback<RegisterResponse>() {
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
                        }

                        if(respResult.equalsIgnoreCase("1"))
                        {

                            AddComp();
                        }
                    }
                }
                else {
                    Log.d("ErrorCodeeee","Registration.java "+response.code());
                }


            }

            @Override
            public void onFailure(Call<RegisterResponse> call,Throwable t) {

                if(t.getMessage()!=null)
                Log.d("onFailure","Registration.java "+t.getMessage());
            }
        });
    }
}
