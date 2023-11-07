package com.project.spendmanagement;

public abstract class GiaoDich {
    //fields
    String ngayGD;
    private String ghiChu;
    private int giaTri;

    //construct

    public GiaoDich(String ngayGD, String ghiChu, int giaTri) {
        this.ngayGD = ngayGD;
        this.ghiChu = ghiChu;
        this.giaTri = giaTri;
    }

    public String getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(String ngayGD) {
        this.ngayGD = ngayGD;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTextOfValue() {
        return giaTri +"";
    }

    public void setGiaTri(int giaTri) {
        this.giaTri = giaTri;
    }

    public abstract DanhMuc getDanhMuc();
}
