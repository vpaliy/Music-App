package com.vpaliy.melophile.ui.user;

import android.support.annotation.NonNull;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

public interface PersonContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showAvatar(String avatarUrl);
        void showTracks(List<Track> tracks);
        void showPlaylists(List<Playlist> playlists);
        void showDescription(String description);
        void showLoading();
        void hideLoading();
        void showFollowers(List<User> followers);
        void showTitle(String title);
        void showFollowersCount(int count);
        void showLikedCount(int count);
        void showEmptyMessage();
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void requestFollowers(String id);
        void start(String id);
    }
}
