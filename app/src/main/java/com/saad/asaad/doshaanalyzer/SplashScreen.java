package com.saad.asaad.doshaanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by asaad on 12/8/2015.
 */
public class SplashScreen extends Activity{

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
    private MediaPlayer welcomeSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
    }


    public void allocateWelcomeSong()
    {
        if(welcomeSong == null)
            welcomeSong = MediaPlayer.create(this.getBaseContext(), R.raw.startup);
        welcomeSong.setOnPreparedListener(welcomeSongPListener);
        welcomeSong.setOnCompletionListener(welcomeSongCListener);
    }

    private MediaPlayer.OnPreparedListener welcomeSongPListener = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            playWelcomeSong();
        }
    };

    private MediaPlayer.OnCompletionListener welcomeSongCListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            playWelcomeSong();
        }
    };

    public void playWelcomeSong()
    {
        if (welcomeSong.isPlaying())
        {
            welcomeSong.pause();
        }
        if(welcomeSong.getCurrentPosition() != 1)
        {
            welcomeSong.seekTo(1);
        }
        welcomeSong.start();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        allocateWelcomeSong();
    }

    @Override
    protected void onPause()
    {
        deallocateWelcomeSong();
        super.onPause();
    }

    public void deallocateWelcomeSong()
    {

        if (welcomeSong.isPlaying())
        {
            welcomeSong.stop();
        }
        if (!(welcomeSong == null))
        {
            welcomeSong.release();
            welcomeSong = null;
        }
        welcomeSongCListener = null;
        welcomeSongPListener = null;
    }
}