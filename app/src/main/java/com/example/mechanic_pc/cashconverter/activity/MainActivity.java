package com.example.mechanic_pc.cashconverter.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mechanic_pc.cashconverter.R;

import com.example.mechanic_pc.cashconverter.fragment.CurrencyConverterFragment;
import com.example.mechanic_pc.cashconverter.pojo.CurrencyData;
import com.example.mechanic_pc.cashconverter.db.CurrencyDatabaseHelper;
import com.example.mechanic_pc.cashconverter.util.CurrencyParser;
import com.example.mechanic_pc.cashconverter.util.InternetConnection;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<CurrencyData> currencyData;
    private CurrencyParser parser;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                InternetConnection net = new InternetConnection(MainActivity.this);
                if(net.connectionAvailable())
                    loadDatabase();
                else
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();

            }
        });

        loadDatabase();

    }

    public void loadDatabase() {
        final TextView msg = (TextView) findViewById(R.id.msg);
        InternetConnection net = new InternetConnection(this);

        if(net.connectionAvailable()) {


            try {
                parser = new CurrencyParser(this);
            } catch(Exception e) {
                msg.setText(getResources().getString(R.string.error_cannot_parse));
            }

            try {
                SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(this);
                db = databaseHelper.getWritableDatabase();
            } catch(Exception e) {
                msg.setText(getResources().getString(R.string.error_db_not_found));
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((currencyData = parser.getCurrency()) != null) {

                        for (int i = 0; i < currencyData.size(); i++) {
                            try {
                                SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(MainActivity.this);
                                db = databaseHelper.getWritableDatabase();
                                CurrencyDatabaseHelper.updateCurrencyData(db,
                                        currencyData.get(i).getPointDate(),
                                        currencyData.get(i).getDate(),
                                        currencyData.get(i).getAsk(),
                                        currencyData.get(i).getBid(),
                                        currencyData.get(i).getTrendAsk(),
                                        currencyData.get(i).getTrendBid(),
                                        currencyData.get(i).getCurrency());
                                db.close();

                            } catch(SQLiteException e) {
                                msg.setText(getResources().getString(R.string.error_cannot_write_to_db));
                            } catch (Exception e) {
                                msg.setText(getResources().getString(R.string.error_unknown));

                            }
                        }

                        loadFragment();

                    } else {
                        handler.postDelayed(this, 10);
                    }
                }

            }, 10);
        } else {
            try {

                SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(this);
                db = databaseHelper.getReadableDatabase();

                //if data exists in the database
                db = databaseHelper.getReadableDatabase();

                cursor = db.query(CurrencyDatabaseHelper.TABLE_NAME,
                        new String[]{"*"},
                        null,
                        null,
                        null, null, null);


                if (cursor.moveToFirst()) {
                    if (!cursor.getString(3).equals("0")) {
                        Toast.makeText(this, getResources().getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
                        loadFragment();
                    } else {
                        //if there isn't data in database
                        Toast.makeText(this, getResources().getString(R.string.error_no_internet_no_data), Toast.LENGTH_LONG).show();
                    }
                }

                cursor.close();
                db.close();

            } catch (Exception e) {
                //if there isn't data in database
                Toast.makeText(this, getResources().getString(R.string.error_no_internet_no_data), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CurrencyConverterFragment converter = new CurrencyConverterFragment();

        ft.replace(R.id.container, converter);
        ft.commit();
    }

    public Context getMainActivityContext() {
        return this;
    }
}



