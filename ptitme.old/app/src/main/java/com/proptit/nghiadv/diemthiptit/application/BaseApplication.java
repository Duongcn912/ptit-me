package com.proptit.nghiadv.diemthiptit.application;

import android.app.Application;

/**
 * Created by nghia on 6/30/2017.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static synchronized BaseApplication getmInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
