package com.dts_arie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("BelajarPref", MODE_PRIVATE);
                String restoredText = prefs.getString("Key_Username", null);
                if (restoredText != null) {
                        Intent main = new Intent(Splash.this, Maps.class);
                        startActivity(main);
                }else{
                    Intent login = new Intent(Splash.this, Login.class);
                    startActivity(login);
                }
                finish();
            }
        },1000);
    }
}
