package com.example.epsi.pickweather.Home.POJO;

import android.content.Context;

import com.example.epsi.pickweather.Utils.Utils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by Camille on 27/02/2016.
 */
public class CallAPIWeather {
    public static Context mContext;
    public static final String API_URL_CITY = "http://api.openweathermap.org/data/2.5/";
    public static OkHttpClient okHttpCli;
    public static RestAdapter.Builder builderAutoCompletionCity;
    public static final int SIZE_OF_CACHE = 10 * 1024 * 1024;

    public static void init(final Context context) {

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        mContext = context;
        Cache cache = null;

            cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);


            okHttpCli = new OkHttpClient();
            if (cache != null) {
                okHttpCli.setCache(cache);
            }
            builderAutoCompletionCity = new RestAdapter.Builder()
                    .setEndpoint(API_URL_CITY)
                    .setLog(new AndroidLog("retrofit"))
                    .setClient(new OkClient(okHttpCli))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(new RequestInterceptor() {

                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Accept", "application/json;versions=1");
                            if (Utils.isNetworkAvailable(context)) {
                                int maxAge = 60; // read from cache for 1 minute
                                request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                            } else {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                                request.addHeader("Cache-Control",
                                        "public, only-if-cached, max-stale=" + maxStale);
                            }
                        }
                    });
        }
    public static <S> S callAPI(Class<S> serviceClass, Context con)
    {
        init(con);
        RestAdapter adapter = builderAutoCompletionCity.build();
        return adapter.create(serviceClass);
    }
}
