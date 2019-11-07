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

import com.bumptech.glide.Glide;
import com.essensol.techmeq.Model.Credentials;
import com.essensol.techmeq.Model.LoginModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.Tab_Fragments.CreditSales;
import com.essensol.techmeq.ViewModel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    TextView reg;

    LinearLayout login;

    ImageView logoimage;

    private LoginViewModel model;

    private TextInputEditText uname,pword;

    private TextInputLayout userHint;


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
                                editor.putInt("compId",CheckingUser.get(i).getCompId());
                                editor.putInt("userId",CheckingUser.get(i).getUserId());
                                editor.putBoolean("isLogged",true);
                            }
                            editor.apply();

                            Intent intent =new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
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

//               if(checking.size()>0)
//               {
////                   for (int i=0;i<)
//
//
//               }
//               else {
//                   //ToDo
//               }


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

}
