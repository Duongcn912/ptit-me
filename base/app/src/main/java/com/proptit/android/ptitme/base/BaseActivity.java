package com.proptit.android.ptitme.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.proptit.android.ptitme.module.AppComponent;
import com.proptit.android.ptitme.module.DependencyInjector;
import com.proptit.android.ptitme.network.NetworkService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected NetworkService networkService;

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            // Here we will add the code to inject the dependencies
            onInject(DependencyInjector.getAppComponent());
        } catch (Exception ex) {
        }
    }
    protected void onInject(AppComponent appComponent) {
        if (appComponent != null) {
            appComponent.inject(this);
        }
    }

}
