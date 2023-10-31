    package com.project.spendmanagement;

    import android.graphics.drawable.Drawable;

    public class Category {
        //khai bao
        private String ten_category;
        private int icon;

        public Category() {

        }

        public Category(String ten_category, int icon) {
            this.ten_category = ten_category;
            this.icon = icon;
        }

        public String getTen_category() {
            return ten_category;
        }

        public int getIcon() {
            return icon;
        }

        public void setTen_category(String ten_category) {
            this.ten_category = ten_category;
        }

        public void setIcon(int icon) {
            this.icon = icon;


        }


    }
