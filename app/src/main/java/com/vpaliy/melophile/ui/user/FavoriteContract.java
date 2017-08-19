package com.vpaliy.melophile.ui.user;

import android.support.annotation.NonNull;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;
import java.util.List;

public interface FavoriteContract {
    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showError();
        void showEmpty();
        void showTracks(@NonNull List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void start(String id);
    }
}
