package com.proptit.nghiadv.diemthiptit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.activity.settings.SettingsActivity;

import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.common.SharedPreferenceSupport;
import com.proptit.nghiadv.diemthiptit.database.SQLController;

/**
 * Created by NghiaDV on 20/06/2017.
 */

public abstract class BaseLayoutActivity extends BaseActivity {
    private static final String TAG = "Log";
    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public FrameLayout activityContainer;
    public CoordinatorLayout coordinatorLayout;
    private int navId;
    private ProgressDialog progressDialog;
    private com.jpardogo.android.googleprogressbar.library.GoogleProgressBar googleProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navId = getNavIdDefault();
    }

    private int getNavIdDefault() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Key.START_UP, MODE_PRIVATE);
        int index = sharedPreferences.getInt(Key.START_UP_INDEX, 0);
        switch (index) {
            case 0: {
                return 0;
            }
            case 1: {
                return 2;
            }
            case 2: {
                return 5;
            }
            case 3: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (isAppUseDrawerLayout() != null) {
            if (isAppUseDrawerLayout()) {
                DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawablelayout, null);
                activityContainer = (FrameLayout) fullView.findViewById(R.id.content_main);
                getLayoutInflater().inflate(layoutResID, activityContainer, true);
                super.setContentView(fullView);
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                coordinatorLayout = (CoordinatorLayout) drawerLayout.findViewById(R.id.coordinator);
                AppBarLayout appBarLayout = (AppBarLayout) coordinatorLayout.findViewById(R.id.appbar_layout);
                appBarLayout.setVisibility(View.GONE);

                if (isUseToolbar()) {
                    appBarLayout.setVisibility(View.VISIBLE);
                    TextView txtActivityTitle = (TextView) toolbar.findViewById(R.id.activity_title);
                    TextView txtActivitySubTitle = (TextView) toolbar.findViewById(R.id.activity_subtitle);
                    txtActivitySubTitle.setText("");
                    txtActivityTitle.setText("");
                    setSupportActionBar(toolbar);
                    toolbar.setBackgroundResource(R.drawable.header);

                    ImageView img_logo = (ImageView) toolbar.findViewById(R.id.img_logo);
                    img_logo.setImageResource(R.drawable.ic_status_ptit);
                    img_logo.setVisibility(View.VISIBLE);
                    ImageView hamburger = (ImageView) toolbar.findViewById(R.id.homeButton);
                    if (isUseToolbar()) {
                        hamburger.setImageResource(R.drawable.ic_hamburger);
                    }
                    // set event for button of toolbar
                    hamburger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                drawerLayout.closeDrawer(GravityCompat.START);
                            } else {
                                drawerLayout.openDrawer(GravityCompat.START);
                                closeKeyboard(drawerLayout);
                            }
                            closeKeyboard(drawerLayout);
                        }
                    });

                }


                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                toggle.setDrawerIndicatorEnabled(false);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) drawerLayout.findViewById(R.id.nav_view);
                navigationView.setItemIconTintList(null);
                navigationView.setNavigationItemSelectedListener(this);

                NavigationView navigationSignOut = (NavigationView) drawerLayout.findViewById(R.id.nav_view_signout);
                navigationSignOut.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        logout();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

                View headerView = navigationView.getHeaderView(0);
                TextView tv_masv = (TextView) headerView.findViewById(R.id.tv_masv);
                TextView tv_ten = (TextView) headerView.findViewById(R.id.tv_tensv);

                setInfo(tv_masv, tv_ten);


            } else {
                coordinatorLayout = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.activity_basic_toolbarlayout, null);
                activityContainer = (FrameLayout) coordinatorLayout.findViewById(R.id.content_main);
                getLayoutInflater().inflate(layoutResID, activityContainer, true);
                super.setContentView(coordinatorLayout);

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setBackgroundResource(R.drawable.header);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);

                ImageView backButton = (ImageView) toolbar.findViewById(R.id.homeButton);
                backButton.setImageResource(R.drawable.ic_back);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        } else {
            super.setContentView(layoutResID);
        }

    }

    private void setInfo(TextView tv_masv, TextView tv_ten) {
        SharedPreferences preference = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String masv = preference.getString(Key.MASV, "Mã Sinh Viên");
        String tensv = preference.getString(Key.NAME, "Name");

        tv_masv.setText(masv);
        tv_ten.setText(tensv);

    }

    private void logout() {
        SharedPreferenceSupport.clearSharedPreferences(BaseLayoutActivity.this);

        Intent intent = new Intent(BaseLayoutActivity.this, LoginActivity.class);
        //clear all task and start new task
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        SQLController database = new SQLController(this);
        database.deleteDatabase();
        SharedPreferences preferences = getSharedPreferences(Key.START_UP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finish();
    }


    @Override
    public void onBackPressed() {
        if (null != isAppUseDrawerLayout() && isAppUseDrawerLayout()) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            closeKeyboard(toolbar);
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_tkb: {
                navId = 0;
                break;
            }
            case R.id.nav_lichthi: {
                navId = 1;
                break;
            }
            case R.id.nav_diem: {
                navId = 2;
                break;
            }
            case R.id.nav_about: {
                navId = 3;
                break;
            }
            case R.id.nav_diemtichluy: {
                navId = 5;
                break;
            }

            case R.id.nav_settting: {
                navId = 6;
                break;
            }
        }


        loadScreen();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadScreen() {
        switch (navId) {
            case 0: {
                Intent intent = new Intent(this, TimeTableActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }

            case 1: {
                Intent intent = new Intent(this, ScheduleTestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }


            case 2: {
                Intent intent = new Intent(this, DiemThiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }

            case 3: {
                Intent intent = new Intent(this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }


            case 5: {
                Intent intent = new Intent(this, DiemTichLuyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }

            case 6: {
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }
        }
    }


    public void showProgress(boolean status) {

        if (status) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.message_loading));
                progressDialog.setTitle(getString(R.string.loading));
            }
            progressDialog.show();

            int current = getResources().getConfiguration().orientation;
            if (current == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            if (current == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }


    public void setActivityTitle(String content) {
        TextView txtActivityTitle = (TextView) toolbar.findViewById(R.id.activity_title);
        if (txtActivityTitle != null)
            txtActivityTitle.setText(content);
    }


    public void setActivitySubTitle(String content) {
        TextView txtActivitySubTitle = (TextView) toolbar.findViewById(R.id.activity_subtitle);
        if (txtActivitySubTitle != null)
            txtActivitySubTitle.setText(content);
    }

    public void setActivityLogo(int idRes) {
        ImageView imageLogo = (ImageView) toolbar.findViewById(R.id.img_logo);
        if (imageLogo != null)
            imageLogo.setImageResource(idRes);
    }

    public void hideActivityLogo(){
        ImageView imageLogo = (ImageView) toolbar.findViewById(R.id.img_logo);
        if(imageLogo != null){
            imageLogo.setVisibility(View.GONE);
        }
    }

}
