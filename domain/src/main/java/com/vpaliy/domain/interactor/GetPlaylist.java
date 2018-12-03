package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetPlaylist extends SingleInteractor<Playlist, String> {

  private Repository repository;

  @Inject
  public GetPlaylist(BaseSchedulerProvider schedulerProvider,
                     Repository repository) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<Playlist> buildUseCase(String id) {
    if (id == null || id.isEmpty()) {
      return Single.error(new IllegalArgumentException("Id is null"));
    }
    return repository.getPlaylistBy(id);
  }
}
