package com.proptit.nghiadv.diemthiptit.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.api.ScheduleTestAPI;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.database.ScheduleTestSupport;
import com.proptit.nghiadv.diemthiptit.interfaces.APIListener;
import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;
import com.proptit.nghiadv.diemthiptit.view.RowScheduleTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleTestActivity extends BaseLayoutActivity {

    private String TAG = "LOG";
    private TableLayout tableLayout;
    private SwipeRefreshLayout swipe;
    private TextView tv_name;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lich_thi);

        tableLayout = (TableLayout) findViewById(R.id.tb_lichthi);
        tv_name = (TextView) findViewById(R.id.tv_tenSV);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeApi();
            }
        });
        clearData();
        executeApi();
    }

    private void executeApi() {
        ScheduleTestAPI api = new ScheduleTestAPI(this);
        api.setAPIListener(new APIListener() {
            @Override
            public void onPreExecute() {
                //showProgress(true);
            }

            @Override
            public void onRequestSuccess(Object object) {
                ArrayList<ScheduleTest> lstSchedule = (ArrayList<ScheduleTest>) object;
                viewDiem(lstSchedule);
//                showProgress(false);
                swipe.setRefreshing(false);

                tv_name.setVisibility(View.VISIBLE);
                SharedPreferences prefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
                String studentName = prefs.getString(Key.NAME, null);
                tv_name.setText(studentName);
            }

            @Override
            public void onRequestFailure(String message, int errorCode) {
                //showProgress(false);
                swipe.setRefreshing(false);
                clearData();
                AlertDialog.showAlertWithAction(getString(R.string.some_error), ScheduleTestActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

        JSONArray postData = getDataPost();
        api.execute(postData);
    }

    private JSONArray getDataPost() {
        ArrayList<ScheduleTest> lstSchedule = new ScheduleTestSupport(this).getListScheduleTestFromDatabase();
        JSONArray datapost = new JSONArray();
        for (ScheduleTest scheduleTest : lstSchedule) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SubjectCode", scheduleTest.getSubjectCode());
                jsonObject.put("GroupCode", scheduleTest.getGroupCode());
                jsonObject.put("ToThi", scheduleTest.getToThi());
                datapost.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return datapost;
    }


    private void viewDiem(ArrayList<ScheduleTest> lstSchedule) {
        clearData();
        tableLayout.setVisibility(View.VISIBLE);
        for (ScheduleTest scheduleTest : lstSchedule) {
            RowScheduleTable row = new RowScheduleTable(this);
            row.setData(scheduleTest);
            tableLayout.addView(row.getView());
        }
    }


    private void clearData() {
        tv_name.setVisibility(View.GONE);
        tv_name.setText("");
        while (tableLayout.getChildCount() > 1) {
            tableLayout.removeViewAt(1);
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
}
