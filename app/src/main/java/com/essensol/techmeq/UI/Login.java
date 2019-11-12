package com.essensol.techmeq.UI;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.essensol.techmeq.Api.ApiConfig;
import com.essensol.techmeq.Api.ApiService;
import com.essensol.techmeq.Api.LoginResponse;
import com.essensol.techmeq.Model.Credentials;
import com.essensol.techmeq.Model.LoginModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.Tab_Fragments.CreditSales;
import com.essensol.techmeq.ViewModel.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView reg;

    LinearLayout login;

    ImageView logoimage;

    private LoginViewModel model;

    private TextInputEditText uname,pword;

    private TextInputLayout userHint;

    ApiService apiService;


    private static List<LoginModel>CheckingUser=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        reg=findViewById(R.id.reg);

        login=findViewById(R.id.login);

        logoimage=findViewById(R.id.logoimage);

        uname=findViewById(R.id.uname);

        userHint=findViewById(R.id.userHint);

        pword=findViewById(R.id.pword);

        login.requestFocus();



//        model= ViewModelProviders.of(this).get(LoginViewModel.class);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




               SharedPreferences paidStatus=getSharedPreferences("PaidStatus",MODE_PRIVATE);

               String RegDate = paidStatus.getString("RegDate","");
               boolean PaidStatus = paidStatus.getBoolean("PaidSatus",false);

                Date curdate = Calendar.getInstance().getTime();

                SimpleDateFormat sf =new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

                String CurrentDate =sf.format(curdate);

                Log.e("PaidStatus",""+PaidStatus);

                if(!PaidStatus )
                {
                    Log.e("PaidStatus","inIF"+PaidStatus);

                    CheckPaidStatus();
                }
                else {
                    Log.e("PaidStatus","CheckLogin"+PaidStatus);
                    CheckLogin();


                }


            }
        });



        Glide
                .with(this)
                .load(R.drawable.teqmeclogo)
                .into(logoimage);


    }


    private static class CheckLoginAsync extends AsyncTask<Credentials,Void,Integer> {
        User_DAO user_dao;

        public CheckLoginAsync(User_DAO user_dao) {
            this.user_dao = user_dao;
        }

        @Override
        protected Integer doInBackground(Credentials... credentials) {

            List<Users> mlist = user_dao.CheckLogin(credentials[0].getUsername(), credentials[0].getPassword());

            Log.e("Loginnnnn", "Checking" + mlist.size());

            return mlist.size();
        }



    }

    private static class GetLoginDetailsAsync extends AsyncTask<Void, Void, String> {
        User_DAO user_dao;

        public GetLoginDetailsAsync(User_DAO user_dao) {
            this.user_dao = user_dao;
        }

        @Override
        protected String doInBackground(Void... voids) {

            CheckingUser = user_dao.GetLoginDetails();

            return "Completed";
        }

    }


    private void CheckPaidStatus(){

        Log.e("Checking Call","");
//                CheckPaidStatus();
        apiService= ApiConfig.getClient().create(ApiService.class);

        Log.e("Checking Call","");

        apiService.Login(uname.getText().toString(),pword.getText().toString()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Responseeeeeeeee Call",""+response);

                if(response.isSuccessful()&&response.code()==200)

                {

                    Log.d("Responseeeeeee getCode",""+response.body().getCode());

                    if(response.body().getCode().equalsIgnoreCase("0"))
                    {
                        String respResult;

                        ArrayList<LoginResponse.Result>results=response.body().getResults();

                        for(int i=0;i<results.size();i++)
                        {

                            Log.d("Response getLoginResult",""+results.get(i).getLoginResult());
                            if(results.get(i).getLoginResult().equalsIgnoreCase("1"))
                            {

                                Log.d("Response getPayStatus",""+results.get(i).getPayStatus());
                                if(results.get(i).getPayStatus())
                                {

                                    Log.d("Checking Call",""+results.get(i).getPayStatus());

                                    SharedPreferences paidStatus=getSharedPreferences("PaidStatus",MODE_PRIVATE);
                                    SharedPreferences.Editor editor =paidStatus.edit();
                                    editor.putBoolean("PaidSatus",true);
                                    editor.putBoolean("isLogged",true);
                                    editor.apply();

//                                    Intent intent =new Intent(Login.this,MainActivity.class);
//                                    startActivity(intent);

                                    CheckLogin();

                                }
                                else {

                                    Toast.makeText(Login.this,"Complete Payment and Login after sometime",Toast.LENGTH_LONG).show();
                                }

                            }

                        }


                    }
                }
                else {


                    Log.d("ErrorCodeeee","Login.java "+response.code());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }

    private void CheckLogin()
    {

        Log.e("CheckLogin", "Checking" );

        Credentials credentials =new Credentials(uname.getText().toString().trim(),pword.getText().toString().trim());

        try {
            int checkVal=  new CheckLoginAsync(OfflineDb.getInstance(Login.this).user_dao()).execute(credentials).get();

            if(checkVal>0)
            {

                String result =new GetLoginDetailsAsync(OfflineDb.getInstance(Login.this).user_dao()).execute().get();

                if(result.equalsIgnoreCase("Completed"))
                {
                    SharedPreferences sp =getSharedPreferences("LogDetails",MODE_PRIVATE);
                    SharedPreferences.Editor editor =sp.edit();

                    for (int i=0;i<CheckingUser.size();i++)
                    {
                        editor.putString("uname",CheckingUser.get(i).getUserName());
                        editor.putString("compname",CheckingUser.get(i).getCompName());
                        editor.putString("Address",CheckingUser.get(i).getCompBuilding());
                        editor.putString("Phn",CheckingUser.get(i).getMobileNo());
                        editor.putString("TRN",CheckingUser.get(i).getTRN());
                        editor.putInt("compId",CheckingUser.get(i).getCompId());
                        editor.putInt("userId",CheckingUser.get(i).getUserId());
                        editor.putBoolean("isLogged",true);
                    }
                    editor.apply();

                    Intent intent =new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
            else {
                userHint.setError("Invalid Username or Password");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
