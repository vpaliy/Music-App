package com.vpaliy.domain.playback;

;

/* The base interface for any playback implementation */
public interface Playback {

  void play(String streamUrl);

  void pause();

  void stop();

  void seekTo(int position);

  void setCallback(Callback callback);

  void invalidateCurrent();

  boolean isPlaying();

  long getPosition();

  /* This interface is used to notify others about changes in the player*/
  interface Callback {
    void onCompletetion();

    void onPause();

    void onPlay();

    void onStop();

    void onError();
  }
}