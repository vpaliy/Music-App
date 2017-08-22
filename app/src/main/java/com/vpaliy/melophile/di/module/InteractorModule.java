package com.vpaliy.melophile.di.module;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.GetTrack;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.interactor.GetUserFavorites;
import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.interactor.PlaylistSearch;
import com.vpaliy.domain.interactor.TrackSearch;
import com.vpaliy.domain.interactor.UserSearch;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

//TODO are you sure they should live all the time???

@Module
public class InteractorModule {
    @Singleton @Provides
    GetPlaylists getPlaylists(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetPlaylists(schedulerProvider,repository);
    }

    @Singleton @Provides
    GetTracks getTracks(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetTracks(schedulerProvider,repository);
    }

    @Singleton @Provides
    GetTrack getTrack(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetTrack(schedulerProvider,repository);
    }

    @Singleton @Provides
    GetPlaylist getPlaylist(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetPlaylist(schedulerProvider,repository);
    }

    @Singleton @Provides
    GetUserDetails getUserDetails(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetUserDetails(schedulerProvider,repository);
    }

    @Singleton @Provides
    GetUserFollowers getUserFollowers(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetUserFollowers(repository,schedulerProvider);
    }

    @Singleton @Provides
    GetUserFavorites getUserFavorites(Repository repository, BaseSchedulerProvider schedulerProvider){
        return new GetUserFavorites(repository,schedulerProvider);
    }

    @Singleton @Provides
    TrackSearch trackSearch(){
        return new TrackSearch();
    }

    @Singleton @Provides
    UserSearch userSearch(){
        return new UserSearch();
    }

    @Singleton @Provides
    PlaylistSearch playlistSearch(){
        return new PlaylistSearch();
    }
}
