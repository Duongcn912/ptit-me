package com.proptit.nghiadv.diemthiptit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.model.Week;

import java.util.ArrayList;


/**
 * Created by nghia on 7/14/2017.
 */

public class WeekSupport extends SQLController {
    private String TAG = "DB";

    public WeekSupport(Context con) {
        super(con);
    }

    public void insertDataTimeTable(ArrayList<Week> lstWeek) {
        deleteAllRowTable("tuan");
        deleteAllRowTable("tiethoc");

        insertWeekData(lstWeek);
        openDataBase();
        for (Week week : lstWeek) {
            for (Lesson lesson : week.getLstLesson()) {
                boolean result = insertLesson(lesson);
                if(result == false){
                    Log.d(TAG, "insertDataTimeTable: " + week.getDuration() + " " + lesson.getMaLop());
                }
            }
        }
        close();
    }

    public boolean insertWeekData(ArrayList<Week> lstWeek) {
        String endDay, details;
        boolean result = false;
        try {
            openDataBase();
            for (Week week : lstWeek) {
                endDay = week.getEndDate();
                details = week.getDuration();
                ContentValues values = new ContentValues();
                values.put("ngay_ket_thuc", endDay);
                values.put("detail", details);
                long rs = database.insert("tuan", null, values);
                if (rs < 0) result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public ArrayList<Week> getAllWeek() {
        ArrayList<Week> lstWeek = getEndDayOfWeek();
        for (int i = 0; i < lstWeek.size(); i++) {
            ArrayList<Lesson> lstLessonOfWeek = getLessonWeek(i + 1);
            lstWeek.get(i).setLstLesson(lstLessonOfWeek);
        }
        return lstWeek;
    }

    public ArrayList<Lesson> getLessonWeek(int week) {
        String query = "SELECT * FROM tiethoc WHERE tuan = " + week + ";";
        ArrayList<Lesson> listLession = new ArrayList<>();
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);
            Lesson tietHoc;
            while (cs.moveToNext()) {
                tietHoc = new Lesson();
                tietHoc.setMaLop(cs.getString(cs.getColumnIndex("ma_lop")));
                tietHoc.setTenMon(cs.getString(cs.getColumnIndex("ten_mon")));
                tietHoc.setMaMon(cs.getString(cs.getColumnIndex("ma_mon")));
                tietHoc.setThu(cs.getInt(cs.getColumnIndex("thu")));
                tietHoc.setSoTC(cs.getInt(cs.getColumnIndex("so_tc")));
                tietHoc.setPhongHoc(cs.getString(cs.getColumnIndex("phonghoc")));
                tietHoc.setTietBD(cs.getInt(cs.getColumnIndex("tiet_bd")));
                tietHoc.setSoTiet(cs.getInt(cs.getColumnIndex("so_tiet")));
                tietHoc.setGiangVien(cs.getString(cs.getColumnIndex("giang_vien")));
                tietHoc.setTuanBD(cs.getString(cs.getColumnIndex("tuan_bd")));
                tietHoc.setTuanKT(cs.getString(cs.getColumnIndex("tuan_kt")));
                tietHoc.setLop(cs.getString(cs.getColumnIndex("lop")));
                tietHoc.setTuan(cs.getInt(cs.getColumnIndex("tuan")));
                listLession.add(tietHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listLession;
    }


    public ArrayList<Week> getEndDayOfWeek() {
        ArrayList<Week> tuanHoc = new ArrayList<>();
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery("SELECT * from tuan", null);
            String endDay = "";
            while (cs.moveToNext()) {
                Week week = new Week();
                week.setEndDate(cs.getString(cs.getColumnIndex("ngay_ket_thuc")));
                week.setDuration(cs.getString(cs.getColumnIndex("detail")));
                tuanHoc.add(week);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return tuanHoc;
    }


    public ArrayList<String> getDetails() {
        ArrayList<String> details = new ArrayList<>();
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery("SELECT * from tuan", null);
            String endDay = "";
            while (cs.moveToNext()) {
                endDay = cs.getString(cs.getColumnIndex("detail"));
                details.add(endDay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return details;
    }




}
