package com.proptit.nghiadv.diemthiptit.adapter;

/**
 * Created by nghia on 7/21/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.proptit.nghiadv.diemthiptit.fragment.TimeTableFragment;
import com.proptit.nghiadv.diemthiptit.model.Lesson;
import com.proptit.nghiadv.diemthiptit.model.Week;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    Week[] lstWeek;

    public PagerAdapter(FragmentManager fm, Week[] lstWeek) {
        super(fm);
        this.lstWeek = lstWeek;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<Lesson> lessons = lstWeek[position].getLstLesson();
        TimeTableFragment fragment = new TimeTableFragment(lessons, position + 1);
        return fragment;
    }

    @Override
    public int getCount() {
        return lstWeek.length;
    }
}
