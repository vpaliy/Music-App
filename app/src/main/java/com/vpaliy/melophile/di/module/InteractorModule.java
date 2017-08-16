package com.vpaliy.melophile.di.module;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.GetTrack;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

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
}
