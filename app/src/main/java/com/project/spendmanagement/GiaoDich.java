package com.project.spendmanagement;

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


    public int getMaGD() {
        return maGD;
    }
    public static GiaoDich LayGiaoDichQuaMa(List<GiaoDich>data,int id){

            for (GiaoDich gd:data) {
                if(gd.getMaGD()==id) {
                    return gd;
                }
            }

         return null;
    }
    public static void setMaGD(int newMaGD) {
        maGD = newMaGD;
    }


}
