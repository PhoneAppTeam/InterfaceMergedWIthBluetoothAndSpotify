package com.lanceandroiddevelop.interfacedesign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.View.OnClickListener;

/**
 * Created by Bart on 4/1/2015.
 */
public class ProfileActivity extends MainActivity{

    Button custom, friends, stats, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        home = (Button)findViewById(R.id.home_pageButton);
        home.setOnClickListener(listen1);

        custom = (Button)findViewById(R.id.customizeButton);
        custom.setOnClickListener(listen2);

        friends = (Button)findViewById(R.id.friends_listButton);
        friends.setOnClickListener(listen3);

        stats = (Button)findViewById(R.id.statsButton);
        stats.setOnClickListener(listen4);
    }

    OnClickListener listen1 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen2 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(ProfileActivity.this, CustomizeActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen3 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(ProfileActivity.this, FriendsActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen4 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(ProfileActivity.this, StatsActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
