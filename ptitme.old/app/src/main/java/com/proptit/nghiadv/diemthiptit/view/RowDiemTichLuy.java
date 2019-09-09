package com.proptit.nghiadv.diemthiptit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSubject;

/**
 * Created by NghiaDV on 19/06/2017.
 */

public class RowDiemTichLuy extends TableRow {
    TableRow layout = null;
    TextView tv_tenmon, tv_stt;
    TextView tv_diemThi, tv_diemTB, tv_diemchu;
    Context context;

    public RowDiemTichLuy(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    public RowDiemTichLuy(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.row_tich_luy_layout, this);
    }


    private void initUI() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (TableRow) li.inflate(R.layout.row_tich_luy_layout, this, true);

        tv_stt = (TextView) layout.findViewById(R.id.tv_stt);
        tv_tenmon = (TextView) layout.findViewById(R.id.tv_tenMon);
        tv_diemThi = (TextView) layout.findViewById(R.id.tv_avg4);
        tv_diemTB = (TextView) layout.findViewById(R.id.tv_avg10);
        tv_diemchu = (TextView) layout.findViewById(R.id.tv_diemchu);

    }


    public void setData(MarkOfSubject data) {
        tv_tenmon.setText(data.getTenMon());
        tv_stt.setText(String.valueOf(data.getSoTT()));
        tv_diemThi.setText(String.valueOf(data.getDiemThi()));
        tv_diemTB.setText(String.valueOf(data.getDiemKTHP()));
        tv_diemchu.setText(data.getDiemChu());
    }

}

