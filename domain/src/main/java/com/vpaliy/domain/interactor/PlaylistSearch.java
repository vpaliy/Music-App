package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.SearchRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

import android.support.annotation.Nullable;
import android.text.TextUtils;

public class PlaylistSearch extends SearchInteractor<List<Playlist>> {

  public PlaylistSearch(SearchRepository searchRepository,
                        BaseSchedulerProvider schedulerProvider) {
    super(searchRepository, schedulerProvider);
  }

  @Override
  public void search(@Nullable String query,
                     Consumer<? super List<Playlist>> onSuccess,
                     Consumer<? super Throwable> onError) {
    if (!TextUtils.isEmpty(query)) {
      searchRepository.searchPlaylist(query)
              .subscribeOn(schedulerProvider.io())
              .observeOn(schedulerProvider.ui())
              .subscribe(onSuccess, onError);
      return;
    }
    throw new IllegalArgumentException("Query can't be null or empty");
  }

  @Override
  public void nextPage(Consumer<? super List<Playlist>> onSuccess,
                       Consumer<? super Throwable> onError) {
    searchRepository.nextPlaylistPage()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }
}
