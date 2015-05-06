package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bart on 4/1/2015.
 */
public class SettingsActivity extends MainActivity implements View.OnClickListener {
    Button about, homePage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        about = (Button)findViewById(R.id.aboutButton);
        about.setOnClickListener(this);

        homePage = (Button)findViewById(R.id.home_pageButton);
        homePage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
            case R.id.aboutButton : {
                Intent intent = new Intent(this, MusicActivity.class);
                startActivity(intent);
                finish();
            }
             **/
            case R.id.home_pageButton : {
                Intent intent = new Intent(this, SpotifyFragment.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
