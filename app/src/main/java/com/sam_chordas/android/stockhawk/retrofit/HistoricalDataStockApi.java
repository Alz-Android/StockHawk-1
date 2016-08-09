package com.sam_chordas.android.stockhawk.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Al on 1/28/2016.
 */
public interface HistoricalDataStockApi {

    @GET("/v1/public/yql?&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=")

    Call<HistoricalData> STOCK_LIST_CALL(
            @Query("q") String stock_symbol
    );
}
