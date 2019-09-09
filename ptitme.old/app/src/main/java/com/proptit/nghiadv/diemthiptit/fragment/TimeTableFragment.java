package com.proptit.nghiadv.diemthiptit.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.activity.NoteActivity;
import com.proptit.nghiadv.diemthiptit.common.AlertDialog;
import com.proptit.nghiadv.diemthiptit.database.LessonSupport;
import com.proptit.nghiadv.diemthiptit.database.NoteSupport;
import com.proptit.nghiadv.diemthiptit.database.WeekSupport;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.model.Note;
import com.proptit.nghiadv.diemthiptit.view.DoubleTextView;

import java.util.ArrayList;

/**
 * Created by nghia on 7/21/2017.
 */

@SuppressLint("ValidFragment")
public class TimeTableFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "LOG";
    TextView textView_currentWeek;

    DoubleTextView cell[][];
    Note[][] listNote = null;
    ArrayList<Lesson> listLesson;

    int currentWeek = -2;
    Context mContext;
    WeekSupport database;
    Dialog dialog;

    public TimeTableFragment() {
        listLesson = new ArrayList<>();
    }

    @SuppressLint("ValidFragment")
    public TimeTableFragment(ArrayList<Lesson> lessons, int week) {
        this.listLesson = lessons;
        currentWeek = week;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment, container, false);
        mContext = getActivity();
        database = new WeekSupport(mContext);

        textView_currentWeek = (TextView) rootView.findViewById(R.id.id_current_week);
        textView_currentWeek.setText(mContext.getString(R.string.name_week) + " " + currentWeek);

        textView_currentWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detail = database.getDetailCurrentWeek(currentWeek);
                AlertDialog.showAlertDetailWeek(detail, mContext);
            }
        });

        initCell(rootView);

        loadCellFromList(listLesson);
        loadNoteLocal();
        return rootView;
    }

    private void initCell(View view) {
        cell = new DoubleTextView[6][7];

        cell[0][0] = (DoubleTextView) view.findViewById(R.id.k1t2);
        cell[0][1] = (DoubleTextView) view.findViewById(R.id.k1t3);
        cell[0][2] = (DoubleTextView) view.findViewById(R.id.k1t4);
        cell[0][3] = (DoubleTextView) view.findViewById(R.id.k1t5);
        cell[0][4] = (DoubleTextView) view.findViewById(R.id.k1t6);
        cell[0][5] = (DoubleTextView) view.findViewById(R.id.k1t7);
        cell[0][6] = (DoubleTextView) view.findViewById(R.id.k1t8);

        cell[1][0] = (DoubleTextView) view.findViewById(R.id.k2t2);
        cell[1][1] = (DoubleTextView) view.findViewById(R.id.k2t3);
        cell[1][2] = (DoubleTextView) view.findViewById(R.id.k2t4);
        cell[1][3] = (DoubleTextView) view.findViewById(R.id.k2t5);
        cell[1][4] = (DoubleTextView) view.findViewById(R.id.k2t6);
        cell[1][5] = (DoubleTextView) view.findViewById(R.id.k2t7);
        cell[1][6] = (DoubleTextView) view.findViewById(R.id.k2t8);

        cell[2][0] = (DoubleTextView) view.findViewById(R.id.k3t2);
        cell[2][1] = (DoubleTextView) view.findViewById(R.id.k3t3);
        cell[2][2] = (DoubleTextView) view.findViewById(R.id.k3t4);
        cell[2][3] = (DoubleTextView) view.findViewById(R.id.k3t5);
        cell[2][4] = (DoubleTextView) view.findViewById(R.id.k3t6);
        cell[2][5] = (DoubleTextView) view.findViewById(R.id.k3t7);
        cell[2][6] = (DoubleTextView) view.findViewById(R.id.k3t8);

        cell[3][0] = (DoubleTextView) view.findViewById(R.id.k4t2);
        cell[3][1] = (DoubleTextView) view.findViewById(R.id.k4t3);
        cell[3][2] = (DoubleTextView) view.findViewById(R.id.k4t4);
        cell[3][3] = (DoubleTextView) view.findViewById(R.id.k4t5);
        cell[3][4] = (DoubleTextView) view.findViewById(R.id.k4t6);
        cell[3][5] = (DoubleTextView) view.findViewById(R.id.k4t7);
        cell[3][6] = (DoubleTextView) view.findViewById(R.id.k4t8);

        cell[4][0] = (DoubleTextView) view.findViewById(R.id.k5t2);
        cell[4][1] = (DoubleTextView) view.findViewById(R.id.k5t3);
        cell[4][2] = (DoubleTextView) view.findViewById(R.id.k5t4);
        cell[4][3] = (DoubleTextView) view.findViewById(R.id.k5t5);
        cell[4][4] = (DoubleTextView) view.findViewById(R.id.k5t6);
        cell[4][5] = (DoubleTextView) view.findViewById(R.id.k5t7);
        cell[4][6] = (DoubleTextView) view.findViewById(R.id.k5t8);

        cell[5][0] = (DoubleTextView) view.findViewById(R.id.k6t2);
        cell[5][1] = (DoubleTextView) view.findViewById(R.id.k6t3);
        cell[5][2] = (DoubleTextView) view.findViewById(R.id.k6t4);
        cell[5][3] = (DoubleTextView) view.findViewById(R.id.k6t5);
        cell[5][4] = (DoubleTextView) view.findViewById(R.id.k6t6);
        cell[5][5] = (DoubleTextView) view.findViewById(R.id.k6t7);
        cell[5][6] = (DoubleTextView) view.findViewById(R.id.k6t8);


        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j].setOnClickListener(this);
                cell[i][j].setOnLongClickListener(this);
            }
        }
    }


    private void loadDataFromLocal(int week) {
        LessonSupport database = new LessonSupport(mContext);
        ArrayList<Lesson> listLesson = null;
        if (week == -1) {
            listLesson = database.getLessonCurrentWeek();
        } else {
            listLesson = database.getLessonWeek(week);
        }
        loadCellFromList(listLesson);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadNoteLocal();
    }

    private void loadNoteLocal() {
        if (currentWeek != -2) {
            NoteSupport noteSupport = new NoteSupport(mContext);

            if (currentWeek == -1) {
                listNote = noteSupport.getNoteCurrentWeek();
            } else {
                listNote = noteSupport.getNoteWeek(currentWeek);
            }
            int a = 0, b = 0;


            for (int i = 0; i < listNote.length; i++) {
                for (int j = 0; j < listNote[i].length; j++) {

                    if (!listNote[i][j].getContent().equals("")) {
                        cell[i][j].visibleIconNote(true);
                    } else {
                        cell[i][j].visibleIconNote(false);
                    }
                }
            }

        }


    }


    private void loadCellFromList(ArrayList<Lesson> listLesson) {
        clearCell();
        ArrayList<Lesson> kip[] = new ArrayList[6];
        for (int i = 0; i < kip.length; i++) {
            kip[i] = new ArrayList<>();
        }

        int index_kip, index_thu, i = 0, j = 0;
        for (Lesson lesson : listLesson) {
            index_kip = lesson.getTietBD() / 2 + 1;
            index_thu = lesson.getThu();
            try {

                i = index_kip - 1;
                j = index_thu - 2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cell[i][j].setBackgroundColor(mContext.getColor(R.color.backround_cell));
                } else
                    cell[i][j].setBackgroundColor(getResources().getColor(R.color.backround_cell));
                cell[i][j].setRoom(lesson.getPhongHoc());
                cell[i][j].setSubject(lesson.getTenMon());
            } catch (NullPointerException e) {
                System.out.println(i + " " + j);
            } catch (IndexOutOfBoundsException e) {

            }
        }

    }

    public void clearCell() {
        int i = 0, j = 0;
        try {
            for (i = 0; i < cell.length; i++) {
                for (j = 0; j < cell[i].length; j++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cell[i][j].setBackgroundColor(mContext.getColor(R.color.white));
                    } else cell[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                    cell[i][j].setRoom("");
                    cell[i][j].setSubject("");
                }
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "clearCell: " + i + " " + j);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.k1t2:
            case R.id.k1t3:
            case R.id.k1t4:
            case R.id.k1t5:
            case R.id.k1t6:
            case R.id.k1t7:
            case R.id.k1t8:
            case R.id.k2t2:
            case R.id.k2t3:
            case R.id.k2t4:
            case R.id.k2t5:
            case R.id.k2t6:
            case R.id.k2t7:
            case R.id.k2t8:
            case R.id.k3t2:
            case R.id.k3t3:
            case R.id.k3t4:
            case R.id.k3t5:
            case R.id.k3t6:
            case R.id.k3t7:
            case R.id.k3t8:
            case R.id.k4t2:
            case R.id.k4t3:
            case R.id.k4t4:
            case R.id.k4t5:
            case R.id.k4t6:
            case R.id.k4t7:
            case R.id.k4t8:
            case R.id.k5t2:
            case R.id.k5t3:
            case R.id.k5t4:
            case R.id.k5t5:
            case R.id.k5t6:
            case R.id.k5t7:
            case R.id.k5t8:
            case R.id.k6t2:
            case R.id.k6t3:
            case R.id.k6t4:
            case R.id.k6t5:
            case R.id.k6t6:
            case R.id.k6t7:
            case R.id.k6t8: {
                gotoNoteScreen(v);
            }
        }

    }

    private void gotoNoteScreen(View v) {
        int kip = -1, day = -1;
        int i, j;
        switch (v.getId()) {
            case R.id.k1t2: {
                kip = 1;
                day = 2;
                break;
            }
            case R.id.k1t3: {
                kip = 1;
                day = 3;
                break;
            }
            case R.id.k1t4: {
                kip = 1;
                day = 4;
                break;
            }
            case R.id.k1t5: {
                kip = 1;
                day = 5;
                break;
            }
            case R.id.k1t6: {
                kip = 1;
                day = 6;
                break;
            }
            case R.id.k1t7: {
                kip = 1;
                day = 7;
                break;
            }
            case R.id.k1t8: {
                kip = 1;
                day = 8;
                break;
            }
            case R.id.k2t2: {
                kip = 2;
                day = 2;
                break;
            }
            case R.id.k2t3: {
                kip = 2;
                day = 3;
                break;
            }
            case R.id.k2t4: {
                kip = 2;
                day = 4;
                break;
            }
            case R.id.k2t5: {
                kip = 2;
                day = 5;
                break;
            }
            case R.id.k2t6: {
                kip = 2;
                day = 6;
                break;
            }
            case R.id.k2t7: {
                kip = 2;
                day = 7;
                break;
            }
            case R.id.k2t8: {
                kip = 2;
                day = 8;
                break;
            }
            case R.id.k3t2: {
                kip = 3;
                day = 2;
                break;
            }
            case R.id.k3t3: {
                kip = 3;
                day = 3;
                break;
            }
            case R.id.k3t4: {
                kip = 3;
                day = 4;
                break;
            }
            case R.id.k3t5: {
                kip = 3;
                day = 5;
                break;
            }
            case R.id.k3t6: {
                kip = 3;
                day = 6;
                break;
            }
            case R.id.k3t7: {
                kip = 3;
                day = 7;
                break;
            }
            case R.id.k3t8: {
                kip = 3;
                day = 8;
                break;
            }
            case R.id.k4t2: {
                kip = 4;
                day = 2;
                break;
            }
            case R.id.k4t3: {
                kip = 4;
                day = 3;
                break;
            }
            case R.id.k4t4: {
                kip = 4;
                day = 4;
                break;
            }
            case R.id.k4t5: {
                kip = 4;
                day = 5;
                break;
            }
            case R.id.k4t6: {
                kip = 4;
                day = 6;
                break;
            }
            case R.id.k4t7: {
                kip = 4;
                day = 7;
                break;
            }
            case R.id.k4t8: {
                kip = 4;
                day = 8;
                break;
            }
            case R.id.k5t2: {
                kip = 5;
                day = 2;
                break;
            }
            case R.id.k5t3: {
                kip = 5;
                day = 3;
                break;
            }
            case R.id.k5t4: {
                kip = 5;
                day = 4;
                break;
            }
            case R.id.k5t5: {
                kip = 5;
                day = 5;
                break;
            }
            case R.id.k5t6: {
                kip = 5;
                day = 6;
                break;
            }
            case R.id.k5t7: {
                kip = 5;
                day = 7;
                break;
            }
            case R.id.k5t8: {
                kip = 5;
                day = 8;
                break;
            }
            case R.id.k6t2: {
                kip = 6;
                day = 2;
                break;
            }
            case R.id.k6t3: {
                kip = 6;
                day = 3;
                break;
            }
            case R.id.k6t4: {
                kip = 6;
                day = 4;
                break;
            }
            case R.id.k6t5: {
                kip = 6;
                day = 5;
                break;
            }
            case R.id.k6t6: {
                kip = 6;
                day = 6;
                break;
            }
            case R.id.k6t7: {
                kip = 6;
                day = 7;
                break;
            }
            case R.id.k6t8: {
                kip = 6;
                day = 8;
                break;
            }
        }

        i = kip - 1;
        j = day - 2;

        Note selected = listNote[i][j];
        Intent intent = new Intent(mContext, NoteActivity.class);
        intent.putExtra("note", selected);
        startActivity(intent);
    }


    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.k1t2:
            case R.id.k1t3:
            case R.id.k1t4:
            case R.id.k1t5:
            case R.id.k1t6:
            case R.id.k1t7:
            case R.id.k1t8:
            case R.id.k2t2:
            case R.id.k2t3:
            case R.id.k2t4:
            case R.id.k2t5:
            case R.id.k2t6:
            case R.id.k2t7:
            case R.id.k2t8:
            case R.id.k3t2:
            case R.id.k3t3:
            case R.id.k3t4:
            case R.id.k3t5:
            case R.id.k3t6:
            case R.id.k3t7:
            case R.id.k3t8:
            case R.id.k4t2:
            case R.id.k4t3:
            case R.id.k4t4:
            case R.id.k4t5:
            case R.id.k4t6:
            case R.id.k4t7:
            case R.id.k4t8:
            case R.id.k5t2:
            case R.id.k5t3:
            case R.id.k5t4:
            case R.id.k5t5:
            case R.id.k5t6:
            case R.id.k5t7:
            case R.id.k5t8:
            case R.id.k6t2:
            case R.id.k6t3:
            case R.id.k6t4:
            case R.id.k6t5:
            case R.id.k6t6:
            case R.id.k6t7:
            case R.id.k6t8: {
                gotoDetails(v);
            }
        }

        return true;
    }

    private void gotoDetails(View v) {
        int kip = -1, day = -1;
        int i, j;
        switch (v.getId()) {
            case R.id.k1t2: {
                kip = 1;
                day = 2;
                break;
            }
            case R.id.k1t3: {
                kip = 1;
                day = 3;
                break;
            }
            case R.id.k1t4: {
                kip = 1;
                day = 4;
                break;
            }
            case R.id.k1t5: {
                kip = 1;
                day = 5;
                break;
            }
            case R.id.k1t6: {
                kip = 1;
                day = 6;
                break;
            }
            case R.id.k1t7: {
                kip = 1;
                day = 7;
                break;
            }
            case R.id.k1t8: {
                kip = 1;
                day = 8;
                break;
            }
            case R.id.k2t2: {
                kip = 2;
                day = 2;
                break;
            }
            case R.id.k2t3: {
                kip = 2;
                day = 3;
                break;
            }
            case R.id.k2t4: {
                kip = 2;
                day = 4;
                break;
            }
            case R.id.k2t5: {
                kip = 2;
                day = 5;
                break;
            }
            case R.id.k2t6: {
                kip = 2;
                day = 6;
                break;
            }
            case R.id.k2t7: {
                kip = 2;
                day = 7;
                break;
            }
            case R.id.k2t8: {
                kip = 2;
                day = 8;
                break;
            }
            case R.id.k3t2: {
                kip = 3;
                day = 2;
                break;
            }
            case R.id.k3t3: {
                kip = 3;
                day = 3;
                break;
            }
            case R.id.k3t4: {
                kip = 3;
                day = 4;
                break;
            }
            case R.id.k3t5: {
                kip = 3;
                day = 5;
                break;
            }
            case R.id.k3t6: {
                kip = 3;
                day = 6;
                break;
            }
            case R.id.k3t7: {
                kip = 3;
                day = 7;
                break;
            }
            case R.id.k3t8: {
                kip = 3;
                day = 8;
                break;
            }
            case R.id.k4t2: {
                kip = 4;
                day = 2;
                break;
            }
            case R.id.k4t3: {
                kip = 4;
                day = 3;
                break;
            }
            case R.id.k4t4: {
                kip = 4;
                day = 4;
                break;
            }
            case R.id.k4t5: {
                kip = 4;
                day = 5;
                break;
            }
            case R.id.k4t6: {
                kip = 4;
                day = 6;
                break;
            }
            case R.id.k4t7: {
                kip = 4;
                day = 7;
                break;
            }
            case R.id.k4t8: {
                kip = 4;
                day = 8;
                break;
            }
            case R.id.k5t2: {
                kip = 5;
                day = 2;
                break;
            }
            case R.id.k5t3: {
                kip = 5;
                day = 3;
                break;
            }
            case R.id.k5t4: {
                kip = 5;
                day = 4;
                break;
            }
            case R.id.k5t5: {
                kip = 5;
                day = 5;
                break;
            }
            case R.id.k5t6: {
                kip = 5;
                day = 6;
                break;
            }
            case R.id.k5t7: {
                kip = 5;
                day = 7;
                break;
            }
            case R.id.k5t8: {
                kip = 5;
                day = 8;
                break;
            }
            case R.id.k6t2: {
                kip = 6;
                day = 2;
                break;
            }
            case R.id.k6t3: {
                kip = 6;
                day = 3;
                break;
            }
            case R.id.k6t4: {
                kip = 6;
                day = 4;
                break;
            }
            case R.id.k6t5: {
                kip = 6;
                day = 5;
                break;
            }
            case R.id.k6t6: {
                kip = 6;
                day = 6;
                break;
            }
            case R.id.k6t7: {
                kip = 6;
                day = 7;
                break;
            }
            case R.id.k6t8: {
                kip = 6;
                day = 8;
                break;
            }
        }


        Lesson selectedLesson = null;
        for (Lesson lesson : listLesson) {
            i = lesson.getTietBD() / 2 + 1;
            j = lesson.getThu();

            if (i == kip && j == day) {
                selectedLesson = lesson;
                break;
            }
        }

        if (selectedLesson != null)
            AlertDialog.showAlertTimeTable(selectedLesson, mContext);
    }
}
