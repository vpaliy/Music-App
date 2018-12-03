package com.vpaliy.domain.interactor;

import android.text.TextUtils;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.Repository;

import java.util.List;

import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetUserFavorites extends SingleInteractor<List<Track>, String> {

  private Repository repository;

  @Inject
  public GetUserFavorites(Repository repository, BaseSchedulerProvider schedulerProvider) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<List<Track>> buildUseCase(String id) {
    if (!TextUtils.isEmpty(id)) {
      return repository.getUserFavorites(id);
    }
    return Single.error(new IllegalArgumentException("Id is null"));
  }
}
