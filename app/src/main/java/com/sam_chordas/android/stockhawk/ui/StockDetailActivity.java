package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String stock_symbol = getIntent().getStringExtra("Stock");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM-dd");
        Calendar cal = Calendar.getInstance();
        final String endDateAPI = sdf1.format(cal.getTime());
        final String endDateUI = sdf2.format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        final String startDateAPI = sdf1.format(cal.getTime());
        final String startDateUI = sdf2.format(cal.getTime());
        Log.i("sort1", startDateUI);

        // Get Historical data using Retrofit
        HistoricalDataStockApi stockService = ServiceGenerator.createService(HistoricalDataStockApi.class);
        String query = "select * from yahoo.finance.historicaldata where symbol='" + stock_symbol + "' and startDate = '"+startDateAPI+"' and endDate = '"+endDateAPI+"'";

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
                        String stockQuote = response.body().query.results.quote.get(i).close;
                        Log.i("sort1", stockQuote);
                        valsComp1.add(new Entry(Float.valueOf(stockQuote), i));
                        if(i==0)
                            xVals.add(startDateUI);
                        else if(i==response.body().query.results.quote.size()-10)
                            xVals.add(endDateUI);
                        else xVals.add("");
                    }


                    Log.i("sort1", "update3");
                    LineChart chart = (LineChart) findViewById(R.id.chart);
                    YAxis yLAxis = chart.getAxisLeft();
                    yLAxis.setTextSize(15f);
                    yLAxis.setTextColor(Color.CYAN);
                    yLAxis.setDrawLabels(true);

                    YAxis yRAxis = chart.getAxisRight();
                    yRAxis.setTextSize(15f);
                    yRAxis.setTextColor(Color.CYAN);

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(15f);
                    xAxis.setTextColor(Color.CYAN);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(true);
                    xAxis.setDrawLabels(true);


                    // Y-axis
                    LineDataSet setComp = new LineDataSet(valsComp1, stock_symbol);
                    setComp.setValueTextColor(Color.CYAN);
                    setComp.setValueTextSize(12f);
                    setComp.setAxisDependency(YAxis.AxisDependency.LEFT);

                    // X-axis
                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(setComp);
                    Log.i("sort1", "update4");
                    LineData data = new LineData(xVals, dataSets);
                    chart.setDescription("");
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
