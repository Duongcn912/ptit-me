package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.common.AnimationUtils;
import com.proptit.nghiadv.diemthiptit.interfaces.PermissionListener;

public class SplashScreen extends BaseLayoutActivity implements PermissionListener {
    private static final String TAG = "LogS";
    private final long DELAY_LENGHT = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPermissionListener(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //getSupportActionBar().hide();
        ImageView iv_loading = (ImageView) findViewById(R.id.ic_loading);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.indicator_rotate);
        iv_loading.startAnimation(rotate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, DELAY_LENGHT);
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return null;
    }

    @Override
    protected Boolean isUseToolbar() {
        return false;
    }


    @Override
    public void onPermissionsSuccess() {
        gotoLoginScreen();
    }

    private void gotoLoginScreen() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onPermissionsFailure() {
        //System.exit(0);
    }
}
