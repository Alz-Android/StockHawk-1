package com.sam_chordas.android.stockhawk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Query {

    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("lang")
    @Expose
    public String lang;
//    @SerializedName("diagnostics")
//    @Expose
//    public Diagnostics diagnostics;
    @SerializedName("results")
    @Expose
    public Results results;

}
