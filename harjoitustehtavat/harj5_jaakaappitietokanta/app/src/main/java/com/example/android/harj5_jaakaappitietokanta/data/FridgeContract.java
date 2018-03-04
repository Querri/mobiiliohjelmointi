package com.example.android.harj5_jaakaappitietokanta.data;

import android.provider.BaseColumns;

public class FridgeContract {

    public static final class FridgeEntry implements BaseColumns {
        public static final String TABLE_NAME = "fridge";
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_EXPIRATION_DATE = "expirationDate";
    }
}
