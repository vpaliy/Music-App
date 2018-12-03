package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.repository.SearchRepository;

import io.reactivex.functions.Consumer;

import android.support.annotation.Nullable;

public abstract class SearchInteractor<Params> {

  protected BaseSchedulerProvider schedulerProvider;
  protected SearchRepository searchRepository;

  public SearchInteractor(SearchRepository searchRepository,
                          BaseSchedulerProvider schedulerProvider) {
    this.searchRepository = searchRepository;
    this.schedulerProvider = schedulerProvider;
  }

  public abstract void search(@Nullable String query,
                              Consumer<? super Params> onSuccess,
                              Consumer<? super Throwable> onError);

  public abstract void nextPage(Consumer<? super Params> onSuccess,
                                Consumer<? super Throwable> onError);
}
