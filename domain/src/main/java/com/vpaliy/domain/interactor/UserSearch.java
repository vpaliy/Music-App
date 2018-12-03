package com.vpaliy.domain.interactor;

import android.text.TextUtils;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.SearchRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

import android.support.annotation.Nullable;

public class UserSearch extends SearchInteractor<List<User>> {

  public UserSearch(SearchRepository searchRepository,
                    BaseSchedulerProvider schedulerProvider) {
    super(searchRepository, schedulerProvider);
  }

  @Override
  public void search(@Nullable String query,
                     Consumer<? super List<User>> onSuccess,
                     Consumer<? super Throwable> onError) {
    if (!TextUtils.isEmpty(query)) {
      searchRepository.searchUser(query)
              .subscribeOn(schedulerProvider.io())
              .observeOn(schedulerProvider.ui())
              .subscribe(onSuccess, onError);
    }
  }

  @Override
  public void nextPage(Consumer<? super List<User>> onSuccess,
                       Consumer<? super Throwable> onError) {
    searchRepository.nextUserPage()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }
}
