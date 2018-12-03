package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.SearchRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

import android.text.TextUtils;
import android.support.annotation.Nullable;

public class TrackSearch extends SearchInteractor<List<Track>> {

  public TrackSearch(SearchRepository searchRepository,
                     BaseSchedulerProvider schedulerProvider) {
    super(searchRepository, schedulerProvider);
  }

  @Override
  public void search(@Nullable String query,
                     Consumer<? super List<Track>> onSuccess,
                     Consumer<? super Throwable> onError) {
    if (!TextUtils.isEmpty(query)) {
      searchRepository.searchTrack(query)
              .subscribeOn(schedulerProvider.io())
              .observeOn(schedulerProvider.ui())
              .subscribe(onSuccess, onError);
      return;
    }
    throw new IllegalArgumentException("Query can't be null or empty");
  }

  @Override
  public void nextPage(Consumer<? super List<Track>> onSuccess,
                       Consumer<? super Throwable> onError) {
    searchRepository.nextTrackPage()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }
}
