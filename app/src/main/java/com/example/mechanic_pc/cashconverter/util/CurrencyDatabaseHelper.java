package com.example.mechanic_pc.cashconverter.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyDatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "currency_db";
    private static final int DB_VERSION = 4;

    public static final String TABLE_NAME = "currency_table";
    public static final String _ID = "id";
    public static final String _POINT_DATE = "pointDate";
    public static final String _DATE = "date";
    public static final String _ASK = "ask";
    public static final String _BID = "bid";
    public static final String _TREND_ASK = "trendAsk";
    public static final String _TREND_BID = "trendBid";
    public static final String _CURRENCY = "currency";

    public CurrencyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //updateDatabase(db, 0, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, 0, DB_VERSION);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + _POINT_DATE +" TEXT, "
                    + _DATE + " TEXT, "
                    + _ASK + " DOUBLE, "
                    + _BID + " DOUBLE, "
                    + _TREND_ASK + " DOUBLE, "
                    + _TREND_BID + " DOUBLE, "
                    + _CURRENCY + " TEXT);");
        }
        if(oldVersion < 3) {
            insertCurrencyData(db, "0", "0", 0, 0, 0, 0, "rub");
            insertCurrencyData(db, "0", "0", 0, 0, 0, 0, "eur");
            insertCurrencyData(db, "0", "0", 0, 0, 0, 0, "usd");
        }
    }

    public static void updateCurrencyData(SQLiteDatabase db,
                                          String pointDate,
                                          String date,
                                          double ask,
                                          double bid,
                                          double trendAsk,
                                          double trendBid,
                                          String currency) {

        ContentValues currencyValues = new ContentValues();
        currencyValues.put(_POINT_DATE, pointDate);
        currencyValues.put(_DATE, date);
        currencyValues.put(_ASK, ask);
        currencyValues.put(_BID, bid);
        currencyValues.put(_TREND_ASK, trendAsk);
        currencyValues.put(_TREND_BID, trendBid);
        currencyValues.put(_CURRENCY, currency);

        try {

            db.update(TABLE_NAME, currencyValues,
                    _CURRENCY + " = ?", new String[]{currency});
            db.close();


        } catch (SQLiteException e) {

        }
    }

    public static void insertCurrencyData(SQLiteDatabase db,
                                          String pointDate,
                                          String date,
                                          double ask,
                                          double bid,
                                          double trendAsk,
                                          double trendBid,
                                          String currency) {

        ContentValues currencyValues = new ContentValues();
        currencyValues.put(_POINT_DATE, pointDate);
        currencyValues.put(_DATE, date);
        currencyValues.put(_ASK, ask);
        currencyValues.put(_BID, bid);
        currencyValues.put(_TREND_ASK, trendAsk);
        currencyValues.put(_TREND_BID, trendBid);
        currencyValues.put(_CURRENCY, currency);

        db.insert(TABLE_NAME, null, currencyValues);
    }

}