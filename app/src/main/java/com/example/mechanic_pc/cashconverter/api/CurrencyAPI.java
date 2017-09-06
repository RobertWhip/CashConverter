package com.example.mechanic_pc.cashconverter.api;

import com.example.mechanic_pc.cashconverter.pojo.CurrencyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyAPI {
    @GET("mb/b03af6a10c910fb83692634f77f046021a210bcf")
    Call<List<CurrencyData>> parseData();
}

