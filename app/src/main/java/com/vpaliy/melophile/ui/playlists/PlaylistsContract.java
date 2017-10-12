package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface PlaylistsContract {
    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showPlaylists(@NonNull PlaylistSet playlistSet);
        void showLoading();
        void hideLoading();
        void showMessage(@StringRes int resource);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void start();
    }
}
