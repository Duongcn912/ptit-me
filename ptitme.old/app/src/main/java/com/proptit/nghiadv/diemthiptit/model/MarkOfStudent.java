package com.proptit.nghiadv.diemthiptit.model;

/**
 * Created by nghia on 7/24/2017.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * @author nghia
 */

public class MarkOfStudent implements Parcelable {

    private String studentCode;
    private String firstName;
    private String lastName;
    private ArrayList<MarkOfSubject> lstMark;
    private double avg4, avg10;


    public MarkOfStudent() {
        lstMark = new ArrayList<>();
    }

    protected MarkOfStudent(Parcel in) {
        studentCode = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        lstMark = in.createTypedArrayList(MarkOfSubject.CREATOR);
    }

    public static final Creator<MarkOfStudent> CREATOR = new Creator<MarkOfStudent>() {
        @Override
        public MarkOfStudent createFromParcel(Parcel in) {
            return new MarkOfStudent(in);
        }

        @Override
        public MarkOfStudent[] newArray(int size) {
            return new MarkOfStudent[size];
        }
    };

    public void addMark(MarkOfSubject markOfSubject) {
        lstMark.add(markOfSubject);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(studentCode);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeTypedList(lstMark);
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<MarkOfSubject> getLstMark() {
        return lstMark;
    }

    public void setLstMark(ArrayList<MarkOfSubject> lstMark) {
        this.lstMark = lstMark;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public int lstMarkSize() {
        return lstMark.size();
    }

    public double getAvg4() {
        return avg4;
    }

    public void setAvg4(double avg4) {
        this.avg4 = avg4;
    }

    public double getAvg10() {
        return avg10;
    }

    public void setAvg10(double avg10) {
        this.avg10 = avg10;
    }

    public static Creator<MarkOfStudent> getCREATOR() {
        return CREATOR;
    }
}

