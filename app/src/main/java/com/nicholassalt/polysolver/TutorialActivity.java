package com.nicholassalt.polysolver;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends Activity {
    int step;
    int res;
    Button button;
    Button backButton;
    ImageView logo;
    ImageView leftArrow;
    ImageView rightArrow;
    TextView content;
    TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        res = size.x * size.y;
        step = 0;
        button = (Button) findViewById(R.id.tutorial_button);
        backButton = (Button) findViewById(R.id.tutorial_button_back);
        logo = (ImageView) findViewById(R.id.tutorial_logo);
        leftArrow = (ImageView) findViewById(R.id.arrow_left);
        rightArrow = (ImageView) findViewById(R.id.arrow_right);
        content = (TextView) findViewById(R.id.tutorial_text);
        bottomText = (TextView) findViewById(R.id.tutorial_text_bottom);
        setInitialScreen();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step == 0){
                    setSecondScreen();
                    step+=1;
                }
                else if (step == 1){
                    setThirdScreen();
                    step+=1;
                }
                else {
                    Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step==1){
                    setInitialScreen();
                    step-=1;
                }
                else {
                    setSecondScreen();
                    step-=1;
                }
            }
        });
    }
    private void setInitialScreen(){
        getActionBar().hide();
        leftArrow.setVisibility(View.GONE);
        rightArrow.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        bottomText.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
        content.setText(getResources().getString(R.string.tutorial_2));
        bottomText.setText(getResources().getString(R.string.tutorial_3));
        button.setText(getResources().getString(R.string.tutorial_8));
        backButton.setText(getResources().getString(R.string.tutorial_9));
    }
    private void setSecondScreen(){
        getActionBar().show();
        int upId = Resources.getSystem().getIdentifier("up", "id", "android");
        if (upId > 0) {
            ImageView up = (ImageView) findViewById(upId);
            up.setImageResource(R.drawable.ic_drawer);
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        content.setText(getResources().getString(R.string.tutorial_4));
        button.setText(getResources().getString(R.string.tutorial_8));
        leftArrow.setVisibility(View.VISIBLE);
        rightArrow.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        bottomText.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
    }
    private void setThirdScreen(){
        content.setText(getResources().getString(R.string.tutorial_5));
        button.setText(getResources().getString(R.string.tutorial_6));
        bottomText.setText(getResources().getString(R.string.tutorial_7));
        button.setText(getResources().getString(R.string.tutorial_10));
        bottomText.setVisibility(View.VISIBLE);
        rightArrow.setVisibility(View.VISIBLE);
        leftArrow.setVisibility(View.GONE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
