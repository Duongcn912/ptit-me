package com.proptit.nghiadv.diemthiptit.database;

/**
 * Created by nghia on 7/12/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.proptit.nghiadv.diemthiptit.common.CommonFunction;
import com.proptit.nghiadv.diemthiptit.model.Lesson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LessonSupport extends SQLController {
    public LessonSupport(Context con) {
        super(con);
    }

    public ArrayList<Lesson>[] getAllLesson() {
        int numberWeek = getCountFromTable("tuan", "id");

        ArrayList<Lesson>[] listLession = new ArrayList[numberWeek];

        for (int i = 0; i < numberWeek; i++) {
            listLession[i] = getLessonWeek(i + 1);
        }

        return listLession;
    }


    public ArrayList<Lesson> getLessonCurrentWeek() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDay = df.format(Calendar.getInstance().getTime());

        String query = "SELECT * FROM tiethoc\n" +
                "WHERE tuan in \n" +
                "(\n" +
                "select id\n" +
                "from tuan \n" +
                "where \n" +
                "datetime(substr(ngay_ket_thuc, 7, 4) || '-' || substr(ngay_ket_thuc, 4, 2) || '-' || substr(ngay_ket_thuc, 1, 2)) >= \n" +
                "datetime(substr('" + currentDay + "', 7, 4) || '-' || substr('" + currentDay + "', 4, 2) || '-' || substr('" + currentDay + "', 1, 2))\n" +
                "LIMIT 1\n" +
                ")";

        ArrayList<Lesson> listLession = new ArrayList<>();
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);
            Lesson tietHoc;
            while (cs.moveToNext()) {
//                tietHoc = new Lesson();
//                tietHoc.setMaLop(cs.getString(cs.getColumnIndex("ma_lop")));
//                tietHoc.setTenMon(cs.getString(cs.getColumnIndex("ten_mon")));
//                tietHoc.setMaMon(cs.getString(cs.getColumnIndex("ma_mon")));
//                tietHoc.setThu(CommonFunction.getDayFromString(cs.getString(cs.getColumnIndex("thu"))));
//                tietHoc.setSoTC(cs.getString(cs.getColumnIndex("so_tc")));
//                tietHoc.setPhongHoc(cs.getString(cs.getColumnIndex("phonghoc")));
//                tietHoc.setTietBD(cs.getString(cs.getColumnIndex("tiet_bd")));
//                tietHoc.setSoTiet(cs.getString(cs.getColumnIndex("so_tiet")));
//                tietHoc.setGiangVien(cs.getString(cs.getColumnIndex("giang_vien")));
//                tietHoc.setTuanBD(cs.getString(cs.getColumnIndex("tuan_bd")));
//                tietHoc.setTuanKT(cs.getString(cs.getColumnIndex("tuan_kt")));
//                tietHoc.setLop(cs.getString(cs.getColumnIndex("lop")));
//                tietHoc.setTuan(cs.getInt(cs.getColumnIndex("tuan")));
                //listLession.add(tietHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listLession;
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
//                tietHoc = new Lesson();
//                tietHoc.setMaLop(cs.getString(cs.getColumnIndex("ma_lop")));
//                tietHoc.setTenMon(cs.getString(cs.getColumnIndex("ten_mon")));
//                tietHoc.setMaMon(cs.getString(cs.getColumnIndex("ma_mon")));
//                tietHoc.setThu(CommonFunction.getDayFromString(cs.getString(cs.getColumnIndex("thu"))));
//                tietHoc.setSoTC(cs.getString(cs.getColumnIndex("so_tc")));
//                tietHoc.setPhongHoc(cs.getString(cs.getColumnIndex("phonghoc")));
//                tietHoc.setTietBD(cs.getString(cs.getColumnIndex("tiet_bd")));
//                tietHoc.setSoTiet(cs.getString(cs.getColumnIndex("so_tiet")));
//                tietHoc.setGiangVien(cs.getString(cs.getColumnIndex("giang_vien")));
//                tietHoc.setTuanBD(cs.getString(cs.getColumnIndex("tuan_bd")));
//                tietHoc.setTuanKT(cs.getString(cs.getColumnIndex("tuan_kt")));
//                tietHoc.setLop(cs.getString(cs.getColumnIndex("lop")));
//                tietHoc.setTuan(cs.getInt(cs.getColumnIndex("tuan")));
//                listLession.add(tietHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listLession;
    }


    public boolean insertInto(Lesson tietHoc) {
        boolean result = false;
        try {

            openDataBase();
            ContentValues values = new ContentValues();

            values.put("ma_lop", tietHoc.getMaLop());
            values.put("ten_mon", tietHoc.getTenMon());
            values.put("ma_mon", tietHoc.getMaMon());
            values.put("thu", tietHoc.getThu());
            values.put("so_tc", tietHoc.getSoTC());
            values.put("phonghoc", tietHoc.getPhongHoc());
            values.put("tiet_bd", tietHoc.getTietBD());
            values.put("so_tiet", tietHoc.getSoTiet());
            values.put("giang_vien", tietHoc.getGiangVien());
            values.put("tuan_bd", tietHoc.getTuanBD());
            values.put("tuan_kt", tietHoc.getTuanKT());
            values.put("lop", tietHoc.getLop());
            values.put("tuan", tietHoc.getTuan());

            long rs = database.insert("tiethoc", null, values);
            if (rs > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }



}