package com.vpaliy.melophile.ui.tracks;

import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.TrackSet;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import android.support.annotation.NonNull;

import com.vpaliy.melophile.di.scope.ViewScope;

import static com.vpaliy.melophile.ui.tracks.TracksContract.View;

@ViewScope
public class TracksPresenter implements TracksContract.Presenter {

  private View view;
  private SingleInteractor<TrackSet, MelophileTheme> trackInteractor;

  @Inject
  public TracksPresenter(SingleInteractor<TrackSet, MelophileTheme> trackInteractor) {
    this.trackInteractor = trackInteractor;
  }

  @Override
  public void start() {
    List<MelophileTheme> themes = Arrays.asList(
            MelophileTheme.create("Top50", "top50", "best"),
            MelophileTheme.create("Sleeping", "Dream", "travel", "road"),
            MelophileTheme.create("Relaxing", "relax", "relaxing", "chills"),
            MelophileTheme.create("Chilling", "chills", "party", "friends"),
            MelophileTheme.create("Working out", "working out", "sweet", "moment"));
    for (MelophileTheme theme : themes) {
      trackInteractor.execute(this::catchData, this::catchError, theme);
    }
  }

  private void catchData(TrackSet trackSet) {
    if (trackSet != null) {
      view.showTrackSet(trackSet);
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
    trackInteractor.dispose();
  }

  @Override
  public void attachView(@NonNull TracksContract.View view) {
    this.view = view;
  }
}
