package com.vpaliy.melophile.di.module;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.interactor.GetPlaylists;
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

}
