package com.proptit.android.ptitme.network;

import android.content.Context;


/**
 * Created by diegonovati on 18/04/2018.
 */

/**
 * This class implements the connections with the backend using Retrofit
 */
public class NetworkService {

    public NetworkService(Context context, ApiService apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    private Context context;
    private ApiService apiService;


    public ApiService getApiService() {
        return apiService;
    }
}
