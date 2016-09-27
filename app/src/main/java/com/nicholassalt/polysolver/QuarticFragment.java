package com.nicholassalt.polysolver;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nick on 2016-02-20.
 */
public class QuarticFragment extends Fragment {

    public QuarticFragment(){}
    double a;
    double b;
    double c;
    double d;
    double e;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View quardicView = inflater.inflate(R.layout.fragment_quartic, container, false);
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        TextView equation = (TextView) quardicView.findViewById(R.id.quartic_format);
        final TextView root = (TextView) quardicView.findViewById(R.id.quartic_root);
        Button enter = (Button) quardicView.findViewById(R.id.quartic_enter);
        final EditText aInput = (EditText) quardicView.findViewById(R.id.quartic_a_text);
        final EditText bInput = (EditText) quardicView.findViewById(R.id.quartic_b_text);
        final EditText cInput = (EditText) quardicView.findViewById(R.id.quartic_c_text);
        final EditText dInput = (EditText) quardicView.findViewById(R.id.quartic_d_text);
        final EditText eInput = (EditText) quardicView.findViewById(R.id.quartic_e_text);
        setEquation(equation, dpHeight);
        equation.setText(Html.fromHtml("ax" + "<small>" + "<sup>" + 4 +
                "</sup>" + "</small>" +" + bx" + "<small>" + "<sup>" + 3 + "</sup>" + "</small>"
                + " + cx" + "<small>" + "<sup>" + 2 + "</sup>" + "</small>" + " + d + e = 0"));
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(getActivity());
                root.setText(enteredPressed(aInput, bInput, cInput, dInput, eInput));
            }
        });
        return quardicView;
    }

    private void setEquation(TextView equation, float height){
        if (height < 640){
            equation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }

        equation.setText(Html.fromHtml("ax" + "<small>" + "<sup>" + 4 +
                "</sup>" + "</small>" +" + bx" + "<small>" + "<sup>" + 3 + "</sup>" + "</small>"
                + " + cx" + "<small>" + "<sup>" + 2 + "</sup>" + "</small>" + " + d + e = 0"));
    }

    private String enteredPressed(EditText aInput, EditText bInput, EditText cInput, EditText dInput, EditText eInput){
        String ans;
        if (isEmpty(aInput)){
            return "Please Enter an 'a' Value";
        }
        else{
            a = Double.valueOf(aInput.getText().toString());
        }
        if (isEmpty(bInput)){
            b= 0.0;
        }
        else{
            b = Double.valueOf(bInput.getText().toString());
        }
        if (isEmpty(cInput)){
            c= 0.0;
        }
        else{
            c = Double.valueOf(cInput.getText().toString());
        }
        if (isEmpty(dInput)){
            d = 0.0;
        }
        else{
            d = Double.valueOf(dInput.getText().toString());
        }
        if (isEmpty(eInput)){
            e=0.0;
        }
        else {
            e = Double.valueOf(eInput.getText().toString());
        }

        ans="";
        Log.d("TESTING", Arrays.toString(test));
        return ans;
    }


    public final double[] findRealRoots(){
      if (isBiquadratic(b, d)) {
          return solveUsingBiquadraticMethod();
      }

      return solveFerrari();
    }

    private boolean isBiquadratic(double b1, double d1){
        return Double.compare(b1, 0) == 0 && Double.compare(d1, 0) == 0;
    }

    private double[] solveUsingBiquadraticMethod(double a1, double c1, double e1) {
        if ((Math.pow(c1,2)-(4*a1*e1)) < 0.0){
            return new double[]{};
        }
        else{
            double[] quadRoots = getQuadRoots(a1, c1, e1);
            Set<Double> roots = new HashSet<>();
            for (double quadRoot : quadRoots){
                if (quadRoot > 0.0) {
                    roots.add(Math.sqrt(quadRoot));
                    roots.add(-Math.sqrt(quadRoot));
                } else if (quadRoot == 0.0) {
                    roots.add(0.0);
                }
            }
            return toDoubleArray(roots);
        }
    }

    private double[] solveFerrari(double a1, double b1, double c1, double d1, double e1) {
        QuarticFunction dQuatratic = toD();
        if (isBiquadratic(dQuatratic.b, dQuatratic.d)){
            double[] dRoots = solveUsingBiquadraticMethod(dQuatratic.a, dQuatratic.c, dQuatratic.e);
            return reconvertToOriginalRoots(dRoots);
        }
        return;
    }

    private double[] reconvertToOriginalRoots(double[] depressedRoots) {
        double[] originalRoots = new double[depressedRoots.length];
        for (int i = 0; i < depressedRoots.length; ++i) {
            originalRoots[i] = depressedRoots[i] - b / (4.0 * a);
        }
        return originalRoots;
    }
    public QuarticFunction toD(){
        double p = (8 * a * c - 3 * Math.pow(b, 2)) / (8 * Math.pow(a, 2));
        double q = (Math.pow(b, 3) - 4 * a * b * c + 8 * d * Math.pow(a, 2)) / (8 * Math.pow(a, 3));
        double r = (-3 * Math.pow(b, 4) + 256 * e * Math.pow(a, 3) - 64 * d * b * Math.pow(a, 2) + 16 * c * a
                * Math.pow(b, 2)) / (256 * Math.pow(a, 4));
        return new QuarticFunction(1, 0, p, q, r);
    }
    private double[] getQuadRoots(double a1, double b1, double c1){
        double part2 = Math.sqrt(Math.pow(b, 2)-4*a1*c1);
        double r1 = -b + part2;
        double r2 = -b - part2;
        return new double[]{r1, r2};
    }

    private double[] toDoubleArray(Collection<Double> values) {
        double[] doubleArray = new double[values.size()];
        int i = 0;
        for (double value : values) {
            doubleArray[i] = value;
            ++i;
        }
        return doubleArray;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0 ||
                Double.valueOf(etText.getText().toString()) == 0.0 ;
    }

    private static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public class QuarticFunction {

        private final double a;
        private final double b;
        private final double c;
        private final double d;
        private final double e;

        public QuarticFunction(double a, double b, double c, double d, double e) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }
    }

}

