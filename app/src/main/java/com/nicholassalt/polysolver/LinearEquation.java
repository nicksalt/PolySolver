package com.nicholassalt.polysolver;

import android.util.Log;

/**
 * Created by Nick on 2016-09-26.
 */
public class LinearEquation {

    private double m;
    private double b;
    double decimal;

    public LinearEquation(double m, double b, double deciaml){
        this.m = m;
        this.b = b;
        this.decimal = Math.pow(10, deciaml);
    }

    public double getRoot(){
        Log.d("Decimal", String.valueOf(decimal));
        return Math.round(-b/m * decimal)/ decimal;
    }
}
