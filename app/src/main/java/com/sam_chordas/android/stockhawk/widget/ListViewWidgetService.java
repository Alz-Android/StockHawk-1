package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

/**
 * Created by Al on 2016-08-04.
 */
public class ListViewWidgetService extends RemoteViewsService {

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private Cursor cursor = null;
            private ArrayList<String> records;

            // Initialize the data set.
            public void onCreate() {
                Log.i("ListViewWidgetService", "onCreate");

                // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
                // for example downloading or creating content etc, should be deferred to onDataSetChanged()
                // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
                records = new ArrayList<String>();
            }
            // Given the position (index) of a WidgetItem in the array, use the item's text value in
            // cobination with the app widget item XML file to construct a RemoteViews object.

            public RemoteViews getViewAt(int position) {

                Log.i("ListViewWidgetService", "getViewAt");
                // position will always range from 0 to getCount() - 1.
                // Construct a RemoteViews item based on the app widget item XML file, and set the
                // text based on the position.

                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
                // feed row

                String data = records.get(position);


                rv.setTextViewText(R.id.item, data);

                // end feed row
                // Next, set a fill-intent, which will be used to fill in the pending intent template
                // that is set on the collection view in ListViewWidgetProvider.

                Bundle extras = new Bundle();
                extras.putInt(MyWidgetProvider.EXTRA_ITEM, position);
                Intent fillInIntent = new Intent();
                fillInIntent.putExtra("stock_info", data);
                fillInIntent.putExtras(extras);

                // Make it possible to distinguish the individual on-click
                // action of a given item
                rv.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                // Return the RemoteViews object.
                return rv;
            }

            public int getCount() {
                Log.e("size=", records.size() + "");
                return records.size();
            }

            public void onDataSetChanged() {
                // Fetching JSON data from server and add them to records arraylist

                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();

                cursor = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                        new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.CHANGE},
                        QuoteColumns.ISCURRENT + "= ?",
                        new String[]{"1"},
                        null);
                Binder.restoreCallingIdentity(identityToken);

                cursor.moveToFirst();
                Log.i("ListViewWidgetService", String.valueOf(cursor.getCount()));
                try {
                    while (cursor.moveToNext()) {
                        if (cursor.getString(1) != null) {
                            Log.i("ListViewWidgetService", cursor.getString(0));
                            Log.i("ListViewWidgetService", cursor.getString(1));
                            Log.i("ListViewWidgetService", cursor.getString(2));
     //                       records.add("");
                        }
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
