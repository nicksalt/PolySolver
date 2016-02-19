package com.nicholassalt.polysolver;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nick on 2016-02-16.
 */
public class QuadraticFragment extends Fragment {

    public QuadraticFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View quadView = inflater.inflate(R.layout.fragment_quadratic, container, false);
        TextView equation = (TextView) quadView.findViewById(R.id.quadratic_format);
        final TextView root = (TextView) quadView.findViewById(R.id.quadratic_root);
        Button enter = (Button) quadView.findViewById(R.id.quadratic_enter);
        final EditText aInput = (EditText) quadView.findViewById(R.id.quadratic_a_text);
        final EditText bInput = (EditText) quadView.findViewById(R.id.quadratic_b_text);
        final EditText cInput = (EditText) quadView.findViewById(R.id.quadratic_c_text);
        equation.setText(Html.fromHtml("ax" + "<small>" + "<sup>" + 2 + "</sup>" + "</small>" + " + bx + c = 0"));
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(getActivity());
                root.setText(enteredPressed(aInput, bInput, cInput));
            }
        });
        return quadView;
    }

    public String enteredPressed(EditText aInput, EditText bInput, EditText cInput){
        Double a;
        Double b;
        Double c;
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
        if (Math.pow(b, 2) < 4*a*c){
            return "No Real Roots";
        }
        else{
            Double tempRoot1 = (-b-(Math.sqrt(Math.pow(b, 2)- 4*a*c)))/(2*a);
            Double tempRoot2 = (-b+(Math.sqrt(Math.pow(b, 2)- 4*a*c)))/(2*a);
            String r1 = Double.toString(Math.round(tempRoot1 * 10000.0)/ 10000.0);
            String r2 = Double.toString(Math.round(tempRoot2 * 10000.0)/ 10000.0);
            if (r1.equals(r2)){
                ans = "x = " + r1;
            }
            else{
                ans = "x = " + r1 + " & " + r2;
            }
        }
        return ans;
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0 ||
                Double.valueOf(etText.getText().toString()) == 0.0 ;
    }

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

