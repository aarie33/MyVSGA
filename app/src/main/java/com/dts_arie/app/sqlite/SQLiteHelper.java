package com.dts_arie.app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SQLiteHelper extends SQLiteOpenHelper {
    
    public SQLiteHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ Const.TABLE_NAME +
                "(" + Const.KEY_ID + " INTEGER PRIMARY KEY, "+
                Const.USERNAME + " TEXT," +
                Const.PASSWORD + " TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Const.TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(ContractUser user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Const.USERNAME, user.getUsername());
        values.put(Const.PASSWORD, md5(user.getPassword()));

        db.insert(Const.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public Cursor getUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME, new String[]{Const.KEY_ID,
                        Const.USERNAME, Const.PASSWORD},
                Const.USERNAME+"=\""+username+"\" AND "+Const.PASSWORD+"=\""+md5(password)+"\"",
                null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
