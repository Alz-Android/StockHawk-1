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
import com.sam_chordas.android.stockhawk.retrofit.HistoricalData;
import com.sam_chordas.android.stockhawk.retrofit.HistoricalDataStockApi;
import com.sam_chordas.android.stockhawk.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        final String stock_symbol = getIntent().getStringExtra("Stock");

        // Get Historical data using Retrofit
        HistoricalDataStockApi stockService = ServiceGenerator.createService(HistoricalDataStockApi.class);
        String query = "select * from yahoo.finance.historicaldata where symbol='" + stock_symbol + "' and startDate = '2016-06-09' and endDate = '2016-06-19'" ;

        Call<HistoricalData> call = stockService.STOCK_LIST_CALL(query);
        Log.i("sort1", query);

        call.enqueue(new Callback<HistoricalData>() {
            @Override
            public void onResponse(Call<HistoricalData> call, Response<HistoricalData> response) {

                setContentView(R.layout.activity_stock_detail);

                ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
                ArrayList<String> xVals = new ArrayList<String>();
                if (response.isSuccess()) {
                    Log.i("sort1", "update2");
                    for (int i = 0; i < response.body().query.results.quote.size(); i++) {
                        String stockQuote   = response.body().query.results.quote.get(i).close;
                        String date         = response.body().query.results.quote.get(i).date;
                        Log.i("sort1", stockQuote);
                        valsComp1.add(new Entry(Float.valueOf(stockQuote), i));
                        xVals.add("x");
                    }
                    Log.i("sort1", "update3");
                        LineChart chart = (LineChart) findViewById(R.id.chart);
                        YAxis yAxis = chart.getAxisLeft();
                        yAxis.setTextSize(15f);
                        yAxis.setTextColor(Color.CYAN);

                        XAxis xAxis = chart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTextSize(15f);
                        xAxis.setTextColor(Color.BLUE);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setDrawGridLines(true);

                        // Y-axis
                        LineDataSet setComp = new LineDataSet(valsComp1, stock_symbol);
                        setComp.setAxisDependency(YAxis.AxisDependency.LEFT);
      //                  setComp.setColor(R.color.material_blue_500);

                        // X-axis
                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                        dataSets.add(setComp);
                    Log.i("sort1", "update4");
                        LineData data = new LineData(xVals, dataSets);
                        chart.setData(data);
                    Log.i("sort1", "update5");
                } else {
                    Log.i("sort1", "update Error");
                    Log.i("sort1", response.raw().message());
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<HistoricalData> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("sort1", t.getMessage());
            }
        });
    }
}
