package com.vpaliy.melophile.di.module;

import com.vpaliy.data.repository.MusicPersonalRepository;
import com.vpaliy.data.repository.MusicRepository;
import com.vpaliy.data.repository.MusicSearchRepository;
import com.vpaliy.data.source.LocalSource;
import com.vpaliy.data.source.RemoteSource;
import com.vpaliy.data.source.SearchSource;
import com.vpaliy.data.source.local.LocalMusicSource;
import com.vpaliy.data.source.remote.Filter;
import com.vpaliy.data.source.remote.MusicRemoteSource;
import com.vpaliy.data.source.remote.RemoteSearchSource;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.domain.repository.SearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
  @Singleton
  @Provides
  Repository repository(MusicRepository repository) {
    return repository;
  }

  @Singleton
  @Provides
  RemoteSource remoteSource(MusicRemoteSource remote) {
    return remote;
  }

  @Singleton
  @Provides
  Filter filter() {
    return new Filter();
  }

  @Singleton
  @Provides
  SearchRepository searchRepository(MusicSearchRepository repository) {
    return repository;
  }

  @Singleton
  @Provides
  SearchSource searchSource(RemoteSearchSource remote) {
    return remote;
  }

  @Singleton
  @Provides
  PersonalRepository personalRepository(MusicPersonalRepository repository) {
    return repository;
  }

  @Singleton
  @Provides
  LocalSource localSource(LocalMusicSource local) {
    return local;
  }

}
