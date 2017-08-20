package com.vpaliy.melophile.playback;

import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.domain.playback.QueueManager;
import com.vpaliy.melophile.di.scope.PlayerScope;
import javax.inject.Inject;

@PlayerScope
public class PlaybackManager implements Playback.Callback {

    private static final String TAG= PlaybackManager.class.getSimpleName();

    private Playback playback;
    private QueueManager queueManager;
    private MetadataUpdateListener updateListener;
    private PlaybackServiceCallback serviceCallback;
    private MediaSessionCallback mediaSessionCallback;

    @Inject
    public PlaybackManager(Playback playback,
                           QueueManager queueManager){
        this.playback=playback;
        this.queueManager=queueManager;
        this.mediaSessionCallback=new MediaSessionCallback();
        this.playback.setCallback(this);
    }

    public void setUpdateListener(MetadataUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public MediaSessionCallback getMediaSessionCallback() {
        return mediaSessionCallback;
    }

    public void setServiceCallback(PlaybackServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public void setQueueManager(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void handlePlayRequest(Track track){
        if(track!=null) {
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
        return actions;
    }

    public void handlePauseRequest(){
        playback.pause();
    }

    public void handleStopRequest(){
        playback.stop();
    }

    @Override
    public void onPlay() {
        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
        serviceCallback.onPlaybackStart();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCompletetion() {
        handlePlayRequest(queueManager.next());
    }

    @Override
    public void onStop() {
        updatePlaybackState(PlaybackStateCompat.STATE_STOPPED);
        serviceCallback.onPlaybackStop();
    }

    public void handleResumeRequest(){
        handlePlayRequest(queueManager.current());
    }

    @Override
    public void onPause() {
        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);
        serviceCallback.onPlaybackStop();
    }

    public void updatePlaybackState(int state){
        long position=playback.getPosition();
        if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_PAUSED) {
            serviceCallback.onNotificationRequired();
        }
        PlaybackStateCompat.Builder builder=new PlaybackStateCompat.Builder()
                .setActions(getAvailableActions())
                .setState(state,position,1.0f, SystemClock.elapsedRealtime());
        serviceCallback.onPlaybackStateUpdated(builder.build());
    }

    private void updateMetadata(){
        if(updateListener!=null){
           // updateListener.onMetadataChanged(queueManager.current());
        }
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            Log.d(TAG,"onPlay");
            handlePlayRequest(queueManager.current());
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            Log.d(TAG,"onSkipToNext");
            handlePlayRequest(queueManager.next());
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            Log.d(TAG,"onSkipToPrev");
            Track track=queueManager.previous();
            if(track==null) track=queueManager.current();
            handlePlayRequest(track);
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG,"onPause");
            handlePauseRequest();
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG,"onStop");
            handleStopRequest();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            Log.d(TAG,"onSeekTo");
            playback.seekTo((int)pos);
        }
    }

    public interface MetadataUpdateListener {
        void onMetadataChanged(MediaMetadataCompat metadata);
        void onMetadataRetrieveError();
        void onCurrentQueueIndexUpdated(int queueIndex);
    }

    public interface PlaybackServiceCallback {
        void onPlaybackStart();
        void onPlaybackPause();
        void onNotificationRequired();
        void onPlaybackStop();
        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }
}