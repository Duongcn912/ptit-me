package com.proptit.android.ptitme;


import android.support.multidex.MultiDexApplication;

import com.proptit.android.ptitme.module.DependencyInjector;

public class PtitMeApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // Here we will add the code to inject the dependencies
        injectDependencies();

    }

    private void injectDependencies() {
        DependencyInjector.initialize(this);
        DependencyInjector.getAppComponent().inject(this);
    }
}
