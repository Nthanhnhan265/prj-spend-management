package com.project.spendmanagement;

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

}
