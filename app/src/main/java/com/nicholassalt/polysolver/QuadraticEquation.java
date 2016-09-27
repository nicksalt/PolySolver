package com.nicholassalt.polysolver;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nick on 2016-09-26.
 */

public class QuadraticEquation {
    private double a;
    private double b;
    private double c;

    public QuadraticEquation (double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
        Log.d(String.valueOf(a), String.valueOf(c));
    }

    public ArrayList<Double> findRealRoots(){
        double check = (Math.pow(b, 2) - 4*a*c);
        if ( check > 0){
            Log.d("X1", String.valueOf(Math.round(((-b + Math.sqrt(check)) / (2*a))*10000.0)/10000.0));
            return new ArrayList<Double>(Arrays.asList(Math.round(((-b - Math.sqrt(check)) / (2*a))*10000.0)/10000.0, Math.round(((-b +
                    Math.sqrt(check)) / (2*a))*10000.0)/10000.0));
        }
        else if (check == 0) {
            Log.d("RUN", "CHECK == 0");
            return new ArrayList<Double>(Arrays.asList(Math.round(((-b + Math.sqrt(check)) / (2*a))*10000.0)/10000.0));
        }
        else{
            Log.d("RUN", "CHECK <0");
            return new ArrayList<Double>();
        }
    }
}