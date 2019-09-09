package com.proptit.nghiadv.diemthiptit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.proptit.nghiadv.diemthiptit.model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nghia on 7/19/2017.
 */

public class NoteSupport extends SQLController {

    public NoteSupport(Context con) {
        super(con);
    }

    public void createTable() {
        String querry = "DROP TABLE IF EXISTS \"main\".\"note\";\n" +
                "CREATE TABLE \"note\" (\n" +
                "\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\"week\"  INTEGER,\n" +
                "\"kip\"  INTEGER,\n" +
                "\"day\"  INTEGER,\n" +
                "\"content\"  TEXT\n" +
                ");\n";

        openDataBase();
        database.execSQL(querry);
        close();
    }

    public Note[][] getNoteCurrentWeek() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDay = df.format(Calendar.getInstance().getTime());

        String query = "SELECT * FROM note\n" +
                "WHERE week in \n" +
                "(\n" +
                "select id\n" +
                "from tuan \n" +
                "where \n" +
                "datetime(substr(ngay_ket_thuc, 7, 4) || '-' || substr(ngay_ket_thuc, 4, 2) || '-' || substr(ngay_ket_thuc, 1, 2)) >= \n" +
                "datetime(substr('" + currentDay + "', 7, 4) || '-' || substr('" + currentDay + "', 4, 2) || '-' || substr('" + currentDay + "', 1, 2))\n" +
                "LIMIT 1\n" +
                ")";


        Note[][] listNote = new Note[6][7];
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);
            Note currentNote;

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (cs.moveToNext()) {
                        currentNote = new Note();
                        currentNote.setId(cs.getInt(cs.getColumnIndex("id")));
                        currentNote.setDay(cs.getInt(cs.getColumnIndex("day")));
                        currentNote.setWeek(cs.getInt(cs.getColumnIndex("week")));
                        currentNote.setKip(cs.getInt(cs.getColumnIndex("kip")));
                        currentNote.setContent(cs.getString(cs.getColumnIndex("content")));

                        listNote[i][j] = currentNote;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listNote;
    }


    public Note[][] getNoteWeek(int week) {
        String query = "SELECT * FROM note WHERE week = " + week + ";";
        Note[][] listNote = new Note[6][7];
        // mo ket noi
        try {
            openDataBase();
            Cursor cs = database.rawQuery(query, null);
            Note currentNote;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (cs.moveToNext()) {
                        currentNote = new Note();
                        currentNote.setId(cs.getInt(cs.getColumnIndex("id")));
                        currentNote.setDay(cs.getInt(cs.getColumnIndex("day")));
                        currentNote.setWeek(cs.getInt(cs.getColumnIndex("week")));
                        currentNote.setKip(cs.getInt(cs.getColumnIndex("kip")));
                        currentNote.setContent(cs.getString(cs.getColumnIndex("content")));

                        listNote[i][j] = currentNote;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listNote;
    }


    public boolean insertInto(Note note) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            values.put("week", note.getWeek());
            values.put("id", note.getId());
            values.put("kip", note.getKip());
            values.put("day", note.getDay());
            values.put("content", note.getContent());
            long rs = database.insert("note", null, values);
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


    public void updateNote(Note note) {
        int id = note.getId();
        ContentValues cv = new ContentValues();
        cv.put("content", note.getContent());
        openDataBase();
        database.update("note", cv, "id = " + id, null);
        close();
    }

}
