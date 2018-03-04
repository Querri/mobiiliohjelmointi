package com.example.android.harj5_jaakaappitietokanta;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.harj5_jaakaappitietokanta.data.FridgeContract;
import com.example.android.harj5_jaakaappitietokanta.data.FridgeDbHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private EditText mProductNameEditText;
    private EditText mExpirationDateEditText;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductNameEditText = findViewById(R.id.et_product_name);
        mExpirationDateEditText = findViewById(R.id.et_product_expiration);

        FridgeDbHelper dbHelper = new FridgeDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        //Cursor curosr = getAllItems();
    }


    public void addToFridge(View view) {
        if (mProductNameEditText.getText().length() != 0  ||
                mExpirationDateEditText.getText().length() != 0) {
            return;
        }

        int expiration = 0;
        try {
            expiration = Integer.parseInt(mExpirationDateEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Päivämäärän muunto epäonnistui: " + e.getMessage());
        }

        addNewItem(expiration, mProductNameEditText.getText().toString());

        //TODO swap cursor

        mProductNameEditText.clearFocus();
        mProductNameEditText.getText().clear();
        mExpirationDateEditText.getText().clear();
    }


    // query the mDb and get all items from the fridge table
    private Cursor getAllItems() {
        return mDb.query(FridgeContract.FridgeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FridgeContract.FridgeEntry.COLUMN_EXPIRATION_DATE
                );
    }


    // add item to database
    private long addNewItem(int expiration, String name) {
        ContentValues cv = new ContentValues();
        cv.put(FridgeContract.FridgeEntry.COLUMN_EXPIRATION_DATE, expiration);
        cv.put(FridgeContract.FridgeEntry.COLUMN_PRODUCT_NAME, name);
        return mDb.insert(FridgeContract.FridgeEntry.TABLE_NAME, null, cv);
    }
}
