package com.proptit.nghiadv.diemthiptit.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NghiaDV on 18/06/2017.
 */


public class Mark {

    private int id, idmon;
    private String masv, hodem, ten, ngaysinh, lop;
    private float diem_kt, diem_th, diem_btl;
    private float diem_tb, diem_ck, diem_cc;
    private String diemchu, xeploai;

    public Mark(int id, int idmon, String masv, String hodem, String ten, String ngaysinh, String lop, float diem_kt, float diem_th, float diem_btl, float diem_tb, float diem_ck, float diem_cc, String diemchu) {
        this.id = id;
        this.idmon = idmon;
        this.masv = masv;
        this.hodem = hodem;
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.lop = lop;
        this.diem_kt = diem_kt;
        this.diem_th = diem_th;
        this.diem_btl = diem_btl;
        this.diem_tb = diem_tb;
        this.diem_ck = diem_ck;
        this.diem_cc = diem_cc;
        this.diemchu = diemchu;
    }

    public Mark(){

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdmon() {
        return idmon;
    }

    public void setIdmon(int idmon) {
        this.idmon = idmon;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHodem() {
        return hodem;
    }

    public void setHodem(String hodem) {
        this.hodem = hodem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public float getDiem_kt() {
        return diem_kt;
    }

    public void setDiem_kt(float diem_kt) {
        this.diem_kt = diem_kt;
    }

    public float getDiem_th() {
        return diem_th;
    }

    public void setDiem_th(float diem_th) {
        this.diem_th = diem_th;
    }

    public float getDiem_btl() {
        return diem_btl;
    }

    public void setDiem_btl(float diem_btl) {
        this.diem_btl = diem_btl;
    }

    public float getDiem_tb() {
        return diem_tb;
    }

    public void setDiem_tb(float diem_tb) {
        this.diem_tb = diem_tb;
    }

    public float getDiem_ck() {
        return diem_ck;
    }

    public void setDiem_ck(float diem_ck) {
        this.diem_ck = diem_ck;
    }

    public float getDiem_cc() {
        return diem_cc;
    }

    public void setDiem_cc(float diem_cc) {
        this.diem_cc = diem_cc;
    }

    public String getDiemchu() {
        return diemchu;
    }

    public void setDiemchu(String diemchu) {
        this.diemchu = diemchu;
    }

    public String getXeploai() {
        return xeploai;
    }

    public void setXeploai(String xeploai) {
        this.xeploai = xeploai;
    }

    public void in() {
        System.out.println(id + " " + idmon);
        System.out.println(masv + " " + hodem + " " + ten + " " + ngaysinh + " " + lop);
        System.out.println(diem_cc + " " + diem_kt + " " + diem_th + " " + diem_btl + " " + diem_ck + " " + diem_tb);
        System.out.println(diemchu + " " + xeploai + "\n");
    }

    String change(String s) {
        return s.replace(",", ".");
    }

    public void update() {
        String query = "UPDATE bangdiem SET diem_cc = '" + diem_cc + "', diem_kt = '" + diem_kt + "', diem_th = '" + diem_th + "', diem_btl = '" + diem_btl + "', diem_ck = '" + diem_ck + "', diem_tb = '" + diem_tb + "' WHERE id = '" + id + "';";
        System.out.println(query);
    }

    public Map<String,Object> postData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("idmon", idmon);
        data.put("masv", masv);
        data.put("hodem", hodem);
        data.put("ten", ten);
        data.put("ngaysinh", ngaysinh);
        data.put("lop", lop);
        data.put("diem_cc", diem_cc);
        data.put("diem_kt", diem_kt);
        data.put("diem_th", diem_th);
        data.put("diem_btl", diem_btl);
        data.put("diem_ck", diem_ck);
        data.put("diem_tb", diem_tb);
        data.put("diemchu", diemchu);
        data.put("xeploai", xeploai);

        return data;

    }
}
