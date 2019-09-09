package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.api.LoginBiz;
import com.proptit.nghiadv.diemthiptit.model.Student;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.interfaces.APIListener;

public class LoginActivity extends BaseLayoutActivity {
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = (EditText) findViewById(R.id.et_masv);

        setEvents();
        isLogin();
    }

    private void setEvents() {
        View loginForm = findViewById(R.id.lnLogin);
        loginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.llLogin);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null || actionId == getResources().getInteger(R.integer.login_id)) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        closeKeyboard(v);
                        login();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return null;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }


    public void submit(View v) {
        login();
        closeKeyboard(v);
    }


    void login() {
        String masv = editText.getText().toString().trim();
        //validMaSV(masv);
        gotoMain();
    }

    private void validMaSV(String masv) {
        boolean cancel = false;
        View focusView = null;

        if (masv.equals("")) {
            AlertDialog.showAlertWithAction(getString(R.string.null_msv), this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            focusView = editText;
            cancel = true;
        } else {
            if (masv.length() < 10) {
                AlertDialog.showAlertWithAction(getString(R.string.short_msv), this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                focusView = editText;
                cancel = true;
            } else {
                checkSinhVien(masv);
            }
        }

        if (cancel) {
            focusView.requestFocus();
        }

    }

    private void checkSinhVien(String masv) {
        LoginBiz api = new LoginBiz(getApplicationContext());

        api.setAPIListener(new APIListener() {
            @Override
            public void onPreExecute() {
                showProgress(true);
            }

            @Override
            public void onRequestSuccess(Object object) {
                Student student = (Student) object;
                if (student != null) {
                    String fullName = student.getFullName();
                    saveLoginStatus(student.getStudentCode(), fullName);
                    gotoMain();
                } else {
                    AlertDialog.showAlertWithAction(getString(R.string.some_error), LoginActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                showProgress(false);
            }

            @Override
            public void onRequestFailure(String message, int errorCode) {
                showProgress(false);
                AlertDialog.showAlertWithAction(message, LoginActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        Student student = new Student();
        student.setStudentCode(masv);

        api.execute(GlobalURL.URL_LOGIN, student);

    }

    private void gotoMain() {
        Class activityDefault = getActivityDefault();
        Intent intent = new Intent(LoginActivity.this, activityDefault);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void saveLoginStatus(String masv, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE).edit();
        editor.putString(Key.MASV, masv);
        editor.putString(Key.NAME, name);
        editor.commit();
    }


    private void isLogin() {
        SharedPreferences prefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String masv = prefs.getString(Key.MASV, null);
        if (masv != null) {
            gotoMain();
        }
    }


    private Class getActivityDefault() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Key.START_UP, MODE_PRIVATE);
        int index = sharedPreferences.getInt(Key.START_UP_INDEX, 0);
        switch (index) {
            case 0: {
                return TimeTableActivity.class;
            }
            case 1: {
                return DiemThiActivity.class;
            }
            case 2: {
                return DiemTichLuyActivity.class;
            }
            case 3: {
                return ScheduleTestActivity.class;
            }
        }
        return TimeTableActivity.class;
    }

}
