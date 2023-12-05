package com.project.spendmanagement;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NhacNho {

    // field
    private int maNhacNho;
    private String thoiGian;
    private String noiDung;
    private String tieuDe;
    public NhacNho(int maNhacNho,String tieuDe, String thoiGian, String noiDung) {
        this.maNhacNho = maNhacNho;
        this.tieuDe = tieuDe;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return tieuDe;
    }
    public void setTieuDe(String tieuDe) {
        this.tieuDe=tieuDe;
    }
    public int getMaNhacNho() {
        return maNhacNho;
    }

    public void setMaNhacNho(int maNhacNho) {
        this.maNhacNho = maNhacNho;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    // Hàm lấy thời gian theo format h,m,s
    public String getFormattedThoiGian() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(thoiGian);
    }

    //phương thức trả về milisecond của thời gian
    public long getMilisecond() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date date=dateFormat.parse(thoiGian);
            if(date!=null) {
                return date.getTime();
            }
        }catch(Exception ex) {
            Log.d("",ex.getMessage());
        }
        return 0;
    }

}
