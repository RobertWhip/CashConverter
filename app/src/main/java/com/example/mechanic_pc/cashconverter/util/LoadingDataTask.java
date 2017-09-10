package com.example.mechanic_pc.cashconverter.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.mechanic_pc.cashconverter.db.CurrencyDatabaseHelper;
import com.example.mechanic_pc.cashconverter.pojo.CurrencyData;

import java.util.List;

public class LoadingDataTask extends AsyncTask<Context, Void, Boolean> {

    final Handler handler = new Handler();
    private Context context;
    private List<CurrencyData> currencyData;

    protected void onPreExecute() {
        //something
    }

    protected Boolean doInBackground(Context... context) {
        this.context = context[0];
       boolean result;





       try {
           final CurrencyParser parser = new CurrencyParser(this.context);

           CurrencyDatabaseHelper databaseHelper = new CurrencyDatabaseHelper(this.context);
           final SQLiteDatabase db = databaseHelper.getWritableDatabase();

           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   if ((currencyData = parser.getCurrency()) != null) {
                       Toast.makeText(LoadingDataTask.this.context, currencyData.get(0).getId(), Toast.LENGTH_SHORT).show();
                       for(int i = 0; i < currencyData.size(); i++) {

                           CurrencyDatabaseHelper.insertCurrencyData(db,
                                   currencyData.get(i).getPointDate(),
                                   currencyData.get(i).getDate(),
                                   currencyData.get(i).getAsk(),
                                   currencyData.get(i).getBid(),
                                   currencyData.get(i).getTrendAsk(),
                                   currencyData.get(i).getTrendBid(),
                                   currencyData.get(i).getCurrency());

                       }
                       db.close();

                   } else {
                       handler.postDelayed(this, 10);
                   }
               }
           }, 10);

           result = true;

       } catch (SQLiteException e) {
            result = false;
       } catch (Exception e) {
           result = false;
       }

       return result;
    }

    protected void onPostExecute(Boolean success) {
        if(success)
            Toast.makeText(context, "Things are done.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Database unavailable.", Toast.LENGTH_SHORT).show();
    }
}
