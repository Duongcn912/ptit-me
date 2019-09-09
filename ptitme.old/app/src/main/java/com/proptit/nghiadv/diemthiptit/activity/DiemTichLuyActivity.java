package com.proptit.nghiadv.diemthiptit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.api.DiemTichLuyAPI;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.common.Key;
import com.proptit.nghiadv.diemthiptit.interfaces.APIListener;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSemester;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSubject;
import com.proptit.nghiadv.diemthiptit.view.RowDiemTichLuy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DiemTichLuyActivity extends BaseLayoutActivity {

    private static final String TAG = "LOG";
    private TableLayout tableLayout;
    private ArrayList<MarkOfSemester> lstMarkOfSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_tich_luy);

        tableLayout = (TableLayout) findViewById(R.id.tb_layout);
        tableLayout.setVisibility(View.GONE);
        lstMarkOfSemester = new ArrayList<>();

        if (savedInstanceState == null) {
            checkLogin();
        } else {
            if (savedInstanceState.containsKey("markofsemester")) {
                lstMarkOfSemester = savedInstanceState.getParcelableArrayList("markofsemester");
                new PrepareDataUI().execute();
            }
        }


        Button btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }


    private void logout() {
        SharedPreferences mySPrefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(Key.MASV);
        editor.remove(Key.PASS);
        editor.apply();

        tableLayout.setVisibility(View.GONE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_button);
        linearLayout.setVisibility(View.GONE);
    }


    private void checkLogin() {
        SharedPreferences prefs = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE);
        String masv = prefs.getString(Key.MASV, null);
        String pass = prefs.getString(Key.PASS, null);

        if (masv == null || pass == null) {
            Intent intent = new Intent(DiemTichLuyActivity.this, LoginWithPasswordActivity.class);
            startActivityForResult(intent, 90);
        } else {
            executeAPI(masv, pass);
        }

    }

    private void executeAPI(final String masv, final String pass) {
        DiemTichLuyAPI api = new DiemTichLuyAPI(this);

        api.setListener(new APIListener() {
            @Override
            public void onPreExecute() {
                showProgress(true);
            }

            @Override
            public void onRequestSuccess(Object object) {
                showProgress(false);

                String result = (String) object;
                try {
                    parseDataHTML(result);
                    saveLoginStatus(masv, pass);
                } catch (Exception e) {
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_button);
                    linearLayout.setVisibility(View.GONE);
                    e.printStackTrace();
                    AlertDialog.showAlertWithAction(getString(R.string.some_error), DiemTichLuyActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }

            @Override
            public void onRequestFailure(String message, int errorCode) {
                showProgress(false);
                AlertDialog.showAlertWithAction(message, DiemTichLuyActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        api.execute(masv, pass);
    }


    private void loadDataToUI() {
        TableRow.LayoutParams paramTilte = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams paramRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        paramTilte.setMargins(getPixelFromDPI(1), 0, 0, 0);

        for (int i = 0; i < lstMarkOfSemester.size(); i++) {
            TableRow tabRow = new TableRow(this);

            TextView tv_title = new TextView(this);
            tv_title.setText("\t" + lstMarkOfSemester.get(i).getTitleOfSemester());
            tv_title.setTextColor(getResources().getColor(R.color.black));
            tv_title.setTypeface(null, Typeface.BOLD);
            tv_title.setBackgroundColor(getResources().getColor(R.color.colorGrayTransparent));
            tv_title.setLayoutParams(paramTilte);
            tabRow.addView(tv_title);

            tabRow.setLayoutParams(paramRow);
            tabRow.setBackgroundColor(getResources().getColor(R.color.black));
            tabRow.setPadding(0, 0, getPixelFromDPI(1), getPixelFromDPI(1));
            tableLayout.addView(tabRow);


            ArrayList<MarkOfSubject> lstMarkSubject = lstMarkOfSemester.get(i).getLstMark();
            for (int j = 0; j < lstMarkSubject.size(); j++) {
                RowDiemTichLuy rowDiemTichLuy = new RowDiemTichLuy(this);
                MarkOfSubject mark = lstMarkSubject.get(j);
                rowDiemTichLuy.setData(mark);
                tableLayout.addView(rowDiemTichLuy);
            }

            if (i < lstMarkOfSemester.size()) {
                MarkOfSemester tichLuy = lstMarkOfSemester.get(i);
                ArrayList<String> value = tichLuy.getDataOfMark();
                for (int j = 0; j < value.size(); j++) {
                    TableRow tabRow2 = new TableRow(this);

                    TextView textView = new TextView(this);
                    textView.setText("\t" + value.get(j));
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setBackgroundColor(getResources().getColor(R.color.white));
                    textView.setLayoutParams(paramTilte);

                    tabRow2.addView(textView);

                    tabRow2.setLayoutParams(paramRow);
                    tabRow2.setBackgroundColor(getResources().getColor(R.color.black));
                    tabRow2.setPadding(0, 0, getPixelFromDPI(1), getPixelFromDPI(1));
                    tableLayout.addView(tabRow2);
                }
            }

        }

        tableLayout.setVisibility(View.VISIBLE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_button);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void parseDataHTML(String result) {
        Document doc = Jsoup.parse(result);
        Element full = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_div1");

        Elements tbody = full.select("table").select("table").select("tbody").select("tr");

        ArrayList<String> lstDataTichLuy = new ArrayList<>();
        int index = 0;
        MarkOfSemester markOfSemester = null;
        for (int i = 0; i < tbody.size(); i++) {
            Element tr = tbody.get(i);

            if (tr.attr("class").equals("title-hk-diem")) {
                for (int j = 0; j < tr.children().size(); j++) {
                    Element td = tr.child(j);
                    for (int k = 0; k < td.children().size(); k++) {
                        Element span = td.child(k);
                        markOfSemester = new MarkOfSemester();
                        markOfSemester.setTitleOfSemester(span.text());
                        lstMarkOfSemester.add(markOfSemester);
                    }
                }
            }

            if (tr.attr("class").equals("row-diem")) {
                ArrayList<String> dataOfMark = new ArrayList<>();

                for (int j = 0; j < tr.children().size(); j++) {
                    Element td = tr.child(j);
                    for (int k = 0; k < td.children().size(); k++) {
                        Element span = td.child(0);
                        String value = span.text().trim();
                        if (value.equals(" ")) {
                            value = "0";
                        }
                        dataOfMark.add(value);
                    }

                }

                MarkOfSubject ms = new MarkOfSubject(dataOfMark);
                markOfSemester.getLstMark().add(ms);
            }

            if (tr.attr("class").equals("row-diemTK")) {
                for (int j = 0; j < tr.children().size(); j++) {
                    Element td = tr.child(j);
                    String tmp = "";
                    for (int k = 0; k < td.children().size(); k++) {
                        Element span = td.child(k);
                        String value = span.text().trim();
                        tmp += value + " ";
                    }
                    lstDataTichLuy.add(tmp.trim());
                }

                if (index == 5) {
                    if (lstDataTichLuy.size() > 0) {
                        markOfSemester.setDataOfMark(lstDataTichLuy);
                    }
                    lstDataTichLuy = new ArrayList<>();
                    index = 0;
                } else {
                    index++;
                }
            }
        }

        new PrepareDataUI().execute();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 90) {
            if (resultCode == RESULT_OK) {
                String msv = data.getStringExtra("msv");
                String pass = data.getStringExtra("pass");
                executeAPI(msv, pass);
            }
        }
    }

    private int getPixelFromDPI(int dp) {
        float d = this.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dp * d); // margin in pixels
        return pxValue;
    }

    private void saveLoginStatus(String masv, String pass) {
        SharedPreferences.Editor editor = getSharedPreferences(Key.FILE_LOGIN, MODE_PRIVATE).edit();
        editor.putString(Key.MASV, masv);
        editor.putString(Key.PASS, pass);
        editor.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("markofsemester", lstMarkOfSemester);
    }


    void clearView() {
        while (tableLayout.getChildCount() > 1) {
            tableLayout.removeViewAt(1);
        }

    }

    private class PrepareDataUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
            clearView();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadDataToUI();
            showProgress(false);
        }
    }

}
