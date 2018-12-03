package com.vpaliy.melophile.ui.user.info;

import android.support.annotation.NonNull;

import com.vpaliy.melophile.ui.base.BasePresenter;
import com.vpaliy.melophile.ui.base.BaseView;

import java.util.List;

public interface UserInfoContract {
  interface View<T> extends BaseView<Presenter<T>> {
    void attachPresenter(@NonNull Presenter<T> presenter);

    void showError();

    void showEmpty();

    void showInfo(@NonNull List<T> tracks);

    void showTitle();
  }

  interface Presenter<T> extends BasePresenter<View<T>> {
    void attachView(@NonNull View<T> view);

    void stop();

    void start(String id);
  }
}
