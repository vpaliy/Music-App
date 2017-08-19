package com.vpaliy.melophile.di.module;

import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.interactor.GetUserFavorites;
import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.playlist.PlaylistContract;
import com.vpaliy.melophile.ui.playlist.PlaylistPresenter;
import com.vpaliy.melophile.ui.playlists.PlaylistsContract;
import com.vpaliy.melophile.ui.playlists.PlaylistsPresenter;
import com.vpaliy.melophile.di.scope.ViewScope;
import com.vpaliy.melophile.ui.tracks.TracksContract;
import com.vpaliy.melophile.ui.tracks.TracksPresenter;
import com.vpaliy.melophile.ui.user.PersonContract;
import com.vpaliy.melophile.ui.user.PersonPresenter;
import com.vpaliy.melophile.ui.user.favorite.FavoritePresenter;
import com.vpaliy.melophile.ui.user.favorite.FollowersPresenter;
import com.vpaliy.melophile.ui.user.favorite.UserInfoContract;

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
    PersonContract.Presenter person(GetUserDetails getUserDetails, GetUserFollowers getUserFollowers){
        return new PersonPresenter(getUserDetails,getUserFollowers);
    }

    @ViewScope @Provides
    UserInfoContract.Presenter<User> followers(GetUserFollowers getUserFollowers){
        return new FollowersPresenter(getUserFollowers);
    }

    @ViewScope @Provides
    UserInfoContract.Presenter<Track> favorites(GetUserFavorites getUserFavorites){
        return new FavoritePresenter(getUserFavorites);
    }
}
