package com.dts_arie.app;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class User extends AppCompatActivity {

    TextView txtnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txtnama = (TextView) findViewById(R.id.textView2);

        SharedPreferences prefs = getSharedPreferences("BelajarPref", MODE_PRIVATE);
        String restoredText = prefs.getString("Key_Username", null);
        if (restoredText != null) {
            String name = prefs.getString("Key_Username", "");//"No name defined" is the default value.
            txtnama.setText(name);
            Log.d("share", name);
        }

    }
}
