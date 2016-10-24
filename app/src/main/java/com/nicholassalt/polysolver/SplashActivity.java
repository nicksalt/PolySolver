package com.nicholassalt.polysolver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor e = myPrefs.edit();
        if (myPrefs.getBoolean("appHasNotRanBefore", true)){
            e.putInt("decimal", 4);
            e.putBoolean("appHasNotRanBefore", false);
            e.apply();
            int SPLASH_TIME_OUT = 2500;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(0,0);
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0,0);
        }
    }
}
