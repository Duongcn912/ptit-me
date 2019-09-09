package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.common.Key;

import java.util.Calendar;


public class AboutActivity extends BaseLayoutActivity implements View.OnClickListener {

    LinearLayout ln_contact_us, ln_visit_web, ln_facebook, ln_rate, ln_reportbug;

    TextView tv_copyright;

    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/nghiaptit")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nghiaptit")); //catches and opens a url to the desired page
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ln_contact_us = (LinearLayout) findViewById(R.id.ln_contact);
        ln_visit_web = (LinearLayout) findViewById(R.id.ln_web);
        ln_facebook = (LinearLayout) findViewById(R.id.ln_content_us_fb);
        ln_rate = (LinearLayout) findViewById(R.id.ln_rate_us);
        ln_reportbug = (LinearLayout) findViewById(R.id.ln_report_bug);

        ln_contact_us.setOnClickListener(this);
        ln_visit_web.setOnClickListener(this);
        ln_facebook.setOnClickListener(this);
        ln_rate.setOnClickListener(this);
        ln_reportbug.setOnClickListener(this);

        tv_copyright = (TextView) findViewById(R.id.tv_copyright);
        setTextCopyRight();

    }

    private void setTextCopyRight() {
        String text = getResources().getString(R.string.copyright);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        text += currentYear;
        tv_copyright.setText(text);
    }

    @Override
    protected Boolean isAppUseDrawerLayout() {
        return true;
    }

    @Override
    protected Boolean isUseToolbar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ln_contact:
                sendEmail(To.contact);
                break;

            case R.id.ln_web:
                gotoWeb();
                break;

            case R.id.ln_content_us_fb:
                gotoFacebook();
                break;

            case R.id.ln_rate_us:
                gotoStore();
                break;

            case R.id.ln_report_bug:
                sendEmail(To.reportBug);
                break;
        }
    }

    private void gotoStore() {
    }

    private void gotoFacebook() {
        Intent facebookIntent = getOpenFacebookIntent(this);
        startActivity(facebookIntent);
        finish();

    }

    private void gotoWeb() {
    }

    private void sendEmail(To choose) {
        String[] TO = {Key.EMAIL_ADDRESS};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        if (choose == To.contact) {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, Key.SUBJECT_CONTACT);
        } else if (choose == To.reportBug) {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, Key.SUBJECT_BUG);
        }

        Intent chooser = Intent.createChooser(emailIntent, "Send email");
        startActivity(chooser);


    }

    public enum To {
        reportBug, contact;
    }
}
