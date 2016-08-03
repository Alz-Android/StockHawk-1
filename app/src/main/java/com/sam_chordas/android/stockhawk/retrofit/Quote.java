package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Quote {

    @SerializedName("Symbol")
    @Expose
    public String symbol;
    @SerializedName("Date")
    @Expose
    public String date;
    @SerializedName("Open")
    @Expose
    public String open;
    @SerializedName("High")
    @Expose
    public String high;
    @SerializedName("Low")
    @Expose
    public String low;
    @SerializedName("Close")
    @Expose
    public String close;
    @SerializedName("Volume")
    @Expose
    public String volume;
    @SerializedName("Adj_Close")
    @Expose
    public String adjClose;

}

