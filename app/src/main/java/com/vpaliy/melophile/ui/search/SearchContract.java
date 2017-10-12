package com.vpaliy.melophile.ui.search;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;
import java.util.List;
import android.support.annotation.StringRes;

public interface SearchContract  {
    interface View extends BaseView<Presenter> {
        void attachPresenter(Presenter presenter);
        void showTracks(List<Track> tracks);
        void showPlaylists(List<Playlist> playlists);
        void showUsers(List<User> users);
        void updateUserPage(List<User> users);
        void updatePlaylistPage(List<Playlist> playlists);
        void updateTrackPage(List<Track> tracks);
        void showMessage(@StringRes int resource);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(View view);
        void query(String query);
        void nextTrackPage();
        void nextPlaylistPage();
        void nextUserPage();
        void stop();
    }
}
