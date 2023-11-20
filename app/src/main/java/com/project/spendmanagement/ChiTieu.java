package com.project.spendmanagement;

public class ChiTieu extends GiaoDich {
     //fields
    private DanhMuc danhMuc;

    public ChiTieu(String ngayGD, String ghiChu, int giaTri, DanhMuc danhMuc) {
        super(ngayGD,ghiChu,giaTri);
        this.danhMuc = danhMuc;
    }
//    public ChiTieu()
//    {
//
//    }

    @Override
    public DanhMuc getDanhMuc() {
        return danhMuc;
    }



}
