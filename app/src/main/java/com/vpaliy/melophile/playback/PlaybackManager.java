package com.vpaliy.melophile.playback;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.domain.interactor.SaveInteractor;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.domain.playback.QueueManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.vpaliy.domain.playback.PlayerScope;

@SuppressWarnings("WeakerAccess")
@PlayerScope
public class PlaybackManager implements Playback.Callback {

  private static final String TAG = PlaybackManager.class.getSimpleName();

  private PlaybackServiceCallback serviceCallback;
  private MediaSessionCallback mediaSessionCallback;
  private Mapper<MediaMetadataCompat, Track> mapper;
  private MetadataUpdateListener updateListener;
  private QueueManager queueManager;
  private Playback playback;
  private SaveInteractor saveInteractor;
  private boolean isRepeat;
  private boolean isShuffle;
  private int lastState;

  @Inject
  public PlaybackManager(Playback playback, Mapper<MediaMetadataCompat, Track> mapper, SaveInteractor saveInteractor) {
    this.mediaSessionCallback = new MediaSessionCallback();
    this.playback = playback;
    this.playback.setCallback(this);
    this.mapper = mapper;
    this.saveInteractor = saveInteractor;
  }

  public void setUpdateListener(MetadataUpdateListener updateListener) {
    this.updateListener = updateListener;
  }

  public MediaSessionCallback getMediaSessionCallback() {
    return mediaSessionCallback;
  }

  public void setQueueManager(QueueManager queueManager) {
    this.queueManager = queueManager;
  }

  public void handlePlayRequest(Track track) {
    if (track != null) {
      saveInteractor.saveTrack(track);
      playback.play(track.getStreamUrl());
      updateMetadata();
    }
  }

  public Playback getPlayback() {
    return playback;
  }

  private long getAvailableActions() {
    long actions =
            PlaybackStateCompat.ACTION_PLAY_PAUSE |
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
    if (playback.isPlaying()) {
      actions |= PlaybackStateCompat.ACTION_PAUSE;
    } else {
      actions |= PlaybackStateCompat.ACTION_PLAY;
    }
    //
    if (isRepeat) {
      actions |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    }
    //
    if (isShuffle) {
      actions |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
    }
    return actions;
  }

  public void handlePauseRequest() {
    playback.pause();
  }

  public void handleStopRequest() {
    if (!playback.isPlaying()) {
      playback.stop();
    }
  }

  @Override
  public void onPlay() {
    updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
    serviceCallback.onPlaybackStart();
  }

  @Override
  public void onStop() {
    updatePlaybackState(PlaybackStateCompat.STATE_STOPPED);
    serviceCallback.onPlaybackStop();
  }

  @Override
  public void onError() {
    updateListener.onMetadataRetrieveError();
  }

  @Override
  public void onCompletetion() {
    Track track = isRepeat ? queueManager.current() : queueManager.next();
    if (isRepeat) {
      playback.invalidateCurrent();
    }
    handlePlayRequest(track);
  }

  public void handleResumeRequest() {
    if (queueManager != null) {
      handlePlayRequest(queueManager.current());
    }
  }

  public void handleNextRequest() {
    if (queueManager != null) {
      playback.invalidateCurrent();
      handlePlayRequest(queueManager.next());
    }
  }

  public void handlePrevRequest() {
    if (queueManager != null) {
      long position = TimeUnit.MILLISECONDS.toSeconds(playback.getPosition());
      playback.invalidateCurrent();
      if (position > 5 || position <= 2) {
        handlePlayRequest(queueManager.previous());
      } else {
        handlePlayRequest(queueManager.current());
      }
    }
  }

  private void handleRepeatMode() {
    isRepeat = !isRepeat;
    updatePlaybackState(lastState);
  }

  private void handleShuffleMode() {
    isShuffle = !isShuffle;
    if (queueManager != null) {
      queueManager.shuffle();
    }
    updatePlaybackState(lastState);
  }

  @Override
  public void onPause() {
    updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);
    serviceCallback.onPlaybackStop();
  }

  public void updatePlaybackState(int state) {
    long position = playback.getPosition();
    this.lastState = state;
    if (state == PlaybackStateCompat.STATE_PLAYING ||
            state == PlaybackStateCompat.STATE_PAUSED) {
      serviceCallback.onNotificationRequired();
    }
    PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder()
            .setActions(getAvailableActions())
            .setState(state, position, 1.0f, SystemClock.elapsedRealtime());
    serviceCallback.onPlaybackStateUpdated(builder.build());
  }

  private void updateMetadata() {
    if (updateListener != null) {
      MediaMetadataCompat result = new MediaMetadataCompat.Builder(mapper.map(queueManager.current()))
              .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, queueManager.size())
              .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, queueManager.currentIndex() + 1)
              .putLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER, playback.getPosition())
              .build();
      updateListener.onMetadataChanged(result);
    }
  }

  public void setServiceCallback(PlaybackServiceCallback serviceCallback) {
    this.serviceCallback = serviceCallback;
  }

  private class MediaSessionCallback extends MediaSessionCompat.Callback {

    @Override
    public void onPlay() {
      super.onPlay();
      handlePlayRequest(queueManager.current());
    }

    @Override
    public void onSkipToNext() {
      super.onSkipToNext();
      handleNextRequest();
    }

    @Override
    public void onSkipToPrevious() {
      super.onSkipToPrevious();
      handlePrevRequest();
    }

    @Override
    public void onPause() {
      super.onPause();
      handlePauseRequest();
    }

    @Override
    public void onSetRepeatMode(int repeatMode) {
      handleRepeatMode();
    }

    @Override
    public void onSetShuffleModeEnabled(boolean enabled) {
      super.onSetShuffleModeEnabled(enabled);
      handleShuffleMode();
    }

    @Override
    public void onStop() {
      super.onStop();
      handleStopRequest();
    }

    @Override
    public void onSeekTo(long pos) {
      super.onSeekTo(pos);
      playback.seekTo((int) pos);
    }

    @Override
    public void onCustomAction(String action, Bundle extras) {
      super.onCustomAction(action, extras);
    }
  }

  public interface MetadataUpdateListener {
    void onMetadataChanged(MediaMetadataCompat metadata);

    void onMetadataRetrieveError();
  }

  public interface PlaybackServiceCallback {
    void onPlaybackStart();

    void onPlaybackStop();

    void onNotificationRequired();

    void onPlaybackStateUpdated(PlaybackStateCompat newState);
  }
}