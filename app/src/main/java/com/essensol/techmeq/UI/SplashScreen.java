package com.essensol.techmeq.UI;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.essensol.techmeq.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    private ImageView splash;
    private boolean isLogged;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        splash = (ImageView) findViewById(R.id.logo);

        Glide.with(this).load(R.drawable.teqmeclogo).into(splash);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                splash,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(2000);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setInterpolator(new FastOutSlowInInterpolator());
        scaleDown.start();

        SharedPreferences settings1 = getSharedPreferences("LogDetails", 0);
        isLogged = settings1.getBoolean("isLogged", false);


        Log.e("checking boolllllll",""+isLogged);

        SharedPreferences paidStatus=getSharedPreferences("LogDetails",MODE_PRIVATE);
        boolean paystatus= paidStatus.getBoolean("PaidSatus",false);

        String installedDate =paidStatus.getString("RegDate","");

        Date curdate = Calendar.getInstance().getTime();

        SimpleDateFormat sf =new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

        String CurrentDate =sf.format(curdate);


        Log.e("checking Installed Date",""+installedDate+"Curr Date  "+CurrentDate);




        assert installedDate != null;
        if(!installedDate.equalsIgnoreCase(CurrentDate)&& !paystatus)
        {
            Log.e("checking Installed Date",""+installedDate);

            Log.e("checking Installed Date",""+installedDate);

            final Intent i = new Intent(this, Login.class);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();
        }

        else {
            if (isLogged) {
                final Intent i = new Intent(this, MainActivity.class);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            startActivity(i);
                            finish();
                        }
                    }
                };
                timer.start();
            } else {
                final Intent i = new Intent(this, Login.class);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            startActivity(i);
                            finish();
                        }
                    }
                };
                timer.start();
            }

        }




    }
}
