package com.vpaliy.melophile.playback;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;
import javax.inject.Singleton;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@Singleton
public class MediaPlayback21 extends BasePlayback
        implements ExoPlayer.EventListener {

  private SimpleExoPlayer exoPlayer;
  private boolean isPause = false;

  @Inject
  public MediaPlayback21(Context context,
                         AudioManager audioManager,
                         WifiManager.WifiLock wifiLock) {
    super(context, audioManager, wifiLock);
  }

  @Override
  public void pausePlayer() {
    if (exoPlayer != null) {
      isPause = true;
      exoPlayer.setPlayWhenReady(false);
    }
  }

  @Override
  public void resumePlayer() {
    if (exoPlayer != null) {
      configPlayer();
    }
  }

  @Override
  public void updatePlayer() {
    configPlayer();
  }

  @Override
  public void startPlayer() {
    if (exoPlayer == null) {
      exoPlayer =
              ExoPlayerFactory.newSimpleInstance(
                      context, new DefaultTrackSelector(), new DefaultLoadControl());
      exoPlayer.addListener(this);
    }
    exoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
            context, Util.getUserAgent(context, "uamp"), null);
    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
    MediaSource mediaSource = new ExtractorMediaSource(
            Uri.parse(currentUrl), dataSourceFactory, extractorsFactory, null, null);
    exoPlayer.prepare(mediaSource);
    configPlayer();
  }

  private void configPlayer() {
    switch (focusState) {
      case AUDIO_NO_FOCUS_NO_DUCK:
        pause();
        return;
      case AUDIO_NO_FOCUS_CAN_DUCK:
        exoPlayer.setVolume(VOLUME_DUCK);
        break;
      case AUDIO_FOCUSED:
        exoPlayer.setVolume(VOLUME_NORMAL);
        break;
    }
    registerNoiseReceiver();
    isPause = false;
    exoPlayer.setPlayWhenReady(true);
  }

  @Override
  public void stopPlayer() {
    if (exoPlayer != null) {
      exoPlayer.release();
      exoPlayer.removeListener(this);
      exoPlayer = null;
    }
  }

  @Override
  public void seekTo(int position) {
    if (exoPlayer != null) {
      registerNoiseReceiver();
      exoPlayer.seekTo(position);
    }
  }

  @Override
  public long getPosition() {
    return exoPlayer != null ? exoPlayer.getCurrentPosition() : 0;
  }

  @Override
  public boolean isPlaying() {
    return exoPlayer != null && exoPlayer.getPlayWhenReady();
  }


  /* Listener */
  @Override
  public void onTimelineChanged(Timeline timeline, Object manifest) {

  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    switch (playbackState) {
      case ExoPlayer.STATE_READY:
        if (callback != null) {
          if (isPause) callback.onPause();
          else callback.onPlay();
        }
        break;
      case ExoPlayer.STATE_ENDED:
        if (callback != null) callback.onCompletetion();
        break;
    }
  }

  @Override
  public void onPlayerError(ExoPlaybackException error) {
    if (callback != null) callback.onError();
  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

  }

  @Override
  public void onPositionDiscontinuity() {

  }

  @Override
  public void onLoadingChanged(boolean isLoading) {

  }

  @Override
  public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

  }
}