package com.tarasevich.test.LocalDataBase;



import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "image.db";
    private static final int DB_VERSION = 1;

    public  DataBase (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ImageContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ImageContract.DROP_TABLE);
        onCreate(db);
    }
}