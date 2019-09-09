package com.proptit.nghiadv.diemthiptit.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.api.DiemBiz;
import com.proptit.nghiadv.diemthiptit.application.BaseApplication;
import com.proptit.nghiadv.diemthiptit.model.MarkOfStudent;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSubject;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.interfaces.APIListener;
import com.proptit.nghiadv.diemthiptit.model.Student;
import com.proptit.nghiadv.diemthiptit.view.RowMarkTable;

import java.util.ArrayList;

public class DiemThiActivity extends BaseLayoutActivity {
    private static final String TAG = "LOGDIEM";

    EditText ed_masv;
    TextView tv_tensv;
    TextView tv_av4, tv_av10;
    TableLayout tb_diem, tb_diemtb;

    private MarkOfStudent markOfStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diem);

        ed_masv = (EditText) findViewById(R.id.et_masv);
        tv_tensv = (TextView) findViewById(R.id.tv_tenSV);

        tb_diem = (TableLayout) findViewById(R.id.tb_diem);
        tb_diem.setVisibility(View.GONE);

        tb_diemtb = (TableLayout) findViewById(R.id.tb_diemtb);
        tb_diemtb.setVisibility(View.GONE);


        tv_av4 = (TextView) findViewById(R.id.tv_avg4);
        tv_av10 = (TextView) findViewById(R.id.tv_avg10);

        fillMSV();

        if (savedInstanceState == null) {
            requestGetDiem();
        } else {
            if (savedInstanceState.containsKey("MarkOfStudent")) {
                markOfStudent = savedInstanceState.getParcelable("MarkOfStudent");
                viewDiem(markOfStudent);
            }

        }

    }

    private void fillMSV() {
        SharedPreferences prefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String masv = prefs.getString(Key.MASV, null);
        ed_masv.setText(masv);
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return true;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }


    public void submit(View v) {
        requestGetDiem();
        closeKeyboard(v);
    }

    private void requestGetDiem() {
        String masv = ed_masv.getText().toString().trim();

        DiemBiz diemBiz = new DiemBiz(getApplicationContext());
        diemBiz.setAPIListener(new APIListener() {
            @Override
            public void onPreExecute() {
                showProgress(true);
            }

            @Override
            public void onRequestSuccess(Object object) {
                markOfStudent = (MarkOfStudent) object;
                String tenSV = markOfStudent.getFullName();

                if (markOfStudent.lstMarkSize() == 0) {
                    AlertDialog.showAlertWithAction(BaseApplication.getmInstance().getString(R.string.not_yet), DiemThiActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {
                    tb_diem.setVisibility(View.VISIBLE);
                    tv_tensv.setText(tenSV);
                    viewDiem(markOfStudent);
                }
                tv_tensv.setText(tenSV);
                showProgress(false);


            }

            @Override
            public void onRequestFailure(String message, int errorCode) {
                tb_diem.setVisibility(View.GONE);
                clearData();
                AlertDialog.showAlertWithAction(BaseApplication.getmInstance().getString(R.string.wrong_msv), DiemThiActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                showProgress(false);
            }
        });
        Student student = new Student();
        student.setStudentCode(masv);
        diemBiz.execute(student);
    }



    private void viewDiem(MarkOfStudent markOfStudent) {
        clearData();
        tb_diem.setVisibility(View.VISIBLE);
        ArrayList<MarkOfSubject> lstMark = markOfStudent.getLstMark();

        for (MarkOfSubject markOfSubject : lstMark) {
            RowMarkTable row = new RowMarkTable(DiemThiActivity.this);
            row.setData(markOfSubject);
            tb_diem.addView(row.getView());
        }

        tb_diemtb.setVisibility(View.VISIBLE);
        tv_av4 = (TextView) findViewById(R.id.tv_avg4);
        tv_av10 = (TextView) findViewById(R.id.tv_avg10);

        tv_av4.setText(String.valueOf(markOfStudent.getAvg4()));
        tv_av10.setText(String.valueOf(markOfStudent.getAvg10()));
    }


    private void clearData() {
        TableLayout tableView = (TableLayout) findViewById(R.id.tb_diem);
        while (tableView.getChildCount() > 1) {
            tableView.removeViewAt(1);
        }
        tv_tensv.setText("");
        tb_diemtb.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MarkOfStudent",markOfStudent);
    }
}
