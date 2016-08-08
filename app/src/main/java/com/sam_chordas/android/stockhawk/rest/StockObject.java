package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by Al on 2016-07-11.
 */
public class StockObject {

    private String symbol;
    private String bidPrice;
    private String change;

    public StockObject(String symbol, String bidPrice, String change) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
