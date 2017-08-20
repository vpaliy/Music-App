package com.vpaliy.melophile.di.module;

import com.vpaliy.data.repository.MusicRepository;
import com.vpaliy.data.source.Source;
import com.vpaliy.data.source.remote.Filter;
import com.vpaliy.data.source.remote.RemoteSource;
import com.vpaliy.domain.repository.Repository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Singleton @Provides
    Repository repository(MusicRepository repository){
        return repository;
    }

    @Singleton @Provides
    Source remoteSource(RemoteSource remote){
        return remote;
    }

    @Singleton @Provides
    Filter filter(){
        return new Filter();
    }
}
