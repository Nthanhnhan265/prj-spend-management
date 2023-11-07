package com.project.spendmanagement;

public class ThuNhap extends GiaoDich {
        //fields
        private DanhMuc danhMuc;

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    //constructor
        public ThuNhap(String ngayGD, String ghiChu, int giaTri, DanhMuc danhMuc) {
            super(ngayGD,ghiChu,giaTri);
            this.danhMuc = danhMuc;

    }

        //get set

}