package com.essensol.techmeq.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.CompanyMaster_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.Entity.CompanyMaster;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.OfflineDb;

import java.util.concurrent.ExecutionException;

public class Registration extends AppCompatActivity {

    LinearLayout reg;
    TextInputEditText Cname, cperson, mob, email, address, trn, username, pword;

    private static int CompId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg = findViewById(R.id.reg);

        Cname = findViewById(R.id.Cpname);
        cperson = findViewById(R.id.cperson);
        mob = findViewById(R.id.mob);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        trn = findViewById(R.id.trn);
        username = findViewById(R.id.username);
        pword = findViewById(R.id.pword);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddComp();
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
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
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
}
