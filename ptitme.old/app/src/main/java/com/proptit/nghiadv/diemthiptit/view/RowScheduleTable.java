package com.proptit.nghiadv.diemthiptit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;

public class RowScheduleTable extends TableRow {

    TextView tv_tenmon, tv_ngaythi, tv_giothi, tv_phongthi;
    View view;

    public RowScheduleTable(Context context) {
        super(context);
        init(context);
    }

    public RowScheduleTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        view = inflate(context, R.layout.row_schedule_layout, null);
        tv_tenmon = (TextView) view.findViewById(R.id.tv_tenmon);
        tv_ngaythi = (TextView) view.findViewById(R.id.tv_ngaythi);
        tv_giothi = (TextView) view.findViewById(R.id.tv_giothi);
        tv_phongthi = (TextView) view.findViewById(R.id.tv_phongthi);
    }

    public void setData(ScheduleTest scheduleTest){
        tv_tenmon.setText(scheduleTest.getSubjectName());
        tv_giothi.setText(scheduleTest.getStartTime());
        tv_ngaythi.setText(scheduleTest.getTestDay());
        tv_phongthi.setText(scheduleTest.getTestRoom());
    }

    public View getView() {
        return view;
    }
}
