package com.sam_chordas.android.stockhawk.retrofit;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Results {

    @SerializedName("quote")
    @Expose
    public List<Quote> quote = new ArrayList<Quote>();

}
