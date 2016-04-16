package com.nicholassalt.polysolver;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * Created by Nick on 2016-02-18.
 */
public class CubicFragment extends Fragment {

    public CubicFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View cubeView = inflater.inflate(R.layout.fragment_cubic, container, false);
        TextView equation = (TextView) cubeView.findViewById(R.id.cubic_format);
        final TextView root = (TextView) cubeView.findViewById(R.id.cubic_root);
        Button enter = (Button) cubeView.findViewById(R.id.cubic_enter);
        final EditText aInput = (EditText) cubeView.findViewById(R.id.cubic_a_text);
        final EditText bInput = (EditText) cubeView.findViewById(R.id.cubic_b_text);
        final EditText cInput = (EditText) cubeView.findViewById(R.id.cubic_c_text);
        final EditText dInput = (EditText) cubeView.findViewById(R.id.cubic_d_text);
        equation.setText(Html.fromHtml("ax" + "<small>" + "<sup>" + 3 +
                "</sup>" + "</small>" +" + bx" + "<small>" + "<sup>" + 2 + "</sup>" + "</small>" +
                " + cx + d = 0"));
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(getActivity());
                root.setText(enteredPressed(aInput, bInput, cInput, dInput));
            }
        });
        return cubeView;
    }

    private String enteredPressed(EditText aInput, EditText bInput, EditText cInput, EditText dInput){
        Double a;
        Double b;
        Double c;
        Double d;
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
            return quadraticSolve(a,b,c);
        }
        else{
            d = Double.valueOf(dInput.getText().toString());
        }
        Double f = ((3 * c / a) - (Math.pow(b, 2) / Math.pow(a, 2)))/3;
        Double g = ((2 * Math.pow(b, 3) / Math.pow(a, 3)) - (9 * b * c / Math.pow(a, 2)) + (27 * d / a)) / 27;
        Double h = (Math.pow(g, 2) / 4) + (Math.pow(f.doubleValue(),3)/27);


        if (h>0){
            Log.d("TESTING", "H>0");
            ans = oneRoot(g,h,a,b);
        }
        else if (h==0  && f==0 && g==0){
            ans = threeEqualRoots(a,d);
        }
        else {
            ans = threeRoots(g,h,a,b);
            Log.d("TESTING", "H<=0");
    }
        return ans;
    }

    private String oneRoot(Double g, Double h, Double a, Double b) {
        Double R = -(g / 2) + Math.sqrt(h);
        Double S = Math.cbrt(R);
        Double T = -(g / 2) - Math.sqrt(h);
        Double U = Math.cbrt(T);
        Double x = (S + U) - (b / (3 * a));
        Log.d("TESTING", Double.toString(x));
        Double x1 = Math.round(x * 10000.0) / 10000.0;
        if (S-U == 0.0) {
            return "x = 0.0 & " + Double.toString(x1);
        } else {
            return "x = " + Double.toString(x1);
        }
    }

    private String quadraticSolve ( Double a, Double b, Double c){
        if (Math.pow(b, 2) > 4*a*c){
            Double zeroRoot = 0.0;
            Double tempRoot1 = (-b-(Math.sqrt(Math.pow(b, 2)- 4*a*c)))/(2*a);
            Double tempRoot2 = (-b+(Math.sqrt(Math.pow(b, 2)- 4*a*c)))/(2*a);
            String r1 = Double.toString(Math.round(tempRoot1 * 10000.0)/ 10000.0);
            String r2 = Double.toString(Math.round(tempRoot2 * 10000.0)/ 10000.0);
            if (r1.equals(r2)){
                return  "x = 0.0 & " + r1;
            }
            else if (tempRoot1.equals(zeroRoot)){
                return "x = 0.0 & " + r2;
            }
            else if (tempRoot2.equals(zeroRoot)){
                return "x = 0.0 & " + r1;
            }
            else{
                return "x = 0.0, " + r1 + " & " + r2;
            }
        }
        return "x = 0.0";
    }
    private String threeEqualRoots(Double a, Double d){
        double x = Math.cbrt(d/a) * -1;
        Double x1 = Math.round(x * 10000.0)/10000.0;
        return "x = " + Double.toString(x1);

    }

    private String threeRoots(Double g,Double h, Double a, Double b){
        Double i = Math.sqrt((Math.pow(g, 2)/4) - h);
        Double j = Math.cbrt(i);
        Double k = Math.acos(-(g / (2 * i)));
        Double l = -j;
        Double m = Math.cos(k/3);
        Double n = Math.sqrt(3)*Math.sin(k / 3);
        Double p = -(b/(3*a));
        double x1 = (2*j) * Math.cos(k/3) -(b/(3*a));
        double x2 = l * (m+n) + p;
        double x3 = l * (m-n) + p;
        Double xR1 = Math.round(x1*10000.0)/10000.0;
        Double xR2 = Math.round(x2*10000.0)/10000.0;
        Double xR3 = Math.round(x3*10000.0)/10000.0;
        return "x = " + Double.toString(xR1) +", " + Double.toString(xR2) +
                " & " + Double.toString(xR3);



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
}



