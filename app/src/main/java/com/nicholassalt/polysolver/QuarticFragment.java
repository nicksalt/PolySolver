package com.nicholassalt.polysolver;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/** Created by Nick on 2016-02-20.
 */
public class QuarticFragment extends Fragment {

    public QuarticFragment(){}
    double a;
    double b;
    double c;
    double d;
    double e;
    SharedPreferences myPrefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View quardicView = inflater.inflate(R.layout.fragment_quartic, container, false);
        AdView mAdView = (AdView) quardicView.findViewById(R.id.adView_quartic);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("CF6F308AE78A3AFECCB00B6092291563").build();
        mAdView.loadAd(adRequest);
        myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
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
                + " + cx" + "<small>" + "<sup>" + 2 + "</sup>" + "</small>" + " + dx + e = 0"));
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

    private String enteredPressed(EditText aInput, EditText bInput, EditText cInput, EditText dInput, EditText eInput) {
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
            d = 0.0;
        } else {
            d = Double.valueOf(dInput.getText().toString());
        }
        if (isEmpty(eInput)) {
            CubicEquation eq = new CubicEquation(a, b, c, d, myPrefs.getInt("decimal", 3));
            ArrayList<Double> roots = eq.findRoots();
            if (! roots.contains(0.0)){
                roots.add(0.0);
            }
            Collections.sort(roots);
            if (roots.size() > 1){
                for (int i = 0; i < roots.size()-1; i++) {
                    ans += String.valueOf(roots.get(i)) + ", ";
                }
            }
            return  ans + String.valueOf(roots.get(roots.size()-1));
        }
        else {
            e = Double.valueOf(eInput.getText().toString());
        }
        QuarticEquation quarticEquation = new QuarticEquation(a, b, c, d, e, myPrefs.getInt("decimal", 3));
        ArrayList<Double> roots = quarticEquation.findRealRoots();
        for (double root : roots){
            Log.d("Root", String.valueOf(root));
        }
        Collections.sort(roots);
        if (roots.size() > 0){
            for (int i = 0; i < roots.size()-1; i++) {
                ans += String.valueOf(roots.get(i)) + ", ";
            }
            return  ans + String.valueOf(roots.get(roots.size()-1));
        }
        return "No Real Roots";
    }

     private boolean isEmpty(EditText etText) {
         return etText.getText().toString().trim().length() <= 0 ||
                 Double.valueOf(etText.getText().toString()) == 0.0;
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


