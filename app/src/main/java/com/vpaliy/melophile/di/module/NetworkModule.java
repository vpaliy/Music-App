package com.vpaliy.melophile.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vpaliy.melophile.di.Config;
import com.vpaliy.soundcloud.SoundCloud;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.Token;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

  private Token token;

  public NetworkModule(@NonNull Token token) {
    this.token = token;
  }

  @Singleton
  @Provides
  SoundCloudService soundCloudService(Context context) {
    return SoundCloud.create(Config.CLIENT_ID)
            .appendToken(token)
            .createService(context);
  }
}
