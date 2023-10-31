package com.project.spendmanagement;

public class Income extends Transaction {
        //fields
        private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //constructor
        public Income(String date, String description, double value, String category) {
            super(date, description, value);
            this.category = category;

    }

        //get set

}