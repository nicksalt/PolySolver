package com.nicholassalt.polysolver;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nick on 2016-09-26.
 */

public class CubicEquation {
    private double a;
    private double b;
    private double c;
    private double d;
   
    private double f;
    private double g;
    private double h;
   
    double decimal;

    public CubicEquation(double a, double b, double c, double d, double decimal){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.decimal = Math.pow(10, decimal);
    }
    public ArrayList<Double> findRoots(){
        f = ((3 * c / a) - (Math.pow(b, 2) / Math.pow(a, 2)))/3;
        g = ((2 * Math.pow(b, 3) / Math.pow(a, 3)) - (9 * b * c / Math.pow(a, 2)) + (27 * d / a)) / 27;
        h = (Math.pow(g, 2) / 4) + (Math.pow(f,3)/27);
        double perError = 0.0000000000000001; //This is to account for double roun
        if (h>perError){
            return oneRoot();
        }
        else if (h==0  && f==0 && g==0){
            return threeEqualRoots();
        }
        else {
            return threeRoots();
        }
    }
    private ArrayList<Double> oneRoot() {
        Double R = -(g / 2) + Math.sqrt(h);
        Double S = Math.cbrt(R);
        Double T = -(g / 2) - Math.sqrt(h);
        Double U = Math.cbrt(T);
        Double x = (S + U) - (b / (3 * a));
        Double x1 = Math.round(x * decimal) / decimal;
        if (S-U == 0.0) {
            return new ArrayList<Double>(Arrays.asList(0.0, x1));
        } else {
            return new ArrayList<Double>(Arrays.asList(x1));
        }
    }
    private ArrayList<Double> threeEqualRoots(){
        double x = Math.cbrt(d/a) * -1;
        Double x1 = Math.round(x * decimal)/decimal;
        return new ArrayList<Double>(Arrays.asList(x1));
    }
    private ArrayList<Double> threeRoots() {
        Double i = Math.sqrt((Math.pow(g, 2) / 4) - h);
        Double j = Math.cbrt(i);
        Double k = Math.acos(-(g / (2 * i)));
        Double l = -j;
        Double m = Math.cos(k / 3);
        Double n = Math.sqrt(3) * Math.sin(k / 3);
        Double p = -(b / (3 * a));
        double x1 = Math.round(((2 * j) * Math.cos(k / 3) - (b / (3 * a))) * decimal) / decimal;
        double x2 = Math.round((l * (m + n) + p) * decimal) / decimal;
        double x3 = Math.round((l * (m - n) + p) * decimal) / decimal;
        return removeDuplicates(new ArrayList<Double>(Arrays.asList(x1, x2, x3)));
    }
    private ArrayList<Double> removeDuplicates( ArrayList<Double> roots){
        Set<Double> sortRoots = new HashSet<>();
        sortRoots.addAll(roots);
        roots.clear();
        roots.addAll(sortRoots);
        return roots;
    }
}
