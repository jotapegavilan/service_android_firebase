package com.gavilan.serviciosenandroid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    MediaPlayer reproductor;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        reproductor = MediaPlayer.create(this,R.raw.cancion);
        reproductor.setVolume(100,100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reproductor.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( reproductor.isPlaying() ){
            reproductor.stop();
        }
        reproductor.release();
        reproductor = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}