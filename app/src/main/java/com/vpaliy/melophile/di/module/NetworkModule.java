package com.vpaliy.melophile.di.module;

import android.content.Context;

import com.vpaliy.melophile.di.Config;
import com.vpaliy.soundcloud.SoundCloud;
import com.vpaliy.soundcloud.SoundCloudService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Singleton @Provides
    SoundCloudService soundCloudService(Context context){
        return SoundCloud.create(Config.CLIENT_ID)
                .createService(context);
    }
}
