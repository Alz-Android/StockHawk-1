package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by Al on 2016-07-11.
 */
public class StockObject {

    private float stockPrice;
    private float dateTime;

    public StockObject(float stockPrice, float dateTime) {
        this.stockPrice = stockPrice;
        this.dateTime = dateTime;
    }

    public float getDateTime() {

        return dateTime;
    }

    public void setDateTime(float dateTime) {
        this.dateTime = dateTime;
    }

    public float getStockPrice() {

        return stockPrice;
    }

    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }
}
