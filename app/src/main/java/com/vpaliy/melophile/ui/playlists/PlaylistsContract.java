package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;
import java.util.List;
import android.support.annotation.NonNull;

public class PlaylistsContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showPlaylists(@NonNull String category, @NonNull List<Playlist> playlists);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
    }
}
