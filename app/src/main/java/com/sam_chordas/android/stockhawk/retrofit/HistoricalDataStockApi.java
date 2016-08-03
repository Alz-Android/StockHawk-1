package com.sam_chordas.android.stockhawk.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Al on 1/28/2016.
 */
public interface HistoricalDataStockApi {

    @GET("/v1/public/yql?q=")

    Call<HistoricalData> STOCK_LIST_CALL(
            @Query("stock_symbol") String stock_symbol
    );
}
