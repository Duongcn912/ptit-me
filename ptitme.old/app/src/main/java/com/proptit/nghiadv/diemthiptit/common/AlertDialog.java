package com.proptit.nghiadv.diemthiptit.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.Lesson;


/**
 * Created by nghia on 4/22/2017.
 */

public class AlertDialog {
    private static Dialog dialogAlert = null;
    private static Dialog dialogWeekDetail = null;


    /**
     * Show alert dialogAlert
     *
     * @param message          : Message show in dialogAlert
     * @param context          : Context for dialogAlert
     * @param mOnClickListener : Action when click dialogAlert
     */
    public static void showAlertWithAction(String message, Context context, final View.OnClickListener mOnClickListener) {

        if (dialogAlert != null && dialogAlert.isShowing()) {
            dialogAlert.dismiss();
            dialogAlert = null;
        }
        dialogAlert = new Dialog(context);
//        Window window = dialogAlert.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAlert.setCancelable(false);
        dialogAlert.setContentView(R.layout.alert_dialog);

        TextView txtmessage = (TextView) dialogAlert.findViewById(R.id.message);
        Button okButton = (Button) dialogAlert.findViewById(R.id.okButton);
        // set text message
        txtmessage.setText(message);
        // set action for button
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                dialogAlert.dismiss();
            }
        });
        dialogAlert.show();
    }


    public static void showAlertTimeTable(Lesson selectedLesson, Context context) {
        if (dialogAlert != null && dialogAlert.isShowing()) {
            dialogAlert.dismiss();
            dialogAlert = null;
        }

        dialogAlert = new Dialog(context);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAlert.setContentView(R.layout.detail_timetable_dialog);

        TextView tv_mamon = (TextView) dialogAlert.findViewById(R.id.tv_mamon);
        TextView tv_sotc = (TextView) dialogAlert.findViewById(R.id.tv_sotc);
        TextView tv_giangvien = (TextView) dialogAlert.findViewById(R.id.tv_giangvien);
        TextView tv_tenmon = (TextView) dialogAlert.findViewById(R.id.tv_tenmon);


        // set text message
        tv_tenmon.setText(selectedLesson.getTenMon());
        tv_mamon.setText(selectedLesson.getMaMon());
        tv_sotc.setText(String.valueOf(selectedLesson.getSoTC()));
        tv_giangvien.setText(selectedLesson.getGiangVien());
        // set action for button

        dialogAlert.show();
    }


    public static void showAlertDetailWeek(String detail, Context context) {
        if (dialogWeekDetail != null && dialogWeekDetail.isShowing()) {
            dialogWeekDetail.dismiss();
            dialogWeekDetail = null;
        }

        dialogWeekDetail = new Dialog(context);
        dialogWeekDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWeekDetail.setContentView(R.layout.detail_week_dialog);

        TextView tv_detail = (TextView) dialogWeekDetail.findViewById(R.id.tv_detail);

        tv_detail.setText(detail);
        // set action for button
        dialogWeekDetail.show();
    }
}