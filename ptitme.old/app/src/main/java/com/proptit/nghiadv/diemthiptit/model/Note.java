package com.proptit.nghiadv.diemthiptit.model;

import java.io.Serializable;

/**
 * Created by nghia on 7/19/2017.
 */

public class Note implements Serializable {
    private int id, week, kip, day;
    private String content;

    public Note(int id, int week, int kip, int day, String content) {
        this.id = id;
        this.week = week;
        this.kip = kip;
        this.day = day;
        this.content = content;
    }

    public Note(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getKip() {
        return kip;
    }

    public void setKip(int kip) {
        this.kip = kip;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
