package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.PersonalRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class HistoryPlaylistInteractor extends SingleInteractor<List<Playlist>, Void>
        implements ModifyInteractor<Playlist> {

  private PersonalRepository personalRepository;

  public HistoryPlaylistInteractor(PersonalRepository personalRepository,
                                   BaseSchedulerProvider schedulerProvider) {
    super(schedulerProvider);
    this.personalRepository = personalRepository;
  }

  @Override
  public void clear(Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.clearPlaylists()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }

  @Override
  public void remove(Playlist playlist, Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.removePlaylist(playlist)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }

  @Override
  public void add(Playlist playlist, Action onComplete, Consumer<? super Throwable> onError) {
    personalRepository.savePlaylist(playlist)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onComplete, onError);
  }

  @Override
  public Single<List<Playlist>> buildUseCase(Void aVoid) {
    return personalRepository.fetchPlaylistHistory();
  }
}
