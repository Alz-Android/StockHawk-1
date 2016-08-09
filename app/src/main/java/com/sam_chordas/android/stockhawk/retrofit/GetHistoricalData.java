//package com.sam_chordas.android.stockhawk.retrofit;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by Al on 2016-08-07.
// */
//public class GetHistoricalData {
//
//
//    //   private Context mContext;
//    public GetHistoricalData(String stockSymbol) {
//        Log.i("sort1", "update1");
//
//
//        // default 'false' flag for movies being favorites
//        String[] args = new String[]{"false"};
//
//        HistoricalDataStockApi stockService = ServiceGenerator.createService(HistoricalDataStockApi.class);
//
//        Call<HistoricalData> call = stockService.STOCK_LIST_CALL();
//        Log.i("sort1", "update11");
//
//        call.enqueue(new Callback<HistoricalData>() {
//            @Override
//            public void onResponse(Call<HistoricalData> call, Response<HistoricalData> response) {
//                Log.i("sort1", "update112");
//                if (response.isSuccess()) {
//                    Log.i("sort1", "update2");
//                    for (int i = 0; i < response.body().query.results.quote.size(); i++) {
//                        Quote stockQuote = (Quote) response.body().query.results.quote.get(i);
//                        String date = stockQuote.date;
//                        Log.i("sortid", stockQuote.close.toString());
//                    }
//
//                } else {
//                    Log.i("sort1", "update Error");
//                    // error response, no access to resource?
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HistoricalData> call, Throwable t) {
//                // something went completely south (like no internet connection)
//                Log.d("sort1", t.getMessage());
//            }
//        });
//
//    }
//}