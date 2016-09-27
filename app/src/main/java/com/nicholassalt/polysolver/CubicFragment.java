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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    private String enteredPressed(EditText aInput, EditText bInput, EditText cInput, EditText dInput) {
        Double a;
        Double b;
        Double c;
        Double d;
        String ans = "x = ";
        if (isEmpty(aInput)) {
            return "Please Enter an 'a' Value";
        } else {
            a = Double.valueOf(aInput.getText().toString());
        }
        if (isEmpty(bInput)) {
            b = 0.0;
        } else {
            b = Double.valueOf(bInput.getText().toString());
        }
        if (isEmpty(cInput)) {
            c = 0.0;
        } else {
            c = Double.valueOf(cInput.getText().toString());
        }
        if (isEmpty(dInput)) {
            QuadraticEquation eq = new QuadraticEquation(a, b, c);
            ArrayList<Double> roots = eq.findRealRoots();
            Collections.sort(roots);
            if (! roots.contains(0.0)){
                roots.add(0.0);
            }
            if (roots.size() > 1){
                for (int i = 0; i < roots.size()-1; i++) {
                    ans += String.valueOf(roots.get(i)) + ", ";
                }
            }
            return  ans + String.valueOf(roots.get(roots.size()-1));
        }
        else {
            d = Double.valueOf(dInput.getText().toString());
        }
        CubicEquation eq = new CubicEquation(a, b, c, d);
        ArrayList<Double> roots = eq.findRoots();
        Collections.sort(roots);
        if (roots.size() > 1){
            for (int i = 0; i < roots.size()-1; i++) {
                ans += String.valueOf(roots.get(i)) + ", ";
            }
        }
        return  ans + String.valueOf(roots.get(roots.size()-1));
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



