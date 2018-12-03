package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

import android.support.annotation.NonNull;

public interface PersonalContract {
  interface View extends BaseView<Presenter> {
    void attachPresenter(@NonNull Presenter presenter);

    void showTrackHistory(@NonNull List<Track> tracks);

    void showMyself(User user);

    void showEmptyHistoryMessage();

    void showErrorMessage();
  }

  interface Presenter extends BasePresenter<View> {
    void attachView(@NonNull View view);

    void start();

    void stop();

    void clearTracks();
  }
}
