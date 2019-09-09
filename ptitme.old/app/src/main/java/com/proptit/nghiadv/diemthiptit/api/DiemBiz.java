package com.proptit.nghiadv.diemthiptit.api;

import android.content.Context;

import com.proptit.nghiadv.diemthiptit.model.MarkOfStudent;
import com.proptit.nghiadv.diemthiptit.model.MarkOfSubject;
import com.proptit.nghiadv.diemthiptit.model.Student;
import com.proptit.nghiadv.diemthiptit.common.GlobalURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nghia on 7/5/2017.
 */

public class DiemBiz extends BaseAPI {

    public DiemBiz(Context context) {
        super(context);
    }

    @Override
    public Object parseObject(JSONObject jsonObject) {
        MarkOfStudent markOfStudent = new MarkOfStudent();
        try {
            if (jsonObject.has("StudentCode")) {
                markOfStudent.setStudentCode(jsonObject.getString("StudentCode"));
            }

            if (jsonObject.has("FirstName")) {
                markOfStudent.setFirstName(jsonObject.getString("FirstName"));
            }

            if (jsonObject.has("LastName")) {
                markOfStudent.setLastName(jsonObject.getString("LastName"));
            }

            if(jsonObject.has("avg10")){
                markOfStudent.setAvg10(jsonObject.getDouble("avg10"));
            }

            if(jsonObject.has("avg4")){
                markOfStudent.setAvg4(jsonObject.getDouble("avg4"));
            }

            if (jsonObject.has("lstMark")) {
                JSONArray lstMark = jsonObject.getJSONArray("lstMark");
                for (int i = 0; i < lstMark.length(); i++) {
                    JSONObject mark = lstMark.getJSONObject(i);
                    MarkOfSubject markOfSubject = new MarkOfSubject();
                    if (mark.has("TenMon")) {
                        markOfSubject.setTenMon(mark.getString("TenMon"));
                    }
                    if (mark.has("DiemCC")) {
                        markOfSubject.setDiemCC((float) mark.getDouble("DiemCC"));
                    }
                    if (mark.has("DiemBTL")) {
                        markOfSubject.setDiemBTL((float) mark.getDouble("DiemBTL"));
                    }
                    if (mark.has("DiemTH")) {
                        markOfSubject.setDiemTH((float) mark.getDouble("DiemTH"));
                    }
                    if (mark.has("DiemThi")) {
                        markOfSubject.setDiemThi((float) mark.getDouble("DiemThi"));
                    }
                    if (mark.has("DiemKTHP")) {
                        markOfSubject.setDiemKTHP((float) mark.getDouble("DiemKTHP"));
                    }

                    if (mark.has("DiemChu")) {
                        markOfSubject.setDiemChu((mark.getString("DiemChu")));
                    }
                    if (mark.has("XepLoai")) {
                        markOfSubject.setXepLoai((mark.getString("XepLoai")));
                    }
                    if (mark.has("SoTinChi")) {
                        markOfSubject.setSoTinChi((mark.getInt("SoTinChi")));
                    }

                    markOfStudent.addMark(markOfSubject);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return markOfStudent;
    }

    @Override
    public void execute(Object... data) {
        try {
            String url = GlobalURL.URL_DIEM;
            Student student = (Student) data[0];
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Code", student.getStudentCode());
            requestPost(url, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
