package com.example.mechanic_pc.cashconverter.activity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mechanic_pc.cashconverter.R;

import com.example.mechanic_pc.cashconverter.pojo.CurrencyData;
import com.example.mechanic_pc.cashconverter.util.CurrencyDatabaseHelper;
import com.example.mechanic_pc.cashconverter.util.CurrencyParser;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<CurrencyData> currencyData;
    CurrencyDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DROP TABLE " + CurrencyDatabaseHelper.TABLE_NAME + ";");
        */
        final TextView msg = (TextView) findViewById(R.id.msg);

        final Handler handler = new Handler();

        final CurrencyParser parser = new CurrencyParser(this);

        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                if((currencyData = parser.getCurrency()) != null) {

                    for(int i = 0; i < currencyData.size(); i++) {
                        try {

                            databaseHelper = new CurrencyDatabaseHelper(MainActivity.this);
                            SQLiteDatabase db = databaseHelper.getWritableDatabase();
                            CurrencyDatabaseHelper.updateCurrencyData(db,
                                    currencyData.get(i).getPointDate(),
                                    currencyData.get(i).getDate(),
                                    currencyData.get(i).getAsk(),
                                    currencyData.get(i).getBid(),
                                    currencyData.get(i).getTrendAsk(),
                                    currencyData.get(i).getTrendBid(),
                                    currencyData.get(i).getCurrency());
                            db.close();
                        } catch (SQLiteException e) {
                            msg.setText("Error");
                        }
                    }
                    go();

                } else {
                    handler.postDelayed(this, 10);
                }
            }

        }, 10);

    }

    private void go() {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }

    private void showIt() {
        TextView msg = (TextView) findViewById(R.id.msg);

        try {

            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.query("currency_table",
                    new String[] {"id, currency"},
                    "id > ?",
                    new String[] {Integer.toString(0)},
                    null, null, null);

            if(cursor.moveToFirst()){
                String s = cursor.getString(0);
                msg.setText(s);
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            msg.setText("error");
        }

    }
}



