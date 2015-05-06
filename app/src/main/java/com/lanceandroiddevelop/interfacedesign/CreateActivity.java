package com.lanceandroiddevelop.interfacedesign;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by Bart on 4/1/2015.
 */
public class CreateActivity extends MainActivity {
    Button create, homePage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        create = (Button)findViewById(R.id.creationButton);
        create.setOnClickListener(listen1);

        homePage = (Button)findViewById(R.id.home_pageButton);
        homePage.setOnClickListener(listen2);
    }

    OnClickListener listen1 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(CreateActivity.this, MusicActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen2 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(CreateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}