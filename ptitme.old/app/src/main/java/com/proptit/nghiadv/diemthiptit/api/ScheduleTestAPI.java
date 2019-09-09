package com.proptit.nghiadv.diemthiptit.api;

import android.content.Context;

import com.proptit.nghiadv.diemthiptit.common.CommonFunction;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;
import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleTestAPI extends BaseAPI {
    public ScheduleTestAPI(Context context) {
        super(context);
    }

    @Override
    public Object parseObject(JSONObject jsonObject) {
        ArrayList<ScheduleTest> lstSchedule = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("lstSchedule");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ScheduleTest scheduleTest = new ScheduleTest();
                if (object.has("TestDay")) {
                    String time1 = object.getString("TestDay");
                    String time2 = CommonFunction.formatDateToString(time1);
                    scheduleTest.setTestDay(time2);
                }
                if (object.has("StartLesson")) {
                    scheduleTest.setStartLesson(object.getInt("StartLesson"));
                }
                if (object.has("StartTime")) {
                    String startTime = object.getString("StartTime");
                    startTime = startTime.replace('g',':');
                    scheduleTest.setStartTime(startTime);
                }
                if (object.has("SubjectCode")) {
                    scheduleTest.setSubjectCode(object.getString("SubjectCode"));
                }
                if (object.has("GroupCode")) {
                    scheduleTest.setGroupCode(object.getInt("GroupCode"));
                }
                if (object.has("ToThi")) {
                    scheduleTest.setToThi(object.getInt("ToThi"));
                }
                if (object.has("SubjectName")) {
                    scheduleTest.setSubjectName(object.getString("SubjectName"));
                }
                if (object.has("lstRoom")) {
                    String testRoom = "";
                    JSONArray arrayRoom = object.getJSONArray("lstRoom");
                    for (int j = 0; j < arrayRoom.length(); j++) {
                        testRoom += arrayRoom.getString(j);
                        if (j != arrayRoom.length() - 1) testRoom += ", ";
                    }
                    scheduleTest.setTestRoom(testRoom);
                }

                lstSchedule.add(scheduleTest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lstSchedule;
    }

    @Override
    public void execute(Object... data) {
        String url = GlobalURL.URL_LICHTHI;
        JSONArray dataPost = (JSONArray) data[0];
        requestPost(url, dataPost);
    }
}
