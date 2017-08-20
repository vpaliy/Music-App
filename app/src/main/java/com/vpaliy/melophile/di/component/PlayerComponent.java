package com.vpaliy.melophile.di.component;

import com.vpaliy.melophile.di.module.PlaybackModule;
import com.vpaliy.melophile.di.scope.PlayerScope;

import dagger.Component;

@PlayerScope
@Component(dependencies = ApplicationComponent.class,
          modules = PlaybackModule.class)
public interface PlayerComponent {
}
