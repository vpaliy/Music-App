package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetPlaylists extends SingleInteractor<PlaylistSet, MelophileTheme> {

  private Repository repository;

  @Inject
  public GetPlaylists(BaseSchedulerProvider schedulerProvider,
                      Repository repository) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<PlaylistSet> buildUseCase(MelophileTheme theme) {
    if (theme != null) {
      return repository.getPlaylistsBy(theme)
              .map(list -> new PlaylistSet(theme, list));
    }
    return Single.error(new IllegalArgumentException("Melophile theme is null!"));
  }
}
