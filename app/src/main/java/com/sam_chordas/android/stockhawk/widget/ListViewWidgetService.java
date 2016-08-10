package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.StockObject;

import java.util.ArrayList;

/**
 * Created by Al on 2016-08-04.
 */
public class ListViewWidgetService extends RemoteViewsService {

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private Cursor cursor = null;
            private ArrayList<StockObject> records;

            public void onCreate() {
                Log.i("ListViewWidgetService", "onCreate");

                records = new ArrayList<StockObject>();
            }

            public RemoteViews getViewAt(int position) {
                Log.i("ListViewWidgetService", "getViewAt");

                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);

                rv.setTextViewText(R.id.stock_symbol, records.get(position).getSymbol());
                rv.setTextViewText(R.id.bid_price, records.get(position).getBidPrice());
                rv.setTextViewText(R.id.change, records.get(position).getChange());

//                if(records.get(position).GetIsUp())
//                    rv.setInt(R.id.change, "setBackgroundColor",
//                            R.drawable.percent_change_pill_green);
//                else
//                    rv.setInt(R.id.change, "setBackgroundColor",
//                            R.drawable.percent_change_pill_red);


                Intent fillInIntent = new Intent();
                fillInIntent.setData(QuoteProvider.Quotes.CONTENT_URI);

                rv.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

                return rv;
            }

            public int getCount() {
                Log.e("size=", records.size() + "");
                return records.size();
            }

            public void onDataSetChanged() {

                final long identityToken = Binder.clearCallingIdentity();

                cursor = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                        new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                        QuoteColumns.ISCURRENT + "= ?",
                        new String[]{"1"},
                        null);
                Binder.restoreCallingIdentity(identityToken);

                cursor.moveToFirst();
                Log.i("ListViewWidgetService", String.valueOf(cursor.getCount()));
                try {
                    cursor.moveToPosition(-1);
                    while (cursor.moveToNext()) {
                        records.add(new StockObject(cursor.getString(0),
                                                    cursor.getString(1),
                                                    cursor.getString(2),
                                                    Boolean.valueOf(cursor.getString(3))));
                    }
                } finally {
                    cursor.close();
                }
            }

            public int getViewTypeCount() {
                return 1;
            }

            public long getItemId(int position) {
                return position;
            }

            public void onDestroy() {
                records.clear();
            }

            public boolean hasStableIds() {
                return true;
            }

            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }
        };
    }
}
