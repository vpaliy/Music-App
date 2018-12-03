package com.vpaliy.melophile.di.component;

import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.di.scope.ViewScope;
import com.vpaliy.melophile.ui.personal.PersonalFragment;
import com.vpaliy.melophile.ui.playlist.PlaylistFragment;
import com.vpaliy.melophile.ui.playlists.PlaylistsFragment;
import com.vpaliy.melophile.ui.search.SearchActivity;
import com.vpaliy.melophile.ui.track.TrackFragment;
import com.vpaliy.melophile.ui.tracks.TracksFragment;
import com.vpaliy.melophile.ui.user.PersonFragment;
import com.vpaliy.melophile.ui.user.info.FavoriteFragment;
import com.vpaliy.melophile.ui.user.info.FollowersFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class,
        modules = PresenterModule.class)
public interface ViewComponent {
  void inject(PlaylistsFragment fragment);

  void inject(TracksFragment fragment);

  void inject(PersonalFragment fragment);

  void inject(PlaylistFragment fragment);

  void inject(PersonFragment fragment);

  void inject(FavoriteFragment fragment);

  void inject(FollowersFragment fragment);

  void inject(SearchActivity activity);
}
