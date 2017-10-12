package com.vpaliy.melophile.di.component;

import com.vpaliy.melophile.di.module.PlaybackModule;
import com.vpaliy.melophile.playback.service.MusicPlaybackService;
import com.vpaliy.melophile.ui.player.PlayerActivity;
import dagger.Component;
import com.vpaliy.domain.playback.PlayerScope;

@PlayerScope
@Component(dependencies = ApplicationComponent.class,
          modules = PlaybackModule.class)
public interface PlayerComponent {
    void inject(MusicPlaybackService service);
    void inject(PlayerActivity fragment);
}
