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

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {

            Log.i("MyWidgetProvider", "onUpdate :");
            Intent intent = new Intent(context, ListViewWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_detail);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            // Trigger listview item click
            Intent startActivityIntent = new Intent(context, MyStocksActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, startActivityPendingIntent);

            rv.setEmptyView(R.id.list_view, R.id.empty_view);

            // Do additional processing specific to this app widget...
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
