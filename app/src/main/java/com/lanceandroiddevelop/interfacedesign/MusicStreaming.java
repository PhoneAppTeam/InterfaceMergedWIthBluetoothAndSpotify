package com.lanceandroiddevelop.interfacedesign;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MusicStreaming extends ListActivity {
    private static final String MEDIA_PATH = new String("/sdcard/");
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        updateSongList();
        setContentView(R.layout.songlist);
    }
    public void updateSongList() {
        File home = new File(MEDIA_PATH);
        if(home.listFiles(new Mp3Filter())==null) {
            return;
        }
        if (home.listFiles(new Mp3Filter()).length > 0) {
            for (File file : home.listFiles(new Mp3Filter())) {
                songs.add(file.getName());
            }

            ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
            setListAdapter(songList);
        }
    }
    class Mp3Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3"));
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        playSong(MEDIA_PATH + songs.get(position));
    }

    private void playSong(String songPath) {
        try {

            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();

            // Setup listener so next song starts automatically
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer arg0) {
                    nextSong();
                }

            });

        } catch (IOException e) {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
    }
    private void nextSong() {
        if (++currentPosition >= songs.size()) {
            // Last song, just reset currentPosition
            currentPosition = 0;
        } else {
            // Play next song
            playSong(MEDIA_PATH + songs.get(currentPosition));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_streaming, menu);
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
