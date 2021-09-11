package com.maishapay.myshop;

public class Rating {
    double rate;
    double count;

    public Rating(double rate, double count) {
        this.rate = rate;
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
