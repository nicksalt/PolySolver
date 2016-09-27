package com.nicholassalt.polysolver;

/**
 * Created by Nick on 2016-09-26.
 */
public class LinearEquation {

    private double m;
    private double b;

    public LinearEquation(double m, double b){
        this.m = m;
        this.b = b;
    }

    public double getRoot(){
        return Math.round(-b/m * 10000.0)/ 10000.0;
    }
}
