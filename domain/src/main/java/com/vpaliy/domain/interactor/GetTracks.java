package com.vpaliy.domain.interactor;


import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetTracks extends SingleInteractor<TrackSet, MelophileTheme> {

  private Repository repository;

  @Inject
  public GetTracks(BaseSchedulerProvider schedulerProvider,
                   Repository repository) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<TrackSet> buildUseCase(MelophileTheme theme) {
    if (theme != null) {
      return repository.getTracksBy(theme)
              .map(list -> new TrackSet(theme, list));
    }
    return Single.error(new IllegalArgumentException("Melophile theme is null!"));
  }
}
