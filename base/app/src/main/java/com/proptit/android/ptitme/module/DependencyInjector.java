package com.proptit.android.ptitme.module;


import com.proptit.android.ptitme.PtitMeApplication;

/**
 * Created by diegonovati on 18/04/2018.
 */

public class DependencyInjector {

    public static void initialize(PtitMeApplication application) {
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(application))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private DependencyInjector() {
    }

    private static AppComponent appComponent;
}
