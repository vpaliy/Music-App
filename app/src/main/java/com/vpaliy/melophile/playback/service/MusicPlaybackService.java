package com.vpaliy.melophile.playback.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import java.util.List;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.playback.MediaTasks;
import com.vpaliy.melophile.playback.PlaybackManager;
import com.vpaliy.melophile.ui.track.TrackActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import static com.vpaliy.melophile.playback.MediaHelper.MEDIA_ID_EMPTY_ROOT;
import static com.vpaliy.melophile.playback.MediaHelper.MEDIA_ID_ROOT;

public class MusicPlaybackService extends MediaBrowserServiceCompat
        implements PlaybackManager.PlaybackServiceCallback,
        PlaybackManager.MetadataUpdateListener{

    private static final String LOG_TAG=MusicPlaybackService.class.getSimpleName();

    private MediaSessionCompat mediaSession;
    private TrackNotification notification;

    @Inject
    protected PlaybackManager playbackManager;

    public MusicPlaybackService(){
        App.appInstance().playerComponent().inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playbackManager.setServiceCallback(this);
        playbackManager.setUpdateListener(this);
        mediaSession=new MediaSessionCompat(getApplicationContext(),LOG_TAG);
        mediaSession.setCallback(playbackManager.getMediaSessionCallback());
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        setSessionToken(mediaSession.getSessionToken());
        Context context = getApplicationContext();
        Intent intent = new Intent(context, TrackActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 99,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mediaSession.setSessionActivity(pi);
        notification=new TrackNotification(this);
        playbackManager.updatePlaybackState(PlaybackStateCompat.STATE_NONE);
    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        Log.d(LOG_TAG,"onStartCommand()");
        if (startIntent != null) {
            String action = startIntent.getAction();
            if(action!=null) {
                if (action.equals(MediaTasks.ACTION_STOP)) {
                    Log.d(LOG_TAG,"Stopping self");
                    stopSelf();
                }
                MediaTasks.executeTask(playbackManager, action);
            }
            // Try to handle the intent as a media button event wrapped by MediaButtonReceiver
            // MediaButtonReceiver.handleIntent(mediaSession, startIntent);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        Log.d(LOG_TAG,"onMetadataChanged");
        mediaSession.setMetadata(metadata);
        notification.updateMetadata(metadata);
    }


    @Override
    public void onMetadataRetrieveError() {

    }

    @Override
    public void onPlaybackStart() {
        Log.d(LOG_TAG,"onPlaybackStart");
        mediaSession.setActive(true);
        Intent intent=new Intent(this,MusicPlaybackService.class);
        startService(intent);
    }

    @Override
    public void onPlaybackPause() {
        Log.d(LOG_TAG,"onPlaybackPause");
        stopForeground(false);
    }

    @Override
    public void onPlaybackStop() {
        Log.d(LOG_TAG,"onPlaybackStop");
        mediaSession.setActive(false);
        notification.pauseNotification();
    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat stateCompat) {
        Log.d(LOG_TAG,"onPlaybackStateUpdated");
        mediaSession.setPlaybackState(stateCompat);
        notification.updatePlaybackState(stateCompat);
    }

    @Override
    public void onNotificationRequired() {
        Log.d(LOG_TAG,"onNotificationRequired");
        notification.startNotification();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"onDestroy()");
        mediaSession.release();
        stopForeground(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        Log.d(LOG_TAG,"onGetRoot()");
        if(!clientPackageName.equals(getPackageName())){
            return new BrowserRoot(MEDIA_ID_ROOT,null);
        }
        return new BrowserRoot(MEDIA_ID_EMPTY_ROOT,null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }
}