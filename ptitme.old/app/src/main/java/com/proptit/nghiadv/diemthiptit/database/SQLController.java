package com.proptit.nghiadv.diemthiptit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proptit.nghiadv.diemthiptit.model.Lesson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nghia on 7/12/2017.
 */

public class SQLController extends SQLiteOpenHelper {

    private static String DB_NAME = "data.sqlite";
    public String DB_PATH = "//data//data//%s//databases//";
    public SQLiteDatabase database;
    private Context mContext;

    public SQLController(Context con) {
        super(con, DB_NAME, null, 1);
        DB_PATH = String.format(DB_PATH, con.getPackageName());
        this.mContext = con;
    }

    public boolean isCreatedDatabase() {
        boolean result = true;
        if (!checkExistDataBase()) {
            this.getReadableDatabase();
            try {
                copyDataBase();
                result = false;
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }

        return result;
    }

    private boolean checkExistDataBase() {
        try {
            String myPath = DB_PATH + DB_NAME;
            File fileDB = new File(myPath);

            if (fileDB.exists()) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }


    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean deleteDatabase() {
        File file = new File(DB_PATH + DB_NAME);
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }


    public void openDataBase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // do nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }

    public void deleteAllRowTable(String tableName) {
        openDataBase();
        String query = "delete from " + tableName + ";";
        database.execSQL(query);
        query = "delete from sqlite_sequence where name='" + tableName + "';";
        database.execSQL(query);
        close();
    }


    public int getCountFromTable(String tableName, String fieldName) {
        String query = "SELECT COUNT(DISTINCT " + fieldName + ") AS count FROM " + tableName;
        int result = -1;

        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);


            while (cs.moveToNext()) {
                result = cs.getInt(cs.getColumnIndex("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;

    }

    public int getCurrentWeek() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDay = df.format(Calendar.getInstance().getTime());

        String query = "select id\n" +
                "from tuan \n" +
                "where \n" +
                "datetime(substr(ngay_ket_thuc, 7, 4) || '-' || substr(ngay_ket_thuc, 4, 2) || '-' || substr(ngay_ket_thuc, 1, 2)) >= \n" +
                "datetime(substr('" + currentDay + "', 7, 4) || '-' || substr('" + currentDay + "', 4, 2) || '-' || substr('" + currentDay + "', 1, 2))\n" +
                "LIMIT 1";

        int result = -1;

        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);

            while (cs.moveToNext()) {
                result = cs.getInt(cs.getColumnIndex("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;

    }

    public String getDetailCurrentWeek(int week) {
        String detail = null;
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery("SELECT * from tuan where id = " + week, null);
            while (cs.moveToNext()) {
                detail = cs.getString(cs.getColumnIndex("detail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return detail;
    }

    public boolean insertLesson(Lesson tietHoc) {
        boolean result = false;
        try {
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
        }
        return result;
    }

    public ArrayList<String> getAllListSubjectCode() {
        ArrayList<String> lstSubjectCode = new ArrayList<>();
        String query = "SELECT DISTINCT ma_mon FROM tiethoc ";

        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);
            String subjectCode = "";
            while (cs.moveToNext()) {
                subjectCode = cs.getString(cs.getColumnIndex("ma_mon"));
                lstSubjectCode.add(subjectCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return lstSubjectCode;
    }


}