package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import android.support.annotation.NonNull;

public interface PlaylistsContract {
  interface View extends BaseView<Presenter> {
    void attachPresenter(@NonNull Presenter presenter);

    void showPlaylists(@NonNull PlaylistSet playlistSet);

    void showLoading();

    void hideLoading();

    void showErrorMessage();

    void showEmptyMessage();
  }

  interface Presenter extends BasePresenter<View> {
    void attachView(@NonNull View view);

    void stop();

    void start();
  }
}
