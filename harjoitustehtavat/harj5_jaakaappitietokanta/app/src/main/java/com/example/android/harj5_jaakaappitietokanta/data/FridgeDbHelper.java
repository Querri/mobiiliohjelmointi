package com.example.android.harj5_jaakaappitietokanta.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.harj5_jaakaappitietokanta.data.FridgeContract.*;


public class FridgeDbHelper extends SQLiteOpenHelper {

    // database name and version
    private static final String DATABASE_NAME = "akut.db";
    private static final int DATABASE_VERSION = 1;

    // constructor
    public FridgeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_AKU_TABLE = "CREATE TABLE " + FridgeEntry.TABLE_NAME + " (" +
                FridgeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FridgeEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                FridgeEntry.COLUMN_PURCHASE_DATE + " INTEGER NOT NULL," +
                FridgeEntry.COLUMN_EXPIRATION_DATE + " INTEGER NOT NULL," +
                FridgeEntry.COLUMN_DESCRIPTION + " TEXT" + ");";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // just drop table and create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  FridgeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}