package com.vpaliy.melophile.ui.search;

import android.support.annotation.NonNull;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

public interface SearchContract {
  interface View extends BaseView<Presenter> {
    void attachPresenter(@NonNull Presenter presenter);

    void showTracks(@NonNull List<Track> tracks);

    void showPlaylists(@NonNull List<Playlist> playlists);

    void showUsers(@NonNull List<User> users);

    void appendUsers(@NonNull List<User> users);

    void appendPlaylists(@NonNull List<Playlist> playlists);

    void appendTracks(@NonNull List<Track> tracks);

    void showErrorMessage();

    void showEmptyMessage();
  }

  interface Presenter extends BasePresenter<View> {
    void attachView(@NonNull View view);

    void query(String query);

    void moreTracks();

    void morePlaylists();

    void moreUsers();

    void stop();
  }
}
