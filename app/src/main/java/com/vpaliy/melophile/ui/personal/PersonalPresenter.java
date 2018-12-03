package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.Track;

import java.util.List;

import static com.vpaliy.melophile.ui.personal.PersonalContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;

import android.support.annotation.NonNull;

import javax.inject.Inject;

@ViewScope
public class PersonalPresenter implements PersonalContract.Presenter {

  private View view;
  private SingleInteractor<User, Void> myself;
  private SingleInteractor<List<Track>, Void> trackHistory;
  private SingleInteractor<List<Playlist>, Void> playlistHistory;

  @Inject
  public PersonalPresenter(SingleInteractor<Track, Void> trackHistory,
                           SingleInteractor<Playlist, Void> playlistHistory,
                           SingleInteractor<User, Void> myself) {

  }

  @Override
  public void attachView(@NonNull View view) {
    this.view = checkNotNull(view);
  }

  @Override
  public void start() {
    myself.execute(this::catchMyself, this::catchError, null);
    trackHistory.execute(this::catchTrackHistory, this::catchError, null);
  }

  @Override
  public void stop() {
    playlistHistory.dispose();
    trackHistory.dispose();
  }

  private void catchTrackHistory(List<Track> tracks) {
    if (tracks == null || tracks.isEmpty()) {
      view.showEmptyHistoryMessage();
      return;
    }
    view.showTrackHistory(tracks);
  }

  private void catchMyself(User user) {
    if (user != null) {
      view.showMyself(user);
    }
  }

  @Override
  public void clearTracks() {
    //trackHistoryUseCase.clearHistory();
  }

  private void catchError(Throwable ex) {
    view.showErrorMessage();
    ex.printStackTrace();
  }
}
