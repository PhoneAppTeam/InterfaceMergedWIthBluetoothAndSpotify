package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bart on 4/1/2015.
 */
public class OpeningActivity extends ActionBarActivity implements View.OnClickListener {
    Button moveOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openscreen);

        //Initializes all the buttons, and has the phone set to see if they get clicked
        moveOn = (Button) findViewById(R.id.continueButton);
        moveOn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
