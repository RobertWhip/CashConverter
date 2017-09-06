package com.example.mechanic_pc.cashconverter.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mechanic_pc.cashconverter.R;
import com.example.mechanic_pc.cashconverter.util.CurrencyDatabaseHelper;

public class TestActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView msg = (TextView) findViewById(R.id.msg);

        SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(this);

        try {

            db = databaseHelper.getReadableDatabase();

            cursor = db.query(CurrencyDatabaseHelper.TABLE_NAME,
                    new String[] {"*"},
                    null,
                    null,
                    null, null, null);

            if(cursor.moveToFirst()){
                String s = cursor.getString(4);
                msg.setText(s);
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            msg.setText("error");
        }
    }
}
