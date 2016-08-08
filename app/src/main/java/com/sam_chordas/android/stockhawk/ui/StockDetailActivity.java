package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.retrofit.GetHistoricalData;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalData;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalDataStockApi;
import com.sam_chordas.android.stockhawk.retrofit.Quote;
import com.sam_chordas.android.stockhawk.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_detail);

        String stock = getIntent().getStringExtra("Stock");


        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        // Get Historical data using Retrofit
        HistoricalDataStockApi stockService = ServiceGenerator.createService(HistoricalDataStockApi.class);
        Call<HistoricalData> call = stockService.STOCK_LIST_CALL();
        Log.i("sort1", "update11");

        call.enqueue(new Callback<HistoricalData>() {
            @Override
            public void onResponse(Call<HistoricalData> call, Response<HistoricalData> response) {
                Log.i("sort1", "update112");
                if (response.isSuccess()) {
                    Log.i("sort1", "update2");
                    for (int i = 0; i < response.body().query.results.quote.size(); i++) {
                        String stockQuote   = response.body().query.results.quote.get(i).close;
                        String date         = response.body().query.results.quote.get(i).date;
                        Entry entry = new Entry(Float.valueOf(stockQuote), i);
 //                       valsComp1.add(entry);

                        Log.i("sortid", date);
                    }

                } else {
                    Log.i("sort1", "update Error");
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<HistoricalData> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("sort1", t.getMessage());
            }
        });



        xVals.add("1.Q");

        LineChart chart = (LineChart) findViewById(R.id.chart);
        YAxis leftAxis = chart.getAxisLeft();

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);

        // Y-axis
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        // X-axis
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        // chart.clear();

        // chart.getLineData();
    }
}
