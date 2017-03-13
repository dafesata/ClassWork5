package com.example.laboratorio.classwork5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by daniel on 12/03/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public String TAG= Constanst.TAG;

    public static final int DATABASE_VERSION =1;

    public static final String DATABASE_NAME = "TheDataBase";

    public static final String TABLE = "theTable";
    public static final String KEY_ID = "id";
    public static final String KEY_FIELD1= "titulo";
    public static final String KEY_FIELD2= "fecha";
    public static final String KEY_FIELD3= "texto";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseBundler Constructor");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "OnCreateDB");
        String CREATE_TABLE="CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_FIELD1 + " TEXT,"
                + KEY_FIELD2 + " TEXT,"
                + KEY_FIELD3 + " TEXT"+")";
        if(db.isOpen()){
            db.execSQL(CREATE_TABLE);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "OnUpdateDB");
        db.execSQL("DROP TABLE IF EXIST " + TABLE);
    }

    public SQLiteDatabase getWriteDatabase(){
        return super.getWritableDatabase();
    }
}
