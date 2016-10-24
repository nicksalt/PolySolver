package com.nicholassalt.polysolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nick on 2016-09-27.
 */

class QuarticEquation {

    private static final double NEAR_ZERO = Math.pow(10, -20);

    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double decimal;

    QuarticEquation(double a, double b, double c, double d, double e, double decimal) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.decimal = Math.pow(10, decimal);
    }

    public ArrayList<Double> findRealRoots() {
        if (Math.abs(a) < NEAR_ZERO) {
            return new CubicEquation(b, c, d, e, decimal).findRoots();
        }
        if (isBiquadratic()) {
            return solveUsingBiquadraticMethod();
        }
        return solveUsingFerrariMethodWikipedia();
    }
    private ArrayList<Double> solveUsingFerrariMethodWikipedia() {
        // Using Ferrari method from wikipedia
        QuarticEquation depressedQuartic = toDepressed();
        if (depressedQuartic.isBiquadratic()) {
            ArrayList<Double> depressedRoots = depressedQuartic.solveUsingBiquadraticMethod();
            return reconvertToOriginalRoots(depressedRoots);
        }
        double y = findFerraryY(depressedQuartic);
        double originalRootConversionPart = -b / (4.0 * a);
        double firstPart = Math.sqrt(depressedQuartic.c + 2.0 * y);

        double positiveSecondPart = Math.sqrt(-(3.0 * depressedQuartic.c + 2.0 * y + 2.0 * depressedQuartic.d
                / Math.sqrt(depressedQuartic.c + 2.0 * y)));
        double negativeSecondPart = Math.sqrt(-(3.0 * depressedQuartic.c + 2.0 * y - 2.0 * depressedQuartic.d
                / Math.sqrt(depressedQuartic.c + 2.0 * y)));

        double x1 = originalRootConversionPart + (firstPart + positiveSecondPart) / 2.0;
        double x2 = originalRootConversionPart + (-firstPart + negativeSecondPart) / 2.0;
        double x3 = originalRootConversionPart + (firstPart - positiveSecondPart) / 2.0;
        double x4 = originalRootConversionPart + (-firstPart - negativeSecondPart) / 2.0;

        Set<Double> realRoots = findOnlyRealRoots(x1, x2, x3, x4);
        return toDoubleArrayList(realRoots);
    }
    private boolean isBiquadratic() {
        return Double.compare(b, 0) == 0 && Double.compare(d, 0) == 0;
    }

    private ArrayList<Double> reconvertToOriginalRoots(ArrayList<Double> depressedRoots) {
        ArrayList<Double> originalRoots = new ArrayList<>();
        for (double depressedRoot : depressedRoots) {
            originalRoots.add(depressedRoot - b / (4.0 * a));
        }
        return originalRoots;
    }

    private double findFerraryY(QuarticEquation depressedQuartic) {
        double a3 = 1.0;
        double a2 = 5.0 / 2.0 * depressedQuartic.c;
        double a1 = 2.0 * Math.pow(depressedQuartic.c, 2.0) - depressedQuartic.e;
        double a0 = Math.pow(depressedQuartic.c, 3.0) / 2.0 - depressedQuartic.c * depressedQuartic.e / 2.0
                - Math.pow(depressedQuartic.d, 2.0) / 8.0;

        CubicEquation cubicEquation = new CubicEquation(a3, a2, a1, a0, 15);
        ArrayList<Double> roots = cubicEquation.findRoots();

        for (double y : roots) {
            if (depressedQuartic.c + 2.0 * y != 0.0) {
                return y;
            }
        }
        throw new IllegalStateException("Should have at least one y");
    }

    private ArrayList<Double> solveUsingBiquadraticMethod() {
        QuadraticEquation quadraticEquation = new QuadraticEquation(a, c, e, 15);
        if (!quadraticEquation.hasRoots()) {
            return new ArrayList<Double>() {};
        }
        ArrayList<Double> quadraticRoots = quadraticEquation.findRealRoots();
        Set<Double> roots = new HashSet<>();
        for (double quadraticRoot : quadraticRoots) {
            if (quadraticRoot > 0.0) {
                roots.add((double) Math.round(Math.sqrt(quadraticRoot)*decimal)/decimal);
                roots.add((double) -Math.round(Math.sqrt(quadraticRoot)*decimal)/decimal);
            } else if (quadraticRoot == 0.00) {
                roots.add(0.00);
            }
        }
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.addAll(roots);
        return reconvertToOriginalRoots(arrayList);
    }

    private QuarticEquation toDepressed() {
        double p = (8.0 * a * c - 3.0 * Math.pow(b, 2.0)) / (8.0 * Math.pow(a, 2.0));
        double q = (Math.pow(b, 3.0) - 4.0 * a * b * c + 8.0 * d * Math.pow(a, 2.0)) / (8.0 * Math.pow(a, 3.0));
        double r = (-3.0 * Math.pow(b, 4.0) + 256.0 * e * Math.pow(a, 3.0) - 64.0 * d * b * Math.pow(a, 2.0) + 16.0 * c
                * a * Math.pow(b, 2.0))
                / (256.0 * Math.pow(a, 4.0));
        return new QuarticEquation(1.0, 0.0, p, q, r, 15);
    }


    private Set<Double> findOnlyRealRoots(double... roots) {
        Set<Double> realRoots = new HashSet<>();
        for (double root : roots) {
            if (!Double.isInfinite(root) && !Double.isNaN(root)) {
                realRoots.add(root);
            }
        }
        return realRoots;
    }


    private ArrayList<Double> toDoubleArrayList(Collection<Double> values) {
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        for (double value : values) {
            value = Math.round(value*decimal)/decimal;
            doubleArrayList.add(value);
        }
        return doubleArrayList;
    }
    private ArrayList<Double> removeDuplicates( ArrayList<Double> roots){
        Set<Double> sortRoots = new HashSet<>();
        sortRoots.addAll(roots);
        roots.clear();
        roots.addAll(sortRoots);
        return roots;
    }
}
