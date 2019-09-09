package com.proptit.nghiadv.diemthiptit.model;

/**
 * Created by nghia on 7/24/2017.
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MarkOfSemester implements Parcelable {

    public static final Creator<MarkOfSemester> CREATOR = new Creator<MarkOfSemester>() {
        @Override
        public MarkOfSemester createFromParcel(Parcel in) {
            return new MarkOfSemester(in);
        }

        @Override
        public MarkOfSemester[] newArray(int size) {
            return new MarkOfSemester[size];
        }
    };


    String arg10, arg4;
    String argky10, argky4;
    String sotc_pass, sotc_tichluy;
    ArrayList<String> dataOfMark;
    ArrayList<MarkOfSubject> lstMark;
    int semester;

    String titleOfSemester;

    public MarkOfSemester() {
        lstMark = new ArrayList<>();
        dataOfMark = new ArrayList<>();
    }

    protected MarkOfSemester(Parcel in) {
        arg10 = in.readString();
        arg4 = in.readString();
        argky10 = in.readString();
        argky4 = in.readString();
        sotc_pass = in.readString();
        sotc_tichluy = in.readString();
        dataOfMark = in.createStringArrayList();
    }

    public String getTitleOfSemester() {
        return titleOfSemester;
    }

    public void setTitleOfSemester(String titleOfSemester) {
        this.titleOfSemester = titleOfSemester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public ArrayList<MarkOfSubject> getLstMark() {
        return lstMark;
    }

    public void setLstMark(ArrayList<MarkOfSubject> lstMark) {
        this.lstMark = lstMark;
    }

    public String getArg10() {
        return arg10;
    }

    public void setArg10(String arg10) {
        this.arg10 = arg10;
    }

    public String getArg4() {
        return arg4;
    }

    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }

    public String getArgky10() {
        return argky10;
    }

    public void setArgky10(String argky10) {
        this.argky10 = argky10;
    }

    public String getArgky4() {
        return argky4;
    }

    public void setArgky4(String argky4) {
        this.argky4 = argky4;
    }

    public String getSotc_pass() {
        return sotc_pass;
    }

    public void setSotc_pass(String sotc_pass) {
        this.sotc_pass = sotc_pass;
    }

    public String getSotc_tichluy() {
        return sotc_tichluy;
    }

    public void setSotc_tichluy(String sotc_tichluy) {
        this.sotc_tichluy = sotc_tichluy;
    }

    public ArrayList<String> getDataOfMark() {
        return dataOfMark;
    }

    public void setDataOfMark(ArrayList<String> dataOfMark) {
        this.dataOfMark = dataOfMark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(arg10);
        dest.writeString(arg4);
        dest.writeString(argky10);
        dest.writeString(argky4);
        dest.writeString(sotc_pass);
        dest.writeString(sotc_tichluy);
        dest.writeStringList(dataOfMark);
    }
}
