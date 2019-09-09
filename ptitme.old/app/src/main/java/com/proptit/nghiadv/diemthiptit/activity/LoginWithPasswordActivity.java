package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.common.Key;

public class LoginWithPasswordActivity extends BaseLayoutActivity {
    EditText ed_msv;
    EditText ed_password;

    Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_password);
        ed_msv = (EditText) findViewById(R.id.et_masv);
        ed_password = (EditText) findViewById(R.id.et_password);


        fillMSV();
        btn_submit = (Button) findViewById(R.id.btn_submit_login);

        setEvent();
    }

    public void setEvent() {
        ed_msv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        closeKeyboard(v);
                        sendData();
                    }
                    return true;
                }
                return false;
            }
        });

        ed_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        closeKeyboard(v);
                        sendData();
                    }
                    return true;
                }
                return false;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                sendData();
            }
        });
    }


    private void sendData() {
        Intent intent = new Intent();
        String masv = ed_msv.getText().toString();
        String pass = ed_password.getText().toString();
        intent.putExtra("msv", masv);
        intent.putExtra("pass", pass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void fillMSV() {
        SharedPreferences prefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String masv = prefs.getString(Key.MASV, null);
        if (masv != null) {
            ed_msv.setText(masv);
        }
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return false;
    }

    @Override
    protected Boolean isUseToolbar() {
        return null;
    }


}
