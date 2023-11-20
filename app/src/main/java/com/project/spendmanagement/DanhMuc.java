    package com.project.spendmanagement;

    public class DanhMuc {
        //khai bao
        private String tenDanhMuc;
        private int icon;
        // them moi
        private int id;
        public DanhMuc() {

        }

        public DanhMuc(int id,String tenDM, int icon) {
            this.id=id;
            this.tenDanhMuc = tenDM;
            this.icon = icon;
        }
        public String getTenDanhMuc() {
            return tenDanhMuc;
        }

        public int getIcon() {
            return icon;
        }
        public int getId() {
            return id;
        }

        public void setTenDanhMuc(String tenDanhMuc) {
            this.tenDanhMuc = tenDanhMuc;
        }

        public void setIcon(int icon) {
            this.icon = icon;


        }


    }
