package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.Repository;

import java.util.List;

import io.reactivex.Single;

public class GetUsers extends SingleInteractor<List<User>, String> {

  private Repository repository;

  public GetUsers(BaseSchedulerProvider schedulerProvider,
                  Repository repository) {
    super(schedulerProvider);
    this.repository = repository;
  }

  @Override
  public Single<List<User>> buildUseCase(String tag) {
    return null;
  }
}
