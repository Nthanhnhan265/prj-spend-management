package com.project.spendmanagement;

public class Extense extends Transaction{
     //fields
    private String category;

    public Extense(String date, String description, double value, String category) {
        super(date, description, value);
        this.category = category;
    }


    @Override
    public String getCategory() {
        return category;
    }
}
