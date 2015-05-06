package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bart on 4/1/2015.
 */
public class JoinActivity extends MainActivity {
    Button homepage, bluetooth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        homepage = (Button)findViewById(R.id.home_pageButton);
        homepage.setOnClickListener(listen2);

        bluetooth = (Button)findViewById(R.id.bluetoothButton);
        bluetooth.setOnClickListener(listen1);

    }

    View.OnClickListener listen1 = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(JoinActivity.this, BluetoothTesting.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener listen2 = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
