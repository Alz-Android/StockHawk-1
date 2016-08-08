package com.sam_chordas.android.stockhawk.widget;

/**
 * Created by Al on 2016-08-04.
 */
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

public class MyWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

//    public void onReceive(Context context, Intent intent) {
//
//        Log.i("MyWidgetProvider", "onReceive");
//        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//        if (intent.getAction().equals(UPDATE_ACTION)) {
//            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, MyWidgetProvider.class));
//            Log.e("received", intent.getAction());
//        }
//        super.onReceive(context, intent);
//    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            // Set up the intent that starts the ListViewService, which will
            // provide the views for this collection.
            Log.i("MyWidgetProvider", "onUpdate :");
            Intent intent = new Intent(context, ListViewWidgetService.class);

            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_detail);

            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            // Trigger listview item click
            Intent startActivityIntent = new Intent(context, MyStocksActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, startActivityPendingIntent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews  object above.

            rv.setEmptyView(R.id.list_view, R.id.empty_view);

            // Do additional processing specific to this app widget...
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
