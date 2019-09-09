package com.proptit.nghiadv.diemthiptit.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.Lesson;

/**
 * Created by nghia on 7/14/2017.
 */

public class CustomCell extends LinearLayout {
    TextView tv_room, tv_subject;

    Lesson lesson;
    View view;

    public CustomCell(Context context) {
        super(context);
       // initUI(context);
    }

    private void initUI(Context context) {
        view = inflate(context, R.layout.cell_layout, null);
        tv_room = (TextView) view.findViewById(R.id.tv_room);
        tv_subject = (TextView) view.findViewById(R.id.tv_subject);
    }

    public CustomCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.double_text,this);
        //initUI(context);
    }

    public CustomCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initUI(context);
    }

    public void setData(Lesson lesson) {
        tv_room.setText(lesson.getPhongHoc());
        tv_subject.setText(lesson.getTenMon());
    }

    public View getView() {
        return view;
    }


    public void setRoom(String room){
        tv_room.setText(room);
    }

    public void setSubject(String subject){
        tv_subject.setText(subject);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_room = (TextView) findViewById(R.id.tv_room);
        tv_subject = (TextView) findViewById(R.id.tv_subject);
    }
}
