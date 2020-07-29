package com.example.sqlitedbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String SALES_DB = "SALES.db";
    public static final String CUSTOMERS_TABLE = "customers";
    public static final String CUSTOMER_ID_COLUMN = "customerId";
    public static final String CUSTOMER_NAME_COLUMN = "customerName";
    public static final String LOCATION_COLUMN = "location";
    public static final String SQL_CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + CUSTOMERS_TABLE + "" +
            "(" + CUSTOMER_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUSTOMER_NAME_COLUMN + " VARCHAR," + LOCATION_COLUMN + " VARCHAR);";

    public DataBaseHelper(@Nullable Context context) {
        super(context, SALES_DB, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STATEMENT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMERS_TABLE);
        onCreate(db);

    }
}
