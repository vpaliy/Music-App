package com.vpaliy.melophile.playback;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MediaPlayback extends BasePlayback
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnSeekCompleteListener {

  private static final String TAG = MediaPlayback.class.getSimpleName();

  private MediaPlayer player;
  private int playerState = PlaybackStateCompat.STATE_NONE;

  @Inject
  public MediaPlayback(Context context,
                       AudioManager audioManager,
                       WifiManager.WifiLock wifiLock) {
    super(context, audioManager, wifiLock);
  }

  @Override
  public void startPlayer() {
    createPlayerIfNeeded();
    try {
      playerState = PlaybackStateCompat.STATE_BUFFERING;
      player.setAudioStreamType(AudioManager.STREAM_MUSIC);
      player.setDataSource(currentUrl);
      player.prepareAsync();
    } catch (IOException ex) {
      ex.printStackTrace();
      //notify UI
      if (callback != null) callback.onError();
    }
  }

  @Override
  public void updatePlayer() {
    if (player != null) {
      switch (focusState) {
        case AUDIO_NO_FOCUS_NO_DUCK:
          if (playerState == PlaybackStateCompat.STATE_PLAYING) {
            pause();
          }
          return;
        case AUDIO_NO_FOCUS_CAN_DUCK:
          registerNoiseReceiver();
          player.setVolume(VOLUME_DUCK, VOLUME_DUCK);
          break;
        default:
          registerNoiseReceiver();
          player.setVolume(VOLUME_NORMAL, VOLUME_NORMAL);
      }
      player.start();
      playerState = PlaybackStateCompat.STATE_PLAYING;
      if (callback != null) callback.onPlay();
    }
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    Log.d(TAG, "State:" + playerState);
    updatePlayer();
    playerState = PlaybackStateCompat.STATE_PLAYING;
  }

  private void createPlayerIfNeeded() {
    if (player == null) {
      player = new MediaPlayer();
      player.setWakeMode(context.getApplicationContext(),
              PowerManager.PARTIAL_WAKE_LOCK);
      player.setOnPreparedListener(this);
      player.setOnErrorListener(this);
      player.setOnCompletionListener(this);
      player.setOnSeekCompleteListener(this);
    } else {
      player.reset();
    }
  }

  @Override
  public boolean onError(MediaPlayer mp, int what, int extra) {
    if (callback != null) callback.onError();
    stopPlayer();
    return true;
  }

  @Override
  public void pausePlayer() {
    Log.d(TAG, "State:" + playerState);
    if (playerState == PlaybackStateCompat.STATE_PLAYING) {
      if (isPlaying()) {
        player.pause();
      }
    }
    playerState = PlaybackStateCompat.STATE_PAUSED;
  }

  @Override
  public void onSeekComplete(MediaPlayer mp) {
    Log.d(TAG, "State:" + playerState);
    if (playerState == PlaybackStateCompat.STATE_BUFFERING) {
      registerNoiseReceiver();
      player.start();
      playerState = PlaybackStateCompat.STATE_PLAYING;
    }
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    if (callback != null) callback.onCompletetion();
  }

  @Override
  public void resumePlayer() {
    if (player != null) {
      if (!player.isPlaying()) {
        player.start();
      }
      playerState = PlaybackStateCompat.STATE_PLAYING;
      if (callback != null) callback.onPlay();
    }
  }

  @Override
  public void stopPlayer() {
    playerState = PlaybackStateCompat.STATE_STOPPED;
    if (player != null) {
      player.release();
      player = null;
    }
  }

  @Override
  public void seekTo(int position) {
    Log.d(TAG, "Player is null:" + Boolean.toString(player == null));
    Log.d(TAG, "Player's position:" + Integer.toString(position));
    if (player != null) {
      if (player.isPlaying()) {
        playerState = PlaybackStateCompat.STATE_BUFFERING;
      }
      registerNoiseReceiver();
      player.seekTo(position);
    }
  }

  @Override
  public long getPosition() {
    return player != null ? player.getCurrentPosition() : 0;
  }

  @Override
  public boolean isPlaying() {
    if (player != null) {
      return player.isPlaying();
    }
    return false;
  }
}