package com.proptit.nghiadv.diemthiptit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.common.CommonFunction;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSubject;

/**
 * Created by NghiaDV on 19/06/2017.
 */

public class RowMarkTable extends TableRow {
    public static int MODE_PORTRAIT = 1;
    public static int MODE_LANDSCAPE = 2;

    private int mode;

    TextView tv_tenmon, tv_diemcc, tv_diemkt;
    TextView tv_diemth, tv_diembtl, tv_diemthi, tv_diemtb, tv_diemchu;
    View view;

    public RowMarkTable(Context context) {
        super(context);
        init(context);
    }

    public RowMarkTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    void init(Context context) {
        view = inflate(context, R.layout.table_row_layout, null);
        tv_tenmon = (TextView) view.findViewById(R.id.tv_tenMon);

        tv_diemcc = (TextView) view.findViewById(R.id.tv_diemcc);
        tv_diemkt = (TextView) view.findViewById(R.id.tv_diemkt);
        tv_diemth = (TextView) view.findViewById(R.id.tv_diemth);
        tv_diembtl = (TextView) view.findViewById(R.id.tv_diembtl);

        tv_diemthi = (TextView) view.findViewById(R.id.tv_diemthi);
        tv_diemtb = (TextView) view.findViewById(R.id.tv_diemTB);

        tv_diemchu = (TextView) view.findViewById(R.id.tv_diemChu);

    }


    public void setData(MarkOfSubject data) {
        tv_tenmon.setText(data.getTenMon());

        tv_diemcc.setText(CommonFunction.formatDataMark(data.getDiemCC()));
        tv_diemkt.setText(CommonFunction.formatDataMark(data.getDiemTBKT()));
        tv_diemth.setText(CommonFunction.formatDataMark(data.getDiemTH()));
        tv_diembtl.setText(CommonFunction.formatDataMark(data.getDiemBTL()));

        tv_diemthi.setText(CommonFunction.formatDataMark(data.getDiemThi()));
        tv_diemtb.setText(CommonFunction.formatDataMark(data.getDiemKTHP()));

        tv_diemchu.setText(String.valueOf(data.getDiemChu()));
    }

    public View getView() {
        return view;
    }
}
