package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.PlaylistSet;

import java.util.Arrays;
import java.util.List;

import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;

import android.support.annotation.NonNull;

@ViewScope
public class PlaylistsPresenter implements PlaylistsContract.Presenter {

  private View view;
  private SingleInteractor<PlaylistSet, MelophileTheme> playlistInteractor;

  @Inject
  public PlaylistsPresenter(SingleInteractor<PlaylistSet, MelophileTheme> playlistsUseCase) {
    this.playlistInteractor = playlistsUseCase;
  }

  @Override
  public void start() {
    List<MelophileTheme> themes = Arrays.asList(
            MelophileTheme.create("Good Morning", "Good Morning", "2017", "Morning", "Coffee", "Tea", "Paris"),
            MelophileTheme.create("Sleeping", "Dream", "travel", "road"),
            MelophileTheme.create("Relaxing", "relax", "relaxing", "chills"),
            MelophileTheme.create("Chilling", "chills", "party", "friends"),
            MelophileTheme.create("Working out", "working out", "sweet", "moment"));
    for (MelophileTheme theme : themes) {
      playlistInteractor.execute(this::catchData, this::catchError, theme);
    }
  }

  private void catchData(PlaylistSet set) {
    if (set != null) {
      view.showPlaylists(set);
      return;
    }
    view.showEmptyMessage();
  }

  private void catchError(Throwable ex) {
    ex.printStackTrace();
    view.showErrorMessage();
  }

  @Override
  public void stop() {
    playlistInteractor.dispose();
  }

  @Override
  public void attachView(@NonNull View view) {
    this.view = checkNotNull(view);
  }
}
