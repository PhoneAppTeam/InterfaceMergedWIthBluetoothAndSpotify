package com.lanceandroiddevelop.interfacedesign;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class  BlueToothActivity extends Activity {

    private Button bluetooth;
    private Button music;

    public void getBluetooth(View view) {
        bluetooth  = (Button)findViewById(R.id.button);
        Intent intent = new Intent(BlueToothActivity.this, BluetoothTesting.class);
        startActivity(intent);
    }

    public void getMusic(View view){
        music = (Button)findViewById(R.id.button4);
        Intent intent = new Intent (BlueToothActivity.this, MusicPlayer.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_main);
       // Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Quicksand-Light.otf");
        TextView myTextview = (TextView)findViewById(R.id.textView);
        Typeface myTypeface2 = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.otf");
        myTextview.setTypeface(myTypeface2);

        Button myButton = (Button)findViewById(R.id.button);
        myButton.setTypeface(myTypeface2);

        Button myButton4 = (Button)findViewById(R.id.button4);
        myButton4.setTypeface(myTypeface2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }







}



