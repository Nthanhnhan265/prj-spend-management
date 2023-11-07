package com.project.spendmanagement;

public class Income extends Transaction {
        //fields
        private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //constructor
        public Income(String date, String description, int value, Category category) {
            super(date, description, value);
            this.category = category;

    }

        //get set

}