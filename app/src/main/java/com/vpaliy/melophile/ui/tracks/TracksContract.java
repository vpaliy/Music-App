package com.vpaliy.melophile.ui.tracks;

import android.support.annotation.NonNull;

import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

public interface TracksContract {
  interface View extends BaseView<Presenter> {
    void attachPresenter(@NonNull Presenter presenter);

    void showTrackSet(@NonNull TrackSet trackSet);

    void showErrorMessage();

    void showEmptyMessage();

    void showLoading();

    void hideLoading();
  }

  interface Presenter extends BasePresenter<View> {
    void attachView(@NonNull View view);

    void stop();

    void start();
  }
}
