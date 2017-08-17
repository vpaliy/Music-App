package com.vpaliy.melophile.di.module;

import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.melophile.ui.playlist.PlaylistContract;
import com.vpaliy.melophile.ui.playlist.PlaylistPresenter;
import com.vpaliy.melophile.ui.playlists.PlaylistsContract;
import com.vpaliy.melophile.ui.playlists.PlaylistsPresenter;
import com.vpaliy.melophile.di.scope.ViewScope;
import com.vpaliy.melophile.ui.tracks.TracksContract;
import com.vpaliy.melophile.ui.tracks.TracksPresenter;
import com.vpaliy.melophile.ui.user.PersonContract;
import com.vpaliy.melophile.ui.user.PersonPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @ViewScope @Provides
    PlaylistsContract.Presenter playlists(GetPlaylists getPlaylists){
        return new PlaylistsPresenter(getPlaylists);
    }

    @ViewScope @Provides
    TracksContract.Presenter tracks(GetTracks getTracks){
        return new TracksPresenter(getTracks);
    }

    @ViewScope @Provides
    PlaylistContract.Presenter playlist(GetPlaylist getPlaylist){
        return new PlaylistPresenter(getPlaylist);
    }

    @ViewScope @Provides
    PersonContract.Presenter person(GetUserDetails getUserDetails){
        return new PersonPresenter(getUserDetails);
    }
}
