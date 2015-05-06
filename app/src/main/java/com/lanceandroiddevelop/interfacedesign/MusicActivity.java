package com.lanceandroiddevelop.interfacedesign;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Connectivity;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Author: @Khoa Tran
 */

public class MusicActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {
    //   ____                _              _
    //  / ___|___  _ __  ___| |_ __ _ _ __ | |_ ___
    // | |   / _ \| '_ \/ __| __/ _` | '_ \| __/ __|
    // | |__| (_) | | | \__ \ || (_| | | | | |_\__ \
    //  \____\___/|_| |_|___/\__\__,_|_| |_|\__|___/
    //

    private static final String CLIENT_ID = "44fdb2ec906a4276a45e7cd77a05a58e";
    private static final String REDIRECT_URI = "my-first-awesome-annoying-app://callback";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_SONG_URI = "spotify:track:6KywfgRqvgvfJc3JRwaZdZ";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_SONG_MONO_URI = "spotify:track:1FqY3uJypma5wkYw66QOUi";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_SONG_48kHz_URI = "spotify:track:3wxTNS3aqb9RbBLZgJdZgH";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_PLAYLIST_URI = "spotify:user:sqook:playlist:0BZvnsfuqmnLyj6WVRuSte";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_QUEUE_SONG_URI = "spotify:track:5EEOjaJyWvfMglmEwf9bG3";

    Button homepage;

    /**
     * Request code that will be passed together with authentication result to the onAuthenticationResult
     */
    private static final int REQUEST_CODE = 1337;

    // Currently the way to play the album is to resolve the list of tracks using
    // WebAPI (as shown here https://developer.spotify.com/web-api/get-albums-tracks/)
    // and pass it to Player#playTrackList(java.util.List)
    // The list of tracks below is from the album: spotify:album:4JWoGR0Kwa0DlqbikKNqOc
    @SuppressWarnings("SpellCheckingInspection")
    private static final List<String> TEST_ALBUM_TRACKS = Arrays.asList(
            "spotify:track:2To3PTOTGJUtRsK3nQemP4",
            "spotify:track:0tDoBMgyAzGgLhs73KPrJL",
            "spotify:track:5YkSQuB8i7J4TTyj0xw6ol",
            "spotify:track:3WpLfCkrlQxj8SISLzhs06",
            "spotify:track:2lGNTC3NKCG1d4lR8x3611",
            "spotify:track:0kdSj5REwpHjTBaBsm1wv8",
            "spotify:track:3BgnZiGnnRlXfeGR8ryKzT",
            "spotify:track:00cVWQIFyQnIdsgoVy7qAG",
            "spotify:track:6eEEoowHpnaD3q83ZhYmhZ",
            "spotify:track:1HFBn8S30ndZ7lLb9HbENU",
            "spotify:track:1I9VibKgJTqGfrh8fEK3sL",
            "spotify:track:6rXSPMgGIyOYiMhsj3eSAi",
            "spotify:track:2xwuXthwdNGbPyEqifPQNW",
            "spotify:track:5vRuWI48vKn4TV7efrYtJL",
            "spotify:track:4SEDYSBDd4Ota125LjHa2w",
            "spotify:track:2bVTnSTjLWAizyj4XcU5bf",
            "spotify:track:4gQzqlFuqv6l4Ka633Ue7T",
            "spotify:track:0SLVmM7IrrtkPNa1Fi3IKT"
    );

    /**
     * UI controls which may only be enabled after the player has been initialized,
     * (or effectively, after the user has logged in).
     */
    private static final int[] REQUIRES_INITIALIZED_STATE = {
            R.id.show_player_state_button,
            R.id.play_track_button,
            R.id.play_album_button,
            R.id.play_playlist_button,
            R.id.pause_button,
//            R.id.low_bitrate_button,
//            R.id.normal_bitrate_button,
//            R.id.high_bitrate_button
    };

    /**
     * UI controls which should only be enabled if the player is actively playing.
     */
    private static final int[] REQUIRES_PLAYING_STATE = {
            R.id.skip_next_button,
            R.id.skip_prev_button,
//            R.id.queue_song_button,
            R.id.toggle_shuffle_button,
            R.id.toggle_repeat_button,
    };

    //  _____ _      _     _
    // |  ___(_) ___| | __| |___
    // | |_  | |/ _ \ |/ _` / __|
    // |  _| | |  __/ | (_| \__ \
    // |_|   |_|\___|_|\__,_|___/
    //

    /**
     * The player used by this activity. There is only ever one instance of the player,
     * which is owned by the {@link com.spotify.sdk.android.player.Spotify} class and refcounted.
     * This means that you may use the Player from as many Fragments as you want, and be
     * assured that state remains consistent between them.
     * <p/>
     * However, each fragment, activity, or helper class <b>must</b> call
     * {@link com.spotify.sdk.android.player.Spotify#destroyPlayer(Object)} when they are no longer
     * need that player. Failing to do so will result in leaked resources.
     */
    private Player mPlayer;

    private PlayerState mCurrentPlayerState = new PlayerState();

    /**
     * Used to get notifications from the system about the current network state in order
     * to pass them along to
     * {@link com.spotify.sdk.android.player.Player#setConnectivityStatus(com.spotify.sdk.android.player.Connectivity)}.
     * Note that this implies <pre>android.permission.ACCESS_NETWORK_STATE</pre> must be
     * declared in the manifest. Not setting the correct network state in the SDK may
     * result in strange behavior.
     */
    private BroadcastReceiver mNetworkStateReceiver;

    /**
     * Used to log messages to a {@link android.widget.TextView} in this activity.
     */
    private TextView mStatusText;
    /**
     * Used to scroll the {@link #mStatusText} to the bottom after updating text.
     */
    private ScrollView mStatusTextScrollView;

    //  ___       _ _   _       _ _          _   _
    // |_ _|_ __ (_) |_(_) __ _| (_)______ _| |_(_) ___  _ __
    //  | || '_ \| | __| |/ _` | | |_  / _` | __| |/ _ \| '_ \
    //  | || | | | | |_| | (_| | | |/ / (_| | |_| | (_) | | | |
    // |___|_| |_|_|\__|_|\__,_|_|_/___\__,_|\__|_|\___/|_| |_|
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer);

        // Get a reference to any UI widgets that we'll need to use later
        mStatusText = (TextView) findViewById(R.id.status_text);
        mStatusTextScrollView = (ScrollView) findViewById(R.id.status_text_container);

        updateButtons();
        logStatus("Ready");

        homepage = (Button)findViewById(R.id.homepageButton);
        homepage.setOnClickListener(listen1);
    }

    OnClickListener listen1 = new OnClickListener() {
        public void onClick(View v){
            Intent intent = new Intent(MusicActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set up the broadcast receiver for network events. Note that we also unregister
        // this receiver again in onPause().
        mNetworkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (mPlayer != null) {
                    Connectivity connectivity = getNetworkConnectivity(getBaseContext());
                    logStatus("Network state changed: " + connectivity.toString());
                    mPlayer.setConnectivityStatus(connectivity);
                }
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, filter);

        if (mPlayer != null) {
            mPlayer.addPlayerNotificationCallback(MusicActivity.this);
            mPlayer.addConnectionStateCallback(MusicActivity.this);
        }
    }

    /**
     * Registering for connectivity changes in Android does not actually deliver them to
     * us in the delivered intent.
     *
     * @param context Android context
     * @return Connectivity state to be passed to the SDK
     * @see com.spotify.sdk.android.player.Player#setConnectivityStatus(com.spotify.sdk.android.player.Connectivity)
     */
    private Connectivity getNetworkConnectivity(Context context) {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return Connectivity.fromNetworkType(activeNetwork.getType());
        } else {
            return Connectivity.OFFLINE;
        }
    }

    //     _         _   _                _   _           _   _
    //    / \  _   _| |_| |__   ___ _ __ | |_(_) ___ __ _| |_(_) ___  _ __
    //   / _ \| | | | __| '_ \ / _ \ '_ \| __| |/ __/ _` | __| |/ _ \| '_ \
    //  / ___ \ |_| | |_| | | |  __/ | | | |_| | (_| (_| | |_| | (_) | | | |
    // /_/   \_\__,_|\__|_| |_|\___|_| |_|\__|_|\___\__,_|\__|_|\___/|_| |_|
    //

    private void openLoginWindow() {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"})
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    onAuthenticationComplete(response);
                    break;

                // Auth flow returned an error
                case ERROR:
                    logStatus("Auth error: " + response.getError());
                    break;

                // Most likely auth flow was cancelled
                default:
                    logStatus("Auth result: " + response.getType());
            }
        }
    }

    private void onAuthenticationComplete(AuthenticationResponse authResponse) {
        // Once we have obtained an authorization token, we can proceed with creating a Player.
        logStatus("Got authentication token");
        if (mPlayer == null) {
            Config playerConfig = new Config(getApplicationContext(), authResponse.getAccessToken(), CLIENT_ID);
            // Since the Player is a static singleton owned by the Spotify class, we pass "this" as
            // the second argument in order to refcount it properly. Note that the method
            // Spotify.destroyPlayer() also takes an Object argument, which must be the same as the
            // one passed in here. If you pass different instances to Spotify.getPlayer() and
            // Spotify.destroyPlayer(), that will definitely result in resource leaks.
            mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                @Override
                public void onInitialized(Player player) {
                    logStatus("-- Player initialized --");
                    mPlayer.setConnectivityStatus(getNetworkConnectivity(MusicActivity.this));
                    mPlayer.addPlayerNotificationCallback(MusicActivity.this);
                    mPlayer.addConnectionStateCallback(MusicActivity.this);
                    // Trigger UI refresh
                    updateButtons();
                }

                @Override
                public void onError(Throwable error) {
                    logStatus("Error in initialization: " + error.getMessage());
                }
            });
        } else {
            mPlayer.login(authResponse.getAccessToken());
        }
    }

    //  _   _ ___   _____                 _
    // | | | |_ _| | ____|_   _____ _ __ | |_ ___
    // | | | || |  |  _| \ \ / / _ \ '_ \| __/ __|
    // | |_| || |  | |___ \ V /  __/ | | | |_\__ \
    //  \___/|___| |_____| \_/ \___|_| |_|\__|___/
    //

    private void updateButtons() {
        boolean loggedIn = isLoggedIn();

        // Login button should be the inverse of the logged in state
        Button loginButton = (Button) findViewById(R.id.login_button);
        if (loggedIn) loginButton.setText(getString(R.string.logout_button_label));
        else loginButton.setText(getString(R.string.login_button_label));

        // Set enabled for all widgets which depend on initialized state
        for (int id : REQUIRES_INITIALIZED_STATE) {
            findViewById(id).setEnabled(loggedIn);
        }

        // Same goes for the playing state
        boolean playing = loggedIn && mCurrentPlayerState.playing;
        for (int id : REQUIRES_PLAYING_STATE) {
            findViewById(id).setEnabled(playing);
        }
    }

    private boolean isLoggedIn() {
        return mPlayer != null && mPlayer.isLoggedIn();
    }

    public void onLoginButtonClicked(View view) {
        if (!isLoggedIn()) {
            logStatus("Logging in");
            openLoginWindow();
        } else {
            mPlayer.logout();
        }
    }

    public void onShowPlayerStateButtonClicked(View view) {
        mPlayer.getPlayerState(new PlayerStateCallback() {
            @Override
            public void onPlayerState(PlayerState playerState) {
                logStatus("-- Current player state --");
                logStatus("Playing? " + playerState.playing);
                logStatus("Position: " + playerState.positionInMs + "ms");
                logStatus("Shuffling? " + playerState.shuffling);
                logStatus("Repeating? " + playerState.repeating);
                logStatus("Active device? " + playerState.activeDevice);
                logStatus("Track uri: " + playerState.trackUri);
                logStatus("Track duration: " + playerState.durationInMs);
            }
        });
    }

    public void onPlayButtonClicked(View view) {
        if (view.getId() == R.id.play_album_button) {
            logStatus("Starting playback the list of tracks");
            mPlayer.play(TEST_ALBUM_TRACKS);
        } else {
            String uri;
            switch (view.getId()) {
                case R.id.play_track_button:
                    uri = TEST_SONG_URI;
                    break;
                case R.id.play_playlist_button:
                    uri = TEST_PLAYLIST_URI;
                    break;
                default:
                    throw new IllegalArgumentException("View ID does not have an associated URI to play");
            }

            logStatus("Starting playback for " + uri);
            mPlayer.play(uri);
        }
    }

    public void onPauseButtonClicked(View view) {
        if (mCurrentPlayerState.playing) {
            mPlayer.pause();
        } else {
            mPlayer.resume();
        }
    }

    public void onSkipToPreviousButtonClicked(View view) {
        mPlayer.skipToPrevious();
    }

    public void onSkipToNextButtonClicked(View view) {
        mPlayer.skipToNext();
    }

//    public void onQueueSongButtonClicked(View view) {
//        mPlayer.queue(TEST_QUEUE_SONG_URI);
//        Toast toast = Toast.makeText(this, R.string.song_queued_toast, Toast.LENGTH_SHORT);
//        toast.show();
//    }

    public void onToggleShuffleButtonClicked(View view) {
        mPlayer.setShuffle(!mCurrentPlayerState.shuffling);
    }

    public void onToggleRepeatButtonClicked(View view) {
        mPlayer.setRepeat(!mCurrentPlayerState.repeating);
    }

    public void onSeekButtonClicked(View view) {
        // Skip to 10 seconds in the current song
        mPlayer.seekToPosition(10000);
    }
//
//    public void onLowBitrateButtonPressed(View view) {
//        mPlayer.setPlaybackBitrate(PlaybackBitrate.BITRATE_LOW);
//    }
//
//    public void onNormalBitrateButtonPressed(View view) {
//        mPlayer.setPlaybackBitrate(PlaybackBitrate.BITRATE_NORMAL);
//    }
//
//    public void onHighBitrateButtonPressed(View view) {
//        mPlayer.setPlaybackBitrate(PlaybackBitrate.BITRATE_HIGH);
//    }

    //   ____      _ _ _                _      __  __      _   _               _
    //  / ___|__ _| | | |__   __ _  ___| | __ |  \/  | ___| |_| |__   ___   __| |___
    // | |   / _` | | | '_ \ / _` |/ __| |/ / | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
    // | |__| (_| | | | |_) | (_| | (__|   <  | |  | |  __/ |_| | | | (_) | (_| \__ \
    //  \____\__,_|_|_|_.__/ \__,_|\___|_|\_\ |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
    //

    @Override
    public void onLoggedIn() {
        logStatus("Login complete");
        updateButtons();
    }

    @Override
    public void onLoggedOut() {
        logStatus("Logout complete");
        updateButtons();
    }

    @Override
    public void onLoginFailed(Throwable error) {
        logStatus("Login error");
    }

    @Override
    public void onTemporaryError() {
        logStatus("Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(final String message) {
        logStatus("Incoming connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(final EventType eventType, final PlayerState playerState) {
        // Remember kids, always use the English locale when changing case for non-UI strings!
        // Otherwise you'll end up with mysterious errors when running in the Turkish locale.
        // See: http://java.sys-con.com/node/46241
        String eventName = eventType.name().toLowerCase(Locale.ENGLISH).replaceAll("_", " ");
        logStatus("Player event: " + eventName);
        mCurrentPlayerState = playerState;
        updateButtons();
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        String errorName = errorType.name().toLowerCase(Locale.ENGLISH).replaceAll("_", " ");
        logStatus("Player error: " + errorName);
    }

    //  _____                       _   _                 _ _ _
    // | ____|_ __ _ __ ___  _ __  | | | | __ _ _ __   __| | (_)_ __   __ _
    // |  _| | '__| '__/ _ \| '__| | |_| |/ _` | '_ \ / _` | | | '_ \ / _` |
    // | |___| |  | | | (_) | |    |  _  | (_| | | | | (_| | | | | | | (_| |
    // |_____|_|  |_|  \___/|_|    |_| |_|\__,_|_| |_|\__,_|_|_|_| |_|\__, |
    //                                                                 |___/

    /**
     * Print a status message from a callback (or some other place) to the TextView in this
     * activity
     *
     * @param status Status message
     */
    private void logStatus(String status) {
        Log.i("SpotifySdkDemo", status);
        if (!TextUtils.isEmpty(mStatusText.getText())) {
            mStatusText.append("\n");
        }
        mStatusText.append(status);
        mStatusTextScrollView.post(new Runnable() {
            @Override
            public void run() {
                // Scroll to the bottom
                mStatusTextScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    //  ____            _                   _   _
    // |  _ \  ___  ___| |_ _ __ _   _  ___| |_(_) ___  _ __
    // | | | |/ _ \/ __| __| '__| | | |/ __| __| |/ _ \| '_ \
    // | |_| |  __/\__ \ |_| |  | |_| | (__| |_| | (_) | | | |
    // |____/ \___||___/\__|_|   \__,_|\___|\__|_|\___/|_| |_|
    //

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStateReceiver);

        // Note that calling Spotify.destroyPlayer() will also remove any callbacks on whatever
        // instance was passed as the refcounted owner. So in the case of this particular example,
        // it's not strictly necessary to call these methods, however it is generally good practice
        // and also will prevent your application from doing extra work in the background when
        // paused.
        if (mPlayer != null) {
            mPlayer.removePlayerNotificationCallback(MusicActivity.this);
            mPlayer.removeConnectionStateCallback(MusicActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        // *** ULTRA-IMPORTANT ***
        // ALWAYS call this in your onDestroy() method, otherwise you will leak native resources!
        // This is an unfortunate necessity due to the different memory management models of
        // Java's garbage collector and C++ RAII.
        // For more information, see the documentation on Spotify.destroyPlayer().
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
