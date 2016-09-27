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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Nick on 2016-02-18.
 */
public class LinearFragment extends Fragment {

    public LinearFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View linearView = inflater.inflate(R.layout.fragment_linear, container, false);
        AdView mAdView = (AdView) linearView.findViewById(R.id.adView_linear);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("CF6F308AE78A3AFECCB00B6092291563").build();
        mAdView.loadAd(adRequest);
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
        LinearEquation eq = new LinearEquation(m, b);
        return "x = " + Double.toString(eq.getRoot());
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
