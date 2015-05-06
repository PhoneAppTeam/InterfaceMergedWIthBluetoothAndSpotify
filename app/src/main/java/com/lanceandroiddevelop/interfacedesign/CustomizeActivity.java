package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


/**
 * Created by Bart on 4/7/2015.
 */
public class CustomizeActivity extends MainActivity{
    Button profilepage, themes, colors, graphics;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        profilepage = (Button)findViewById(R.id.profileButton);
        profilepage.setOnClickListener(listen1);

        themes = (Button)findViewById(R.id.themeButton);
        themes.setOnClickListener(listen2);

        colors = (Button) findViewById(R.id.colorButton);
        colors.setOnClickListener(listen3);

        graphics = (Button)findViewById(R.id.graphicsButton);
        graphics.setOnClickListener(listen4);
    }

   OnClickListener listen1 = new OnClickListener() {
       public void onClick(View v){
           Intent intent = new Intent(CustomizeActivity.this, ProfileActivity.class);
           startActivity(intent);
           finish();
       }
   };

    OnClickListener listen2 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(CustomizeActivity.this, ThemesActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen3 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(CustomizeActivity.this, ColorsActivity.class);
            startActivity(intent);
            finish();
        }
    };
}

