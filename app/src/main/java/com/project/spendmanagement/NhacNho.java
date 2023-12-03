package com.project.spendmanagement;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NhacNho {
    public NhacNho(int maNhacNho, String thoiGian, String noiDung) {
        this.maNhacNho = maNhacNho;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
    }

    // field
    private int maNhacNho;

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

    private String thoiGian;
    private String noiDung;
    // Hàm lấy thời gian theo format h,m,s
    public String getFormattedThoiGian() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(thoiGian);
    }

}
