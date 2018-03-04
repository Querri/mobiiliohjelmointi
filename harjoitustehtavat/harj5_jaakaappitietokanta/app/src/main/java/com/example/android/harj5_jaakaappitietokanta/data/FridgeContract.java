package com.example.android.harj5_jaakaappitietokanta.data;

import android.provider.BaseColumns;

public class FridgeContract {

    public static final class FridgeEntry implements BaseColumns {
        public static final String TABLE_NAME = "waitlist";
        public static final String COLUMN_EXPIRATION_DATE = "";
        public static final String COLUMN_PRODUCT_NAME = "productName";
    }
}
