package com.nicholassalt.polysolver;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nick on 2016-02-18.
 */
public class LinearFragment extends Fragment {

    public LinearFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View linearView = inflater.inflate(R.layout.fragment_linear, container, false);
        final TextView root = (TextView) linearView.findViewById(R.id.linear_root);
        Button enter = (Button) linearView.findViewById(R.id.linear_enter);
        final EditText mInput = (EditText) linearView.findViewById(R.id.linear_m_text);
        final EditText bInput = (EditText) linearView.findViewById(R.id.linear_b_text);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(getActivity());
                root.setText(enteredPressed(mInput, bInput));
            }
        });
        return linearView;
    }

    public String enteredPressed(EditText mInput, EditText bInput) {
        Double m;
        Double b;
        if (isEmpty(mInput)) {
            return "Please Enter an 'm' Value";
        } else {
            m = Double.valueOf(mInput.getText().toString());
        }
        if (isEmpty(bInput)) {
            return "x = 0.0";
        } else {
            b = Double.valueOf(bInput.getText().toString());
        }
        Double root = Math.round(-b/m * 10000.0)/ 10000.0;
        return "x = " + Double.toString(root);
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
