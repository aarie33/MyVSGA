package com.dts_arie.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dts_arie.app.sqlite.ContractUser;
import com.dts_arie.app.sqlite.SQLiteHelper;

public class Daftar extends AppCompatActivity {
    TextView username, pass;//, email, nama, asal, alamat;
    Button btnDaftar;
    private SQLiteHelper dba;

    public static final String FILENAME = "app.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Pendaftaran user");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username    =  findViewById(R.id.txtUser);
        pass    =  findViewById(R.id.txtPass);
//        email    = (TextView) findViewById(R.id.txtEmail);
//        nama    = (TextView) findViewById(R.id.txtNama);
//        asal    = (TextView) findViewById(R.id.txtAsal);
//        alamat    = (TextView) findViewById(R.id.txtAlamat);
        btnDaftar   =  findViewById(R.id.btn_daftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daftar()){
                    Toast.makeText(Daftar.this, "User berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }else{
                    Toast.makeText(Daftar.this, "User gagal disimpan, silahkan coba lagi", Toast.LENGTH_SHORT).show();;
                }
            }
        });

    }

    public boolean daftar(){
        ContractUser user = new ContractUser();
        if (validation()){
            dba = new SQLiteHelper(getApplicationContext());
            user.setUsername(username.getText().toString());
            user.setPassword(pass.getText().toString());

            dba.addUser(user);

            dba.close();

            return true;
        }else{
            return false;
        }
    }

//    void buatFile(){
//
//        String isiFile = user.getText().toString() + ";"
//                + pass.getText().toString() + ";"
//                + email.getText().toString()+";"
//                + nama.getText().toString()+";"
//                + asal.getText().toString()+";"
//                + alamat.getText().toString();
//        File file   = new File(getFilesDir(), FILENAME);
//        if (file.exists()){
//            file.delete();
//        }
//        FileOutputStream outputStream = null;
//        Toast.makeText(getApplicationContext(), isiFile, Toast.LENGTH_LONG);
//        try {
//            file.createNewFile();
//            outputStream    = new FileOutputStream(file, true);
//            outputStream.write(isiFile.getBytes());
//            outputStream.flush();
//            outputStream.close();
//            Toast.makeText(getApplicationContext(), "Berhasil menyimpan file", Toast.LENGTH_LONG).show();
//            Log.d("coba : ","Berhasil Menyimpan");
//            Intent login = new Intent(Daftar.this, Login.class);
//            SharedPreferences pref = getApplicationContext().getSharedPreferences("BelajarPref", 0);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.remove("Key_Username");
//            editor.clear();
//            editor.commit();
//            startActivity(login);
//            finish();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "Gagal menyimpan file", Toast.LENGTH_LONG).show();
//            Log.d("coba : ","GAGAL Menyimpan");
//        }
//
//    }

    boolean validation(){

        if (username.getText().toString().equals("") ||
                pass.getText().toString().equals("")
//                email.getText().toString().equals("") ||
//                nama.getText().toString().equals("") ||
//                asal.getText().toString().equals("") ||
//                alamat.getText().toString().equals("")
                ){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
