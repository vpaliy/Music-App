package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;
import com.vpaliy.melophile.ui.base.bus.event.OnClick;

import android.support.annotation.NonNull;

public interface PlaylistsContract {
    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showPlaylists(@NonNull PlaylistSet playlistSet);
        void showPlaylist(@NonNull OnClick<Playlist> onClick);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void onPlaylistClicked(OnClick<Playlist> onClick);
        void stop();
        void start();
    }
}
