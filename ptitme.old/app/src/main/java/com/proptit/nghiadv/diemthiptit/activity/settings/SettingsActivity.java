package com.proptit.nghiadv.diemthiptit.activity.settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.activity.BaseLayoutActivity;
import com.proptit.nghiadv.diemthiptit.adapter.ListViewAdapter;
import com.proptit.nghiadv.diemthiptit.api.TimeTableAPI;
import com.proptit.nghiadv.diemthiptit.database.ScheduleTestSupport;
import com.proptit.nghiadv.diemthiptit.database.WeekSupport;
import com.proptit.nghiadv.diemthiptit.model.ItemSetting;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.database.LessonSupport;
import com.proptit.nghiadv.diemthiptit.interfaces.TimeTableListener;
import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;
import com.proptit.nghiadv.diemthiptit.model.Week;

import java.util.ArrayList;

public class SettingsActivity extends BaseLayoutActivity {

    ListView listView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setActivityTitle("Settings");
        hideActivityLogo();

        listView = (ListView) findViewById(R.id.listview_settings);

        ArrayList<ItemSetting> listItem = new ArrayList<>();

        String itemDescription[] = getResources().getStringArray(R.array.item_settings);

        ItemSetting item1 = new ItemSetting(R.drawable.ic_startup, itemDescription[0]);
        ItemSetting item2 = new ItemSetting(R.drawable.ic_download_timetable, itemDescription[1]);

        listItem.add(item1);
        listItem.add(item2);


        listView.setAdapter(new ListViewAdapter(this, listItem));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoScreen(position);
            }
        });

    }

    private void gotoScreen(int position) {
        if (position == 0) {
            Intent i = new Intent(this, SettingStartupActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (position == 1) {
            downloadData();
        }
    }

    private void downloadData() {
        TimeTableAPI api = new TimeTableAPI(this);
        api.setListener(new TimeTableListener() {
            @Override
            public void onPreExecute() {
                if (dialog == null) {
                    dialog = new ProgressDialog(SettingsActivity.this);
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

                AlertDialog.showAlertWithAction(getString(R.string.download_complete), SettingsActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                ArrayList<Week> lstWeek = (ArrayList<Week>) object;
                WeekSupport weekSupport = new WeekSupport(SettingsActivity.this);
                weekSupport.insertDataTimeTable(lstWeek);

                ScheduleTestSupport testSupport = new ScheduleTestSupport(SettingsActivity.this);
                ArrayList<String> lstSubjectCode = testSupport.getAllListSubjectCode();

                ArrayList<ScheduleTest> lstSchedule = testSupport.getListScheduleTest(lstSubjectCode);
                boolean result = testSupport.insertScheduleTest(lstSchedule);
                if(!result){
                    AlertDialog.showAlertWithAction("Có lỗi xảy ra", SettingsActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
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

                AlertDialog.showAlertWithAction(message, SettingsActivity.this, new View.OnClickListener() {
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
            AlertDialog.showAlertWithAction(getString(R.string.some_error), SettingsActivity.this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return true;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }

    public String getMaSVFromSharedPre() {
        SharedPreferences prefs = this.getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String msv = prefs.getString(Key.MASV, null);
        return msv;
    }
}
