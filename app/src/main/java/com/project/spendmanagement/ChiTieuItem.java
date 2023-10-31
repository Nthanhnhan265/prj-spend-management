package com.project.spendmanagement;

public class ChiTieuItem {
    private String nhapNgay;

    public void setNhapNgay(String nhapNgay) {
        this.nhapNgay = nhapNgay;
    }

    public ChiTieuItem(String nhapNgay, String nhapGhiChu, String nhapTienChi) {
        this.nhapNgay = nhapNgay;
        this.nhapGhiChu = nhapGhiChu;
        this.nhapTienChi = nhapTienChi;
    }
    public ChiTieuItem()
    {

    }

    public void setNhapGhiChu(String nhapGhiChu) {
        this.nhapGhiChu = nhapGhiChu;
    }

    public void setNhapTienChi(String nhapTienChi) {
        this.nhapTienChi = nhapTienChi;
    }

    public String getNhapNgay() {
        return nhapNgay;
    }

    public String getNhapGhiChu() {
        return nhapGhiChu;
    }

    public String getNhapTienChi() {
        return nhapTienChi;
    }

    private String nhapGhiChu;
    private String nhapTienChi;

}
