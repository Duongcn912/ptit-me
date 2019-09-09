package com.proptit.nghiadv.diemthiptit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by NghiaDV on 19/06/2017.
 */

public class MarkOfSubject implements Parcelable {

    public static final Creator<MarkOfSubject> CREATOR = new Creator<MarkOfSubject>() {
        @Override
        public MarkOfSubject createFromParcel(Parcel in) {
            return new MarkOfSubject(in);
        }

        @Override
        public MarkOfSubject[] newArray(int size) {
            return new MarkOfSubject[size];
        }
    };


    private String tenMon;
    private float diemCC, diemTBKT, diemTH, diemBTL, diemThi, diemKTHP;
    private String diemChu, xepLoai, maMon;

    private int trongSoCC, trongSoTBKC, trongSoBTL, trongSoTH, trongSoThi;
    private int soTinChi, soTT;

    public MarkOfSubject() {

    }

    public MarkOfSubject(ArrayList<String> data) {
        soTT = Integer.parseInt(data.get(0));
        maMon = data.get(1);
        tenMon = data.get(2);
        soTinChi = Integer.parseInt(data.get(3));

        trongSoCC = Integer.parseInt(data.get(4));
        trongSoTBKC = Integer.parseInt(data.get(5));
        trongSoTH = Integer.parseInt(data.get(6));
        trongSoBTL = Integer.parseInt(data.get(7));
        trongSoThi = Integer.parseInt(data.get(8));

        diemCC = Float.parseFloat(data.get(9));
        diemTBKT = Float.parseFloat(data.get(10));
        diemTH = Float.parseFloat(data.get(11));
        diemBTL = Float.parseFloat(data.get(12));
        try {
            diemThi = Float.parseFloat(data.get(13));
        } catch (Exception e) {
            diemThi = 0;
        }
        diemKTHP = Float.parseFloat(data.get(15));
        diemChu = data.get(16);

    }

    public MarkOfSubject(Parcel in) {
        soTT = in.readInt();
        maMon = in.readString();
        tenMon = in.readString();
        soTinChi = in.readInt();
        trongSoCC = in.readInt();
        trongSoTBKC = in.readInt();
        trongSoTH = in.readInt();
        trongSoBTL = in.readInt();
        trongSoThi = in.readInt();
        diemCC = in.readFloat();
        diemTBKT = in.readFloat();
        diemTH = in.readFloat();
        diemBTL = in.readFloat();
        diemThi = in.readFloat();
        diemKTHP = in.readFloat();
        diemChu = in.readString();
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public float getDiemCC() {
        return diemCC;
    }

    public void setDiemCC(float diemCC) {
        this.diemCC = diemCC;
    }

    public float getDiemTBKT() {
        return diemTBKT;
    }

    public void setDiemTBKT(float diemTBKT) {
        this.diemTBKT = diemTBKT;
    }

    public float getDiemTH() {
        return diemTH;
    }

    public void setDiemTH(float diemTH) {
        this.diemTH = diemTH;
    }

    public float getDiemBTL() {
        return diemBTL;
    }

    public void setDiemBTL(float diemBTL) {
        this.diemBTL = diemBTL;
    }

    public float getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(float diemThi) {
        this.diemThi = diemThi;
    }

    public float getDiemKTHP() {
        return diemKTHP;
    }

    public void setDiemKTHP(float diemKTHP) {
        this.diemKTHP = diemKTHP;
    }

    public String getDiemChu() {
        return diemChu;
    }

    public void setDiemChu(String diemChu) {
        this.diemChu = diemChu;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(soTT);
        dest.writeString(maMon);
        dest.writeString(tenMon);
        dest.writeInt(soTinChi);

        dest.writeInt(trongSoCC);
        dest.writeInt(trongSoTBKC);
        dest.writeInt(trongSoTH);
        dest.writeInt(trongSoBTL);
        dest.writeInt(trongSoThi);

        dest.writeFloat(diemCC);
        dest.writeFloat(diemTBKT);
        dest.writeFloat(diemTH);
        dest.writeFloat(diemBTL);
        dest.writeFloat(diemThi);
        dest.writeFloat(diemKTHP);
        dest.writeString(diemChu);
    }

    public int getSoTT() {
        return soTT;
    }

    public void setSoTT(int soTT) {
        this.soTT = soTT;
    }
}
