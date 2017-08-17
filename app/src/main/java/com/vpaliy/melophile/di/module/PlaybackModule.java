package com.vpaliy.melophile.di.module;

import android.content.Context;
import android.os.Build;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.melophile.playback.BasePlayback;
import com.vpaliy.melophile.playback.MediaPlayback;
import com.vpaliy.melophile.playback.MediaPlayback21;
import com.vpaliy.melophile.playback.PlaybackManager;
import com.vpaliy.melophile.playback.QueueManager;
import com.vpaliy.melophile.ui.utils.Permission;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class PlaybackModule {

    @Singleton @Provides
    Playback playback(Context context){
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            return new MediaPlayback21(context,null,null);
        }
        return new MediaPlayback(context,null,null);
    }

    @Singleton @Provides
    QueueManager queueManager(){
        return new QueueManager();
    }

    @Singleton @Provides
    PlaybackManager playbackManager(Playback playback, QueueManager queueManager){
        return new PlaybackManager(playback,queueManager);
    }
}
