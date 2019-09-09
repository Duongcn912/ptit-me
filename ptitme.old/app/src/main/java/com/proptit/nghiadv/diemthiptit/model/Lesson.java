package com.proptit.nghiadv.diemthiptit.model;

/**
 * @author nghia
 */
public class Lesson {
    String maLop, tenMon, maMon;
    int soTC, tietBD, thu, soTiet;
    String phongHoc;
    String giangVien, tuanBD, tuanKT, unknow;
    String lop;
    int tuan;


    public Lesson() {
    }


    public Lesson(String[] data) {
//        maLop = data[0];
//        tenMon = data[1];
//        maMon = data[2];
//        thu = CommonFunction.getDayFromString(data[3]);
//        soTC = data[4];
//        phongHoc = data[5];
//        tietBD = data[6];
//        soTiet = data[7];
//        giangVien = data[8];
//        tuanBD = data[9];
//        tuanKT = data[10];
//        unknow = data[11];
//        lop = data[12];
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }

    public int getTietBD() {
        return tietBD;
    }

    public void setTietBD(int tietBD) {
        this.tietBD = tietBD;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getSoTiet() {
        return soTiet;
    }

    public void setSoTiet(int soTiet) {
        this.soTiet = soTiet;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public String getTuanBD() {
        return tuanBD;
    }

    public void setTuanBD(String tuanBD) {
        this.tuanBD = tuanBD;
    }

    public String getTuanKT() {
        return tuanKT;
    }

    public void setTuanKT(String tuanKT) {
        this.tuanKT = tuanKT;
    }

    public String getUnknow() {
        return unknow;
    }

    public void setUnknow(String unknow) {
        this.unknow = unknow;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getTuan() {
        return tuan;
    }

    public void setTuan(int tuan) {
        this.tuan = tuan;
    }

    public String genInsert() {
        String query = "INSERT INTO tiethoc(ma_lop,ten_mon,ma_mon,thu,so_tc,phonghoc,tiet_bd,so_tiet,giang_vien,tuan_bd,tuan_kt,lop,tuan) "
                + "VALUES ('" + maLop + "','" + tenMon + "','" + maMon + "'," + thu + "," + soTC + ",'" + phongHoc + "'," + tietBD + "," + soTiet + ",'" + giangVien + "','"
                + tuanBD + "','" + tuanKT + "','" + lop + "'," + tuan + ");";
        return query;
    }

    public void setDataOfLesson(String[] dataOfLesson) {
        maLop = dataOfLesson[0];
        tenMon = dataOfLesson[1];
        maMon = dataOfLesson[2];
        //thu = CommonFunction.getDayFromString(dataOfLesson[3]);
        soTC = Integer.parseInt(dataOfLesson[4]);
        phongHoc = dataOfLesson[5];
        tietBD = Integer.parseInt(dataOfLesson[6]);
        soTiet = Integer.parseInt(dataOfLesson[7]);
        giangVien = dataOfLesson[8];
        tuanBD = dataOfLesson[9];
        tuanKT = dataOfLesson[10];
        unknow = dataOfLesson[11];
        lop = dataOfLesson[12];
    }
}
