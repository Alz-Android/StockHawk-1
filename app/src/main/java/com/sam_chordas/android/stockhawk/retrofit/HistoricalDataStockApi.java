package com.sam_chordas.android.stockhawk.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Al on 1/28/2016.
 */
public interface HistoricalDataStockApi {

    @GET("/v1/public/yql?q= select * from yahoo.finance.historicaldata where symbol = \"AAPL\" and startDate = \"2012-08-09\" and endDate = \"2012-09-10\" &format=json &diagnostics=true &env=store://datatables.org/alltableswithkeys &callback=")

    Call<HistoricalData> STOCK_LIST_CALL(
 //           @Query("stock_symbol") String stock_symbol
    );
}
