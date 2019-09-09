package com.proptit.nghiadv.diemthiptit.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.adapter.PagerAdapter;
import com.proptit.nghiadv.diemthiptit.api.TimeTableAPI;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.database.ScheduleTestSupport;
import com.proptit.nghiadv.diemthiptit.database.WeekSupport;
import com.proptit.nghiadv.diemthiptit.interfaces.TimeTableListener;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;
import com.proptit.nghiadv.diemthiptit.model.Week;

import java.util.ArrayList;

public class TimeTableActivity extends BaseLayoutActivity {
    private static final String TAG = "LOG_BA";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tkb);
        changColorStatusBar();

        WeekSupport support = new WeekSupport(this);
        if (support.isCreatedDatabase()) {
            ArrayList<Week> lstWeek = support.getAllWeek();
            Week[] arrayWeek = lstWeek.toArray(new Week[lstWeek.size()]);
            loadDataOnUI(arrayWeek);
        } else {
            executeAPI();
        }
    }

    private void loadDataOnUI(Week[] lstWeek) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setVisibility(View.GONE);

        WeekSupport weekSupport = new WeekSupport(this);
        int currentWeek = weekSupport.getCurrentWeek();

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), lstWeek);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentWeek - 1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return true;
    }

    @Override
    protected Boolean isUseToolbar() {
        return false;
    }

    private void changColorStatusBar() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBarTT));
        }
    }

    private void executeAPI() {
        TimeTableAPI api = new TimeTableAPI(this);
        api.setListener(new TimeTableListener() {
            @Override
            public void onPreExecute() {
                if (dialog == null) {
                    dialog = new ProgressDialog(TimeTableActivity.this);
                }
                dialog.setTitle("Download Data");
                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(false);
                dialog.setMax(100);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onRequestSuccess(Object object) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertDialog.showAlertWithAction(getString(R.string.download_complete), TimeTableActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ArrayList<Week> lstWeek = (ArrayList<Week>) object;
                processData(lstWeek);
            }

            @Override
            public void onDoInBackGround(int status) {
                dialog.setProgress(status);
            }

            @Override
            public void onRequestFailure(String message, int errorCode) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertDialog.showAlertWithAction(message, TimeTableActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        String masv = getMaSVFromSharedPre();

        if (masv != null) {
            String url_TKB = GlobalURL.URL_TKB + masv;
            api.execute(url_TKB);
        } else {
            AlertDialog.showAlertWithAction(getString(R.string.some_error), TimeTableActivity.this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    private void processData(ArrayList<Week> lstWeek) {
        saveDataInDatabase(lstWeek);
        Week[] arrayWeek = lstWeek.toArray(new Week[lstWeek.size()]);
        loadDataOnUI(arrayWeek);
    }

    private void saveDataInDatabase(ArrayList<Week> lstWeek) {
        WeekSupport weekSupport = new WeekSupport(this);
        weekSupport.insertDataTimeTable(lstWeek);

        ScheduleTestSupport testSupport = new ScheduleTestSupport(this);
        ArrayList<String> lstSubjectCode = testSupport.getAllListSubjectCode();

        ArrayList<ScheduleTest> lstSchedule = testSupport.getListScheduleTest(lstSubjectCode);
        boolean result = testSupport.insertScheduleTest(lstSchedule);
        if(!result){
            AlertDialog.showAlertWithAction("Có lỗi xảy ra", this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private String getMaSVFromSharedPre() {
        SharedPreferences prefs = this.getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String msv = prefs.getString(Key.MASV, null);
        return msv;
    }


}
