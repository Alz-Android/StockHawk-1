package com.sam_chordas.android.stockhawk.retrofit;

//import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Al on 2/25/2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://query.yahooapis.com";

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
//            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

}
