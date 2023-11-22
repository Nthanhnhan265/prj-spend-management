package com.project.spendmanagement;
import com.project.spendmanagement.GiaoDich;

import java.util.List;

public abstract class GiaoDich {
    //fields
    static int maGD=0;
    private String ngayGD;
    private String ghiChu;
    private int giaTri;
    @Override
    public String toString() {
        return "GiaoDich{" +
                "maGD="+maGD+
                "ngayGD='" + ngayGD + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", giaTri=" + giaTri +
                '}';
    }



    //construct

    public GiaoDich(String ngayGD, String ghiChu, int giaTri) {
        this.maGD++;
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

    public String getGiaTri() {
        return giaTri +"";
    }

    public void setGiaTri(int giaTri) {
        this.giaTri = giaTri;
    }

    public abstract DanhMuc getDanhMuc();
    public abstract void setDanhMuc(DanhMuc danhMuc);


    public int getMaGD() {
        return maGD;
    }

    public void setMaGD(int newMaGD) {
        maGD = newMaGD;
    }


}
