package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    public static boolean showPercent = true;
    private static String LOG_TAG = Utils.class.getSimpleName();
    private static String dateString;
//    private static Context mContext;
//
//    public Utils(Context context) {
//        mContext = context;
//    }

    public static float FormatDate(String dateString) {

        float dateGraphPoint=0;

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = fmt.parse(dateString);

            SimpleDateFormat month = new SimpleDateFormat("MM");
            SimpleDateFormat days = new SimpleDateFormat("d");
            SimpleDateFormat hours = new SimpleDateFormat("H");

            float hoursFormat  = Float.parseFloat(String.format("%.2f", (Float.parseFloat(hours.format(date)))/24));

            dateGraphPoint = Float.parseFloat(days.format(date)) + hoursFormat;

            Log.i("util", "hoursFormat " + hoursFormat);
            Log.i("util", "dateGraphPoint: " + dateGraphPoint);


        } catch (ParseException e) {
            Log.e(LOG_TAG, "Parsing the Date failed: " + e);
            e.printStackTrace();
        }
        return dateGraphPoint;
    }

    public static ArrayList quoteJsonToContentVals(String JSON, Context context) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try {
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0) {
                jsonObject = jsonObject.getJSONObject("query");
                int count = Integer.parseInt(jsonObject.getString("count"));

                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                dateString = String.valueOf(jsonObject.getString("created"));
                Date date = fmt.parse(dateString);

                SimpleDateFormat month = new SimpleDateFormat("MM");
                SimpleDateFormat days = new SimpleDateFormat("dd");
                SimpleDateFormat hours = new SimpleDateFormat("HH");
//                Log.i("Utils1", month.format(date));
//                Log.i("Utils1", days.format(date));
//                Log.i("Utils1", hours.format(date));

                // there's only one stock
                if (count == 1) {
                    jsonObject = jsonObject.getJSONObject("results").getJSONObject("quote");

                    Log.i("StockTaskService1", jsonObject.getString("Bid"));
                    if ((jsonObject.getString("Bid")).equals("null")) {
                        Log.i("StockTaskService", "That stock does not exist");
                        Toast.makeText(context, "That stock does not exist",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        batchOperations.add(buildBatchOperation(jsonObject));
                    }
                } else {
                    resultsArray = jsonObject.getJSONObject("results").getJSONArray("quote");

                    Log.i("Utils resultsArray", resultsArray.toString());

                    if (resultsArray != null && resultsArray.length() != 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            jsonObject = resultsArray.getJSONObject(i);

                            Log.i("Utils jsonObject", jsonObject.toString());
                            batchOperations.add(buildBatchOperation(jsonObject));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "String to JSON failed: " + e);
        }  catch (ParseException e) {
            Log.e(LOG_TAG, "Parsing the Date failed: " + e);
            e.printStackTrace();
        }
        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));
        return bidPrice;
    }

    public static String truncateChange(String change, boolean isPercentChange) {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(JSONObject jsonObject) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        try {
            String change = jsonObject.getString("Change");
            builder.withValue(QuoteColumns.SYMBOL, jsonObject.getString("symbol"));
            builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(jsonObject.getString("Bid")));
            builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                    jsonObject.getString("ChangeinPercent"), true));
            builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
            builder.withValue(QuoteColumns.ISCURRENT, 1);

            if (change.charAt(0) == '-') {
                builder.withValue(QuoteColumns.ISUP, 0);
            } else {
                builder.withValue(QuoteColumns.ISUP, 1);
            }

            builder.withValue(QuoteColumns.CREATED, dateString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}
