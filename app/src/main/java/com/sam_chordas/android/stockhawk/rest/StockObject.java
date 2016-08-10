package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by Al on 2016-07-11.
 */
public class StockObject {

    private String symbol;
    private String bidPrice;
    private String change;
    private boolean isUp;

    public StockObject(String symbol, String bidPrice, String change, boolean isUp) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.change = change;
        this.isUp = isUp;
    }
    public boolean GetIsUp() {
        return isUp;
    }
    public void setIsUp(boolean up) {
        isUp = up;
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
