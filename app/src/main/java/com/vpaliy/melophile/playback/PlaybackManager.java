package com.vpaliy.melophile.playback;

import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.domain.playback.PlayerScope;
import com.vpaliy.domain.playback.QueueManager;
import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
@PlayerScope
public class PlaybackManager implements Playback.Callback {

    private static final String TAG= PlaybackManager.class.getSimpleName();

    private PlaybackServiceCallback serviceCallback;
    private MediaSessionCallback mediaSessionCallback;
    private Mapper<MediaMetadataCompat,Track> mapper;
    private MetadataUpdateListener updateListener;
    protected QueueManager queueManager;
    protected Playback playback;


    @Inject
    public PlaybackManager(Playback playback, Mapper<MediaMetadataCompat,Track> mapper){
        this.mediaSessionCallback=new MediaSessionCallback();
        this.playback=playback;
        this.playback.setCallback(this);
        this.mapper=mapper;
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

    public void handlePlayRequest(Track track){
        if(track!=null) {
            Log.d(TAG,track.getStreamUrl());
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
    public void onStop() {
        updatePlaybackState(PlaybackStateCompat.STATE_STOPPED);
        serviceCallback.onPlaybackStop();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCompletetion() {
        handlePlayRequest(queueManager.next());
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
        long position = playback.getPosition();
        if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_PAUSED) {
            serviceCallback.onNotificationRequired();
        }
        PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder()
                .setActions(getAvailableActions())
                .setState(state, position, 1.0f, SystemClock.elapsedRealtime());
        serviceCallback.onPlaybackStateUpdated(builder.build());
    }

    private void updateMetadata(){
        if(updateListener!=null){
            MediaMetadataCompat result=new MediaMetadataCompat.Builder(mapper.map(queueManager.current()))
                    .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS,queueManager.size())
                    .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER,queueManager.currentIndex()+1)
                    .build();
            updateListener.onMetadataChanged(result);
        }
    }

    public void setServiceCallback(PlaybackServiceCallback serviceCallback) {
        this.serviceCallback=serviceCallback;
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
    }

    public interface PlaybackServiceCallback  {
        void onPlaybackStart();
        void onPlaybackPause();
        void onPlaybackStop();
        void onNotificationRequired();
        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }
}