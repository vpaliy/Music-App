package com.vpaliy.melophile.di.module;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.domain.interactor.SaveInteractor;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.melophile.playback.MediaPlayback;
import com.vpaliy.melophile.playback.MediaPlayback21;
import com.vpaliy.melophile.playback.PlaybackManager;
import com.vpaliy.melophile.ui.utils.Permission;

import android.support.v4.media.MediaMetadataCompat;

import com.vpaliy.domain.playback.PlayerScope;

import dagger.Provides;
import dagger.Module;

@Module
public class PlaybackModule {

  @PlayerScope
  @Provides
  Playback playback(Context context) {
    AudioManager audioManager = AudioManager.class.cast(context.getSystemService(Context.AUDIO_SERVICE));
    WifiManager.WifiLock wifiManager = ((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE))
            .createWifiLock(WifiManager.WIFI_MODE_FULL, "uAmp_lock");
    if (Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
      return new MediaPlayback21(context, audioManager, wifiManager);
    }
    return new MediaPlayback(context, audioManager, wifiManager);
  }

  @PlayerScope
  @Provides
  PlaybackManager playbackManager(Playback playback, Mapper<MediaMetadataCompat, Track> mapper, SaveInteractor saveInteractor) {
    return new PlaybackManager(playback, mapper, saveInteractor);
  }
}
