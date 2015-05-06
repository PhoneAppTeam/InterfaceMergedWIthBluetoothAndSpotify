package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bart on 4/7/2015.
 */
public class StatsActivity extends MainActivity{

    Button profilePage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        profilePage = (Button)findViewById(R.id.profileButton);
        profilePage.setOnClickListener(listen1);
    }

    View.OnClickListener listen1 = new View.OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(StatsActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    };


}