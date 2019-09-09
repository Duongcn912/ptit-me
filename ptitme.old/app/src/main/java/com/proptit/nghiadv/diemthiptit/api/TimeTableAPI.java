package com.proptit.nghiadv.diemthiptit.api;


import android.content.Context;
import android.os.AsyncTask;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.application.BaseApplication;
import com.proptit.nghiadv.diemthiptit.interfaces.TimeTableListener;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.model.Week;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nghia on 7/13/2017.
 */

public class TimeTableAPI extends AsyncTask<String, Integer, ArrayList<Week>> {
    TimeTableListener listener;
    OkHttpClient client;
    OkHttpClient.Builder builder;
    CookieJar cookieJar;
    Context mContext;

    public TimeTableAPI(Context context) {
        this.mContext = context;
    }

    public void setListener(TimeTableListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onPreExecute();
        }
        cookieJar = null;
        builder = new OkHttpClient.Builder();
        client = getBuilder(mContext).build();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        listener.onDoInBackGround(values[0]);
    }

    @Override
    protected ArrayList<Week> doInBackground(String... params) {
        String url = params[0];

        ArrayList<Week> lstWeek = new ArrayList<>();

        String __VIEWSTATE;
        String __VIEWSTATEGENERATOR;
        Request request;
        Response response;
        Document document;
        FormBody.Builder builderBody;
        RequestBody formBody;

        try {
            request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            document = Jsoup.parse(response.body().string());
            Element elementCapcha = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblCapcha");

            if (elementCapcha != null) {
                String capcha = elementCapcha.text();
                __VIEWSTATE = document.getElementById("__VIEWSTATE").val();
                __VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR").val();

                builderBody = new FormBody.Builder()
                        .add("ctl00$ContentPlaceHolder1$ctl00$txtCaptcha", capcha)
                        .add("__VIEWSTATE", __VIEWSTATE)
                        .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                        .add("__EVENTARGUMENT", "")
                        .add("__EVENTTARGET", "")
                        .add("ctl00$ContentPlaceHolder1$ctl00$btnXacNhan", "Vào website");

                formBody = builderBody.build();


                // request capcha
                request = new Request.Builder()
                        .post(formBody)
                        .url(url)
                        .build();

                // reponse fill capcha
                response = client.newCall(request).execute();
                String content = response.body().toString();
            }


            request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            String string_response = response.body().string();
            document = Jsoup.parse(string_response);


//            WeekSupport weekSupport = new WeekSupport(mContext);
//            weekSupport.openDataBase();
//            weekSupport.createTableWeek();
//            weekSupport.deleteAllRowTable("tuan");

             // get all header of week to request data
            Element element = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_ddlTuan");

            int currentWeek = 0;
            String value, endDay;
            for (int i = 0; i < element.children().size(); i++) {
                Week week = new Week();
                Element e = element.child(i);
                try {
                    if (e.hasAttr("selected")) {
                        currentWeek = i;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                value = e.val();
                endDay = value.substring(value.lastIndexOf(" ") + 1, value.length() - 1);

                week.setDuration(value);
                week.setEndDate(endDay);
                lstWeek.add(week);
            }

            int indexWeekRequest = 0;
            if (currentWeek == lstWeek.size() - 1) {
                indexWeekRequest = currentWeek--;
            } else {
                indexWeekRequest++;
            }

            __VIEWSTATE = document.getElementById("__VIEWSTATE").val();
            __VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR").val();

            Calendar calander = Calendar.getInstance();
            int cMonth = calander.get(Calendar.MONTH) + 1;
            int cYear = calander.get(Calendar.YEAR);

            String timeRequest = ""; // request to get current semester
            if (cMonth >= 2 && cMonth < 7) {
                timeRequest = String.valueOf(cYear - 1) + String.valueOf("2");
            } else {
                timeRequest = String.valueOf(cYear) + String.valueOf("1");
            }

            builderBody = new FormBody.Builder()
                    .add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ctl00$ddlTuan")
                    .add("__EVENTARGUMENT", "")
                    .add("__LASTFOCUS", "")
                    .add("__VIEWSTATE", __VIEWSTATE)
                    .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                    .add("ctl00$ContentPlaceHolder1$ctl00$ddlTuan", lstWeek.get(indexWeekRequest).getDuration())
                    .add("ctl00$ContentPlaceHolder1$ctl00$ddlLoai", "0")
                    .add("ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK", timeRequest);


            formBody = builderBody.build();
            request = new Request.Builder()
                    .post(formBody)
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            document = Jsoup.parse(response.body().string());

            int index = 0;
            do {
                if (index == lstWeek.size()) break;
                __VIEWSTATE = document.getElementById("__VIEWSTATE").val();
                __VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR").val();
                builderBody = new FormBody.Builder()
                        .add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ctl00$ddlTuan")
                        .add("__EVENTARGUMENT", "")
                        .add("__LASTFOCUS", "")
                        .add("__VIEWSTATE", __VIEWSTATE)
                        .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                        .add("ctl00$ContentPlaceHolder1$ctl00$ddlTuan", lstWeek.get(index).getDuration())
                        .add("ctl00$ContentPlaceHolder1$ctl00$ddlLoai", "0")
                        .add("ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK", timeRequest);

                formBody = builderBody.build();

                request = new Request.Builder()
                        .post(formBody)
                        .url(url)
                        .build();

                response = client.newCall(request).execute();
                document = Jsoup.parse(response.body().string());

                ArrayList<Lesson> listLesson = getListLesson(document, index);
                lstWeek.get(index).setLstLesson(listLesson);
                index++;
                publishProgress(index * 100 / 23);
            }
            while (index <= lstWeek.size());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lstWeek;
    }


    @Override
    protected void onPostExecute(ArrayList<Week> listDataLesson) {
        super.onPostExecute(listDataLesson);
        if (listDataLesson == null) {
            listener.onRequestFailure(BaseApplication.getmInstance().getString(R.string.some_error), 100);
        } else listener.onRequestSuccess(listDataLesson);
    }


    private ArrayList<Lesson> getListLesson(Document document, int tuan) {
        ArrayList<Lesson> lstLesson = new ArrayList<>();
        Element e = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_Table1");
        Elements tr = e.select("tbody").select("tr");
        for (int i = 0; i < tr.size(); i++) {
            Elements lstTD = tr.get(i).children();
            for (int j = 0; j < lstTD.size(); j++) {
                Element ee = lstTD.get(j);
                String value = ee.attr("onmouseover");
                if (!value.equals("")) {
                    String[] data = splitStringToArray(value);
                    Lesson lesson = new Lesson();
                    lesson.setDataOfLesson(data);
                    lesson.setThu(j + 1);
                    lesson.setTuan(tuan + 1);
                    lstLesson.add(lesson);
                }
            }
        }
        return lstLesson;
    }

    String[] splitStringToArray(String value) {
        String array[] = value.split("','");
        String result[] = new String[array.length - 1];
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                result[i] = array[0].substring(array[0].indexOf("'") + 1);
                continue;
            }
            if (i == array.length - 1) {
                continue;
            }
            result[i] = array[i];
        }

        return result;
    }

    private OkHttpClient.Builder getBuilder(Context context) {
        if (cookieJar == null) {
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
            builder.cookieJar(cookieJar);
        }
        return builder;
    }

}