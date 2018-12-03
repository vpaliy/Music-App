package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.Repository;

import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetTrack extends SingleInteractor<Track, String> {

  private Repository repository;

  @Inject
  public GetTrack(BaseSchedulerProvider schedulerProvider,
                  Repository repository) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<Track> buildUseCase(String id) {
    if (id == null || id.isEmpty()) {
      return Single.error(new IllegalArgumentException("Id is null"));
    }
    return repository.getTrackBy(id);
  }
}
