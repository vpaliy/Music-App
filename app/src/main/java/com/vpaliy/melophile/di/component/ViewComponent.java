package com.vpaliy.melophile.di.component;

import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.di.scope.ViewScope;
import com.vpaliy.melophile.ui.personal.PersonalFragment;
import com.vpaliy.melophile.ui.playlist.PlaylistFragment;
import com.vpaliy.melophile.ui.playlists.PlaylistsFragment;
import com.vpaliy.melophile.ui.track.TrackFragment;
import com.vpaliy.melophile.ui.tracks.TracksFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class,
        modules = PresenterModule.class)
public interface ViewComponent {
    void inject(PlaylistsFragment fragment);
    void inject(TracksFragment fragment);
    void inject(PersonalFragment fragment);
    void inject(PlaylistFragment fragment);
    void inject(TrackFragment fragment);
}
