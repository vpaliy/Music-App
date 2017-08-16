package com.vpaliy.melophile.ui.playlist;

import android.support.annotation.NonNull;

import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

public interface PlaylistContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showPlaylistArt(String artUrl);
        void showTracks(List<Track> tracks);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void start(String id);
    }
}
