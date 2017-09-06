package com.example.mechanic_pc.cashconverter.pojo;

//import java.util.Date;

public class CurrencyData {
    private int id;
    private String pointDate;
    private String date;
    private double ask;
    private double bid;
    private double trendAsk;
    private double trendBid;
    private String currency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPointDate() {
        return pointDate;
    }

    public void setPointDate(String pointDate) {
        this.pointDate = pointDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getTrendAsk() {
        return trendAsk;
    }

    public void setTrendAsk(double trendAsk) {
        this.trendAsk = trendAsk;
    }

    public double getTrendBid() {
        return trendBid;
    }

    public void setTrendBid(double trendBid) {
        this.trendBid = trendBid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
