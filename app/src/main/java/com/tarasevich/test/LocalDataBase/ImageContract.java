package com.tarasevich.test.LocalDataBase;

import android.net.Uri;
import android.provider.BaseColumns;

import static android.provider.BaseColumns._ID;
import static com.tarasevich.test.LocalDataBase.ImageContract.Entity.DATE;
import static com.tarasevich.test.LocalDataBase.ImageContract.Entity.IMAGE;
import static com.tarasevich.test.LocalDataBase.ImageContract.Entity.LAT;
import static com.tarasevich.test.LocalDataBase.ImageContract.Entity.LNG;
import static com.tarasevich.test.LocalDataBase.ImageContract.Entity.URL;


public class ImageContract {
    public static final String TABLE_NAME = "image";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE + " TEXT, " +
            LAT + " TEXT, " +
            LNG + " TEXT, " +
            IMAGE + " TEXT,"+
            URL + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private ImageContract() {
    }

    public static class Entity implements BaseColumns {
        public static final String DATE= "date";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String URL = "url";
        public static final String IMAGE = "image";
}
}
