package com.proptit.nghiadv.diemthiptit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.proptit.nghiadv.diemthiptit.model.ScheduleTest;
import com.proptit.nghiadv.diemthiptit.model.Week;

import java.util.ArrayList;

public class ScheduleTestSupport extends SQLController {

    public ScheduleTestSupport(Context context) {
        super(context);
    }

    public boolean insertScheduleTest(ArrayList<ScheduleTest> lstSchedule) {
        boolean result = true;
        deleteAllRowTable("lichthi");
        try {
            openDataBase();
            for (ScheduleTest scheduleTest : lstSchedule) {
                ContentValues values = new ContentValues();
                values.put("subjectcode", scheduleTest.getSubjectCode());
                values.put("idgroup", scheduleTest.getGroupCode());
                values.put("tothi", scheduleTest.getToThi());
                long rs = database.insert("lichthi", null, values);
                if (rs < 0) result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public ArrayList<ScheduleTest> getListScheduleTest(ArrayList<String> lstSubjectCode) {
        ArrayList<ScheduleTest> lstSchedule = new ArrayList<>();
        for (String item : lstSubjectCode) {
            ScheduleTest scheduleTest = new ScheduleTest();
            String[] lstData = item.split(" ");

            scheduleTest.setSubjectCode(lstData[0]);
            scheduleTest.setGroupCode(Integer.parseInt(lstData[2]));

            if (lstData.length > 3) {
                scheduleTest.setToThi(Integer.parseInt(lstData[6]));
            }
            lstSchedule.add(scheduleTest);
        }
        return lstSchedule;
    }

    public ArrayList<ScheduleTest> getListScheduleTestFromDatabase(){
        ArrayList<ScheduleTest> lstschedule = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.rawQuery("SELECT * from lichthi", null);
            while (cs.moveToNext()) {
                ScheduleTest schedule = new ScheduleTest();
                schedule.setSubjectCode(cs.getString(cs.getColumnIndex("subjectcode")));
                schedule.setGroupCode(cs.getInt(cs.getColumnIndex("idgroup")));
                schedule.setToThi(cs.getInt(cs.getColumnIndex("tothi")));
                lstschedule.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return lstschedule;
    }
}
