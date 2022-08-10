package com.Autopark.Auto;

import java.util.Date;

public class Rent {
    Date date;
    double rent;

    public Rent() {
    }

    public Rent(Date date, double rent) {
        this.date = date;
        this.rent = rent;
    }

    public Date getData() {
        return date;
    }

    public void setData(Date date) {
        this.date = date;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }
}
