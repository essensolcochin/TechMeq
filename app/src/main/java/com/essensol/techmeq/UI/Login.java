package com.essensol.techmeq.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.essensol.techmeq.R;

public class Login extends AppCompatActivity {

    TextView reg;

    LinearLayout login;

    ImageView logoimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        reg=findViewById(R.id.reg);

        login=findViewById(R.id.login);

        logoimage=findViewById(R.id.logoimage);


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
                Intent intent =new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });



        Glide
                .with(this)
                .load(R.drawable.teqmeclogo)
                .into(logoimage);


    }
}
