package com.project.spendmanagement;

public abstract class Transaction {
    //fields
    String Date;
    private String Description;
    private int Value;

    //construct

    public Transaction(String date, String description, int value) {
        Date = date;
        Description = description;
        Value = value;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTextOfValue() {
        return Value+"";
    }

    public void setValue(int value) {
        Value = value;
    }

    public abstract Category getCategory();
}
