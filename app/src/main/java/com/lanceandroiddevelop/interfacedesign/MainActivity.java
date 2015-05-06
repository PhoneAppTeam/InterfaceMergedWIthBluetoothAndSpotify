package com.lanceandroiddevelop.interfacedesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    Button profile, settings, create, join;
    //Might add an extra button

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Initializes all the buttons, and has the phone set to see if they get clicked
        profile = (Button)findViewById(R.id.profileButton);
        profile.setOnClickListener(listen1);

        settings = (Button)findViewById(R.id.settingsButton);
        settings.setOnClickListener(listen2);

        create = (Button)findViewById(R.id.createButton);
        create.setOnClickListener(listen3);

        join = (Button)findViewById(R.id.joinButton);
        join.setOnClickListener(listen4);
    }

    OnClickListener listen1 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen2 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen3 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener listen4 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(intent);
            finish();
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
