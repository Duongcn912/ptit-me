package com.proptit.nghiadv.diemthiptit.api;

import android.content.Context;
import android.util.Log;

import com.proptit.nghiadv.diemthiptit.model.Student;
import com.proptit.nghiadv.diemthiptit.common.CommonFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nghia on 6/30/2017.
 */

public class LoginBiz extends BaseAPI {
    private static final String TAG = "LOG_API";

    public LoginBiz(Context context) {
        super(context);
    }

    @Override
    public Object parseObject(JSONObject jsonObject) {
        if (jsonObject == null) return null;
        Student student = new Student();
        try {

            if (jsonObject.has("ID")) {
                student.setId(jsonObject.getInt("ID"));
            }
            if (jsonObject.has("Code")) {
                student.setStudentCode(jsonObject.getString("Code"));
            }
            if (jsonObject.has("FirstName")) {
                student.setFirstName(jsonObject.getString("FirstName"));
            }
            if (jsonObject.has("LastName")) {
                student.setLastName(jsonObject.getString("LastName"));
            }
            if (jsonObject.has("ClassName")) {
                student.setClassname(jsonObject.getString("ClassName"));
            }
            if (jsonObject.has("DOB")) {
                String dateString = jsonObject.getString("DOB");
                Date date = CommonFunction.parseStringToDate(dateString);
                student.setDateOfBirth(date);
            }

            Log.d(TAG, "parseObject: " + student.getFullName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void execute(Object... data) {
        try {
            String urlRequest = (String) data[0];
            Student student = (Student) data[1];

            JSONObject dataPost = new JSONObject();
            dataPost.put("Code", student.getStudentCode());
            requestPost(urlRequest, dataPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
