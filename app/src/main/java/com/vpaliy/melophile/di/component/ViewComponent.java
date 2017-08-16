package com.vpaliy.melophile.di.component;

import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.di.scope.ViewScope;
import com.vpaliy.melophile.ui.playlists.PlaylistsFragment;
import com.vpaliy.melophile.ui.tracks.TracksFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class,
        modules = PresenterModule.class)
public interface ViewComponent {
    void inject(PlaylistsFragment fragment);
    void inject(TracksFragment fragment);
}
