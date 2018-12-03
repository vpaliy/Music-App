package com.vpaliy.melophile.ui.user.info;

import java.util.List;

import static com.vpaliy.melophile.ui.user.info.UserInfoContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.domain.interactor.SingleInteractor;

import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public abstract class UserInfoPresenter<T> implements UserInfoContract.Presenter<T> {

  private View<T> view;
  private SingleInteractor<List<T>, String> userInfo;

  public UserInfoPresenter(SingleInteractor<List<T>, String> userInfo) {
    this.userInfo = userInfo;
  }

  @Override
  public void start(String id) {
    userInfo.execute(this::catchData, this::catchError, id);
  }

  @Override
  public void stop() {
    userInfo.dispose();
  }

  @Override
  public void attachView(@NonNull View<T> view) {
    this.view = checkNotNull(view);
  }

  protected void catchData(List<T> data) {
    if (data == null || data.isEmpty()) {
      view.showEmpty();
    } else {
      view.showTitle();
      view.showInfo(data);
    }
  }

  protected void catchError(Throwable ex) {
    ex.printStackTrace();
    view.showError();
  }
}
