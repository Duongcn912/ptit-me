package com.proptit.nghiadv.diemthiptit.api;

import android.content.Context;
import android.os.AsyncTask;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.application.BaseApplication;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;
import com.proptit.nghiadv.diemthiptit.interfaces.APIListener;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nghia on 7/24/2017.
 */

public class DiemTichLuyAPI extends AsyncTask<String, Integer, String> {
    APIListener listener;

    OkHttpClient client;
    OkHttpClient.Builder builder;
    CookieJar cookieJar;

    Context mContext;

    public DiemTichLuyAPI(Context context) {
        this.mContext = context;
    }


    public void setListener(APIListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cookieJar = null;
        builder = new OkHttpClient.Builder();
        client = getBuilder(mContext).build();
        listener.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        String masv = params[0];
        String pass = params[1];

        ArrayList<Lesson> lessons = new ArrayList<>();
        String str_response = "";

        try {
            Request request = new Request.Builder().url(GlobalURL.URL_GET_DIEMTICHLUY).build();
            Response response = client.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());

            Element elementCapcha = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblCapcha");
            String __VIEWSTATE;
            String __VIEWSTATEGENERATOR;
            FormBody.Builder builderBody;
            RequestBody formBody;
            __VIEWSTATE = document.getElementById("__VIEWSTATE").val();
            __VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR").val();

            if (elementCapcha != null) {
                String capcha = elementCapcha.text();
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
                        .url(GlobalURL.URL_GET_DIEMTICHLUY)
                        .build();


                // reponse fill capcha
                response = client.newCall(request).execute();

            }

            builderBody = new FormBody.Builder()
                    .add("__EVENTARGUMENT", "")
                    .add("__EVENTTARGET", "")
                    .add("__VIEWSTATE", __VIEWSTATE)
                    .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                    .add("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa", masv)
                    .add("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau", pass)
                    .add("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$btnDangNhap", "Đăng Nhập");

            formBody = builderBody.build();

            // request login
            request = new Request.Builder()
                    .post(formBody)
                    .url(GlobalURL.URL_GET_LOGIN)
                    .build();

            // reponse login
            response = client.newCall(request).execute();

            str_response = response.body().string();

            request = new Request.Builder()
                    .url(GlobalURL.URL_GET_DIEMTICHLUY)
                    .build();
            response = client.newCall(request).execute();

            document = Jsoup.parse(response.body().string());

            __VIEWSTATE = document.getElementById("__VIEWSTATE").val();
            __VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR").val();
            builderBody = new FormBody.Builder()
                    .add("__EVENTARGUMENT", "")
                    .add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ctl00$lnkChangeview2")
                    .add("__VIEWSTATE", __VIEWSTATE)
                    .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                    .add("ctl00$ContentPlaceHolder1$ctl00$txtChonHK", "");

            formBody = builderBody.build();
            request = new Request.Builder()
                    .post(formBody)
                    .url(GlobalURL.URL_GET_DIEMTICHLUY)
                    .build();

            response = client.newCall(request).execute();

            str_response = response.body().string();

        } catch (IOException e) {
            return "";
        }

        return str_response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null) {
            if (!s.equals("")) {
                listener.onRequestSuccess(s);
            } else {
                listener.onRequestFailure(BaseApplication.getmInstance().getString(R.string.some_error), 101);
            }
        }
    }

    private OkHttpClient.Builder getBuilder(Context context) {
        if (cookieJar == null) {
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
            builder.cookieJar(cookieJar);
        }
        return builder;
    }

}
