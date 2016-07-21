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

import java.util.ArrayList;

public class StockDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_detail);

        String stock = getIntent().getStringExtra("Stock");

        Cursor cursor = getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.BIDPRICE, QuoteColumns.CREATED},
                QuoteColumns.SYMBOL + "= ?",
                new String[]{stock},
                null);

        cursor.moveToFirst();
        Log.i("StockDetailActivity", cursor.getString(0));
        Log.i("StockDetailActivity", String.valueOf(cursor.getCount()));
        Log.i("StockDetailActivity", String.valueOf(cursor.getColumnNames()));
        Log.i("StockDetailActivity1", String.valueOf(stock));

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int xIndex=0;

        try {
            while (cursor.moveToNext()){

                if (cursor.getString(1)!= null) {

//                                    StockObject stockObject = new StockObject(Float.parseFloat(cursor.getString(0)), Utils.FormatDate(cursor.getString(1)));
//                                    stockArray.add(stockObject);

                    Log.i("StockDetailActivity", cursor.getString(0));
                    Log.i("StockDetailActivity", String.valueOf(Utils.FormatDate(cursor.getString(1))));

                    Entry entry = new Entry(Float.valueOf(cursor.getString(0)), xIndex++);
                    valsComp1.add(entry);

                    xVals.add("1.Q");// xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");
                }
            }
        } finally {
            cursor.close();
        }


        LineChart chart = (LineChart) findViewById(R.id.chart);
        YAxis leftAxis = chart.getAxisLeft();

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);



//        Entry c1e1 = new Entry(100.000f, 0);
//        Entry c1e2 = new Entry(10.000f, 1);
//        Entry c1e3 = new Entry(200.000f, 1);
//        Entry c1e4 = new Entry(300.000f, 2);// 0 == quarter 1
//        valsComp1.add(c1e1);
//        valsComp1.add(c1e2);
//        valsComp1.add(c1e3);
//        valsComp1.add(c1e4);

        // Y-axis
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        // X-axis
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);


 //       xVals.add("1.Q"); xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        // chart.clear();

        // chart.getLineData();
    }
}
