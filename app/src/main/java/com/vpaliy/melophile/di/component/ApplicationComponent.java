package com.vpaliy.melophile.di.component;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.source.RemoteSource;
import com.vpaliy.data.source.remote.Filter;
import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.interactor.FollowUser;
import com.vpaliy.domain.interactor.GetMe;
import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.PlaylistHistory;
import com.vpaliy.domain.interactor.TrackHistory;
import com.vpaliy.domain.interactor.GetTrack;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.interactor.GetUserFavorites;
import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.interactor.LoveTrack;
import com.vpaliy.domain.interactor.PlaylistSearch;
import com.vpaliy.domain.interactor.SaveInteractor;
import com.vpaliy.domain.interactor.TrackSearch;
import com.vpaliy.domain.interactor.UserSearch;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.domain.repository.SearchRepository;
import com.vpaliy.melophile.di.module.ApplicationModule;
import com.vpaliy.melophile.di.module.DataModule;
import com.vpaliy.melophile.di.module.InteractorModule;
import com.vpaliy.melophile.di.module.MapperModule;
import com.vpaliy.melophile.di.module.NetworkModule;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.base.Navigator;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.soundcloud.SoundCloudService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        MapperModule.class,
        NetworkModule.class,
        InteractorModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {
  void inject(BaseActivity activity);

  void inject(BaseFragment fragment);

  Context context();

  RemoteSource remote();

  Filter filter();

  Repository repository();

  SearchRepository searchRepository();

  BaseSchedulerProvider scheduler();

  SoundCloudService soundCloud();

  Navigator navigator();

  RxBus rxBus();

  //use cases
  GetPlaylists playlistsInteractor();

  GetTracks tracksInteractor();

  GetPlaylist playlistInteractor();

  GetTrack trackInteractor();

  GetUserDetails userDetailsInteractor();

  GetUserFollowers userFollowersInteractor();

  GetUserFavorites userFavoritesInteractor();

  TrackSearch trackSearchInteractor();

  PlaylistSearch playlistSearchInteractor();

  UserSearch userSearchInteractor();

  SaveInteractor saveInteractor();

  GetMe meInteractor();

  FollowUser followUserInteractor();

  LoveTrack loveTrackInteractor();

  PlaylistHistory recentPlaylistsInteractor();

  TrackHistory recentTracksInteractor();

  Mapper<MediaMetadataCompat, Track> mapper();
}
