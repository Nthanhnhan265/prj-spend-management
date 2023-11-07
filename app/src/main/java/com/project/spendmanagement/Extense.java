package com.project.spendmanagement;

public class Extense extends Transaction{
     //fields
    private Category category;

    public Extense(String date, String description, int value, Category category) {
        super(date, description, value);
        this.category = category;
    }


    @Override
    public Category getCategory() {
        return category;
    }

}
