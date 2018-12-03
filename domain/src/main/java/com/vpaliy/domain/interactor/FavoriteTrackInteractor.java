package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class FavoriteTrackInteractor extends SingleInteractor<List<Track>, Void>
        implements ModifyInteractor<Track> {

  private PersonalRepository personalRepository;

  public FavoriteTrackInteractor(PersonalRepository personalRepository,
                                 BaseSchedulerProvider schedulerProvider) {
    super(schedulerProvider);
    this.personalRepository = personalRepository;
  }

  @Override
  public void add(Track track, Action onComplete, Consumer<? super Throwable> onError) {
    personalRepository.likeTrack(track)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onComplete, onError);
  }

  @Override
  public void remove(Track track, Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.unlikeTrack(track)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }

  @Override
  public void clear(Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.clearFavoriteTracks()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }

  @Override
  public Single<List<Track>> buildUseCase(Void aVoid) {
    return personalRepository.fetchFavoriteTracks();
  }
}
