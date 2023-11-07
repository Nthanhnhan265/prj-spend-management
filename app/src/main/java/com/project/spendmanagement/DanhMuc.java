    package com.project.spendmanagement;

    public class DanhMuc {
        //khai bao
        private String tenDanhMuc;
        private int icon;

        public DanhMuc() {

        }

        public DanhMuc(String tenDM, int icon) {
            this.tenDanhMuc = tenDM;
            this.icon = icon;
        }

        public String getTenDanhMuc() {
            return tenDanhMuc;
        }

        public int getIcon() {
            return icon;
        }

        public void setTenDanhMuc(String tenDanhMuc) {
            this.tenDanhMuc = tenDanhMuc;
        }

        public void setIcon(int icon) {
            this.icon = icon;


        }


    }
