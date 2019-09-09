package com.proptit.nghiadv.diemthiptit.activity.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.activity.BaseLayoutActivity;
import com.proptit.nghiadv.diemthiptit.common.Key;

public class SettingStartupActivity extends BaseLayoutActivity {


    Button btn_save;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_startup);
        btn_save = (Button) findViewById(R.id.btn_save);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int indexDefault = getDefaultIndex();
        int id = getIdFromIndex(indexDefault);

        radioGroup.check(id);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSave();
            }
        });

    }

    private void submitSave() {
        int idBtnSelected = radioGroup.getCheckedRadioButtonId();
        int index = radioGroup.indexOfChild(findViewById(idBtnSelected));
        saveStartup(index);
        finish();
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return false;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }


    private void saveStartup(int index) {
        SharedPreferences.Editor editor = this.getSharedPreferences(Key.START_UP, MODE_PRIVATE).edit();
        editor.putInt(Key.START_UP_INDEX, index);
        editor.commit();
    }


    private int getIdFromIndex(int index) {
        switch (index) {
            case 0: {
                return R.id.rb_timetable;
            }
            case 1: {
                return R.id.rb_diemthi;
            }
            case 2: {
                return R.id.rb_tichluy;
            }
            case 3: {
                return R.id.rb_lichthi;
            }
        }
        return R.id.rb_timetable;
    }

    private int getDefaultIndex() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Key.START_UP, MODE_PRIVATE);
        int index = sharedPreferences.getInt(Key.START_UP_INDEX, 0);
        return index;
    }

}
