package com.proptit.nghiadv.diemthiptit.view;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;

/**
 * Created by nghia on 7/15/2017.
 */

public class DoubleTextView extends LinearLayout {
    LinearLayout layout = null;
    TextView tv_room = null;
    TextView tv_subject = null;
    Context mContext = null;
    ImageView imageView;


    public DoubleTextView(Context context) {
        super(context);
        mContext = context;
        initUI();
    }

    private void initUI() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) li.inflate(R.layout.double_text, this, true);

        tv_room = (TextView) layout.findViewById(R.id.tv_room);
        tv_subject = (TextView) layout.findViewById(R.id.tv_subject);

        RelativeLayout relativeLayout = (RelativeLayout) layout.findViewById(R.id.rl_note);
        imageView = (ImageView) relativeLayout.findViewById(R.id.img_pencil);

    }

    public DoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View.inflate(context, R.layout.double_text, this);
    }

    public DoubleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }


    public void setRoom(String text) {
        if (!text.equals("")) {
            text = "<font color='gray'><b><i>PH: </i></b></font> <font color='teal'><b>" + text + "</b></font>";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_room.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_room.setText(Html.fromHtml(text));
        }
    }

    public void setSubject(String text) {
        if (!text.equals("")) {
            text = "<font color='gray'><b><i>MH: </i></b></font> <font color='teal'><b>" + text + "</b></font>";
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_subject.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_subject.setText(Html.fromHtml(text));
        }
    }

    public String getRoom() {
        return tv_room.getText().toString();
    }

    public String getSubject() {
        return tv_subject.getText().toString();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_room = (TextView) findViewById(R.id.tv_room);
        tv_subject = (TextView) findViewById(R.id.tv_subject);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_note);
        imageView = (ImageView) relativeLayout.findViewById(R.id.img_pencil);

    }

    public void visibleIconNote(boolean status){
        if(status == true){
            imageView.setVisibility(VISIBLE);
        }
        else imageView.setVisibility(GONE);
    }
}