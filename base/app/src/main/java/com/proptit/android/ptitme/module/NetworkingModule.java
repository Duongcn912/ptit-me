package com.proptit.android.ptitme.module;

import android.content.Context;

import com.proptit.android.ptitme.BuildConfig;
import com.proptit.android.ptitme.network.ApiService;
import com.proptit.android.ptitme.network.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diegonovati on 18/04/2018.
 */

/**
 * This module creates the NetworkingService class linking the NetworkAPI interface to Retrofit.
 * Note: in the getRetrofit method is defined to use the GSON library to convert json to java; if
 * the backend uses XML, use an XML converter instead.
 */
@Module(includes = { ContextModule.class })
public class NetworkingModule {

    @Provides
    @Singleton
    public NetworkService getNetworkingService(Context context) {
        // Note: the base url is defined in the app build.gradle (so that we can have testing and
        // production backend
        ApiService networkingAPI = getRetrofit(BuildConfig.SERVER_URL).create(ApiService.class);
        return new NetworkService(context, networkingAPI);
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor).build();
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // Link between Retrofit 2 and RxJava 2
                .build();
    }
}