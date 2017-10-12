package com.vpaliy.melophile.ui.user.info;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

public interface UserInfoContract {
    interface View<T> extends BaseView<Presenter<T>> {
        void attachPresenter(Presenter<T> presenter);
        void showMessage(@StringRes int resource);
        void showEmptyState();
        void finishWithDelay(long delay);
        void showInfo(List<T> tracks);
        void showTitle();
    }

    interface Presenter<T> extends BasePresenter<View<T>> {
        void attachView(@NonNull View<T> view);
        void stop();
        void start(String id);
    }
}
