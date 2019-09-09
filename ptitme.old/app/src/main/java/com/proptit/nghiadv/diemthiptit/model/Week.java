package com.proptit.nghiadv.diemthiptit.model;

import java.util.ArrayList;

/**
 * Created by nghia on 4/19/2018.
 */

public class Week {
    private ArrayList<Lesson> lstLesson;
    private String startDate, endDate;
    private String duration;

    public Week() {
        lstLesson = new ArrayList<>();
    }

    public ArrayList<Lesson> getLstLesson() {
        return lstLesson;
    }

    public void setLstLesson(ArrayList<Lesson> lstLesson) {
        this.lstLesson = lstLesson;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
