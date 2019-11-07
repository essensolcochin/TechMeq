package com.essensol.techmeq.UI;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.essensol.techmeq.R;

public class SplashScreen extends AppCompatActivity {

    private ImageView splash;
    private boolean isLogged;

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
