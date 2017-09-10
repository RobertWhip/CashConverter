package com.example.mechanic_pc.cashconverter.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechanic_pc.cashconverter.R;
import com.example.mechanic_pc.cashconverter.activity.MainActivity;
import com.example.mechanic_pc.cashconverter.db.CurrencyDatabaseHelper;

public class CurrencyConverterFragment extends Fragment implements View.OnClickListener{

    private String dateText;
    private double rubAsk, rubBid, eurAsk, eurBid, usdAsk, usdBid;
    private TextView rubBuy, rubSell, eurBuy, eurSell, usdBuy, usdSell, date;
    private EditText number;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context context;


    public CurrencyConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_currency, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        context = mainActivity.getMainActivityContext();

        Button convert = v.findViewById(R.id.convert);
        convert.setOnClickListener(this);

        fragmentCreated();

        rubBuy = v.findViewById(R.id.rub_buy);
        rubSell = v.findViewById(R.id.rub_sell);
        eurBuy = v.findViewById(R.id.eur_buy);
        eurSell = v.findViewById(R.id.eur_sell);
        usdBuy =  v.findViewById(R.id.usd_buy);
        usdSell = v.findViewById(R.id.usd_sell);
        date = v.findViewById(R.id.date);
        number = v.findViewById(R.id.number);

        return v;
    }

    @Override
    public void onClick(View v){
        if(number.getText().toString().length() > 0) {

            rubBuy.setText(format(rubAsk * Double.parseDouble(number.getText().toString())));
            rubSell.setText(format(rubBid * Double.parseDouble(number.getText().toString())));

            eurBuy.setText(format(eurAsk * Double.parseDouble(number.getText().toString())));
            eurSell.setText(format(eurBid * Double.parseDouble(number.getText().toString())));

            usdBuy.setText(format(usdAsk * Double.parseDouble(number.getText().toString())));
            usdSell.setText(format(usdBid * Double.parseDouble(number.getText().toString())));

            date.setText(dateText);
        }
    }

    private static String format(double number){
        return String.format("%.2f", number);
    }

    private void fragmentCreated() {

        try {
            SQLiteOpenHelper databaseHelper = new CurrencyDatabaseHelper(context);
            db = databaseHelper.getReadableDatabase();

            cursor = db.query(CurrencyDatabaseHelper.TABLE_NAME,
                        new String[]{"*"},
                        null,
                        null,
                        null, null, null);

            if (cursor.moveToFirst()) {
                if (cursor.getString(7).equals(getResources().getString(R.string.rub_text))) {
                        rubAsk = Double.parseDouble(cursor.getString(3));
                        rubBid = Double.parseDouble(cursor.getString(4));
                }
            }

            if (cursor.moveToNext()) {
                if (cursor.getString(7).equals(getResources().getString(R.string.eur_text))) {
                        eurAsk = Double.parseDouble(cursor.getString(3));
                        eurBid = Double.parseDouble(cursor.getString(4));
                }
            }

            if (cursor.moveToNext()) {
                dateText = cursor.getString(2);
                if (cursor.getString(7).equals(getResources().getString(R.string.usd_text))) {
                    usdAsk = Double.parseDouble(cursor.getString(3));
                    usdBid = Double.parseDouble(cursor.getString(4));
                }
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
                Toast.makeText(context, getResources().getString(R.string.error_db_unavailable), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
                ///Toast.makeText(context, getResources().getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
        }
    }
}
