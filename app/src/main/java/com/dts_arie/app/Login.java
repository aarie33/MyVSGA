package com.dts_arie.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dts_arie.app.sqlite.SQLiteHelper;

public class Login extends AppCompatActivity {
    public static final String FILENAME = "app.txt";
    TextView user, pass;
    Button btnLogin, btnRegister;

    SharedPreferences share;
    public static final String KEY = "BelajarPref";
    public static final String KEYUsername = "Key_Username";
    public static final String KEYPassword = "Key_Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        user    =  findViewById(R.id.txtusername);
        pass    =  findViewById(R.id.txtpassword);
        btnLogin=  findViewById(R.id.button);
        btnRegister=  findViewById(R.id.button2);


        share = getSharedPreferences(KEY, Context.MODE_PRIVATE);

        if (share.contains(KEYUsername)){
            user.setText(share.getString(KEYUsername,""));
            Intent intent = new Intent(Login.this, Maps.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Daftar.class);
                startActivity(intent);
            }
        });

    }

    public void login(){
        SQLiteHelper dba  = new SQLiteHelper(getApplicationContext());
        Cursor cursor = dba.getUser(user.getText().toString(), pass.getText().toString());
        if (cursor.moveToFirst()){
            SharedPreferences.Editor editor = share.edit();
            editor.putString(KEYUsername, user.getText().toString());
            editor.putString(KEYPassword, pass.getText().toString());
            editor.commit();
            editor.apply();

            startActivity(new Intent(getApplicationContext(), Maps.class));
        }else{
            Toast.makeText(Login.this, "Username atau password salah, silahkan coba lagi", Toast.LENGTH_SHORT).show();;
        }
    }

//    void bacaFile(){
//        File sdcard = getFilesDir();
//        File file   = new File(getFilesDir(), FILENAME);
//        if (file.exists()){
//            StringBuilder text = new StringBuilder();
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(file));
//                String line = br.readLine();
//                while (line != null){
//                    text.append(line);
//                    line = br.readLine();
//                }
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "GAGAL MENAMPILKAN FILE", Toast.LENGTH_LONG).show();
//            }
//            String hasil = text.toString();
//            String[] datauser = hasil.split(";");
//            Log.d("hasil", hasil);
//
//            if (datauser[0].equals(user.getText().toString()) && datauser[1].equals(pass.getText().toString())){
//                Log.d("login : ", "Berhasil");
//                SharedPreferences.Editor editor = share.edit();
//                editor.putString(KEYUsername, user.getText().toString());
//                editor.putString(KEYPassword, pass.getText().toString());
//                editor.commit();
//                editor.apply();
//                Log.d("share : ", "berhasil Share Pref");
//                Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Login.this, Maps.class);
//                startActivity(intent);
//                finish();
//            }else{
//                Log.d("Login : ","Gagal");
//                Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
}
