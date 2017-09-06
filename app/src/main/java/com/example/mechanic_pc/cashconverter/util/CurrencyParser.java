package com.example.mechanic_pc.cashconverter.util;

import android.content.Context;
import android.widget.Toast;

import com.example.mechanic_pc.cashconverter.R;
import com.example.mechanic_pc.cashconverter.api.CurrencyAPI;
import com.example.mechanic_pc.cashconverter.pojo.CurrencyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyParser {

    private static final String BASE_JSON_URL = "http://api.minfin.com.ua/";
    private List<CurrencyData> currency;
    private Context context;
    private boolean result;

    public CurrencyParser(Context context) {
        this.context = context;
        parseJson();
    }

    public boolean parseJson(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_JSON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyAPI service = retrofit.create(CurrencyAPI.class);

        Call<List<CurrencyData>> call = service.parseData();

        call.enqueue(new Callback<List<CurrencyData>>() {

            @Override
            public void onResponse(Call<List<CurrencyData>> call, Response<List<CurrencyData>> response) {

                currency = response.body();
                result = true;

            }

            @Override
            public void onFailure(Call<List<CurrencyData>> call, Throwable t) {
                result = false;
            }
        });
        return result;
    }

    public List<CurrencyData> getCurrency() {
        return currency;
    }
}
