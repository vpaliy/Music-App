package com.vpaliy.melophile.ui.user.info;

import android.os.Bundle;

import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;

import java.util.List;

import javax.inject.Inject;

import android.support.annotation.NonNull;

public class FollowersFragment extends BaseInfoFragment<User> {

  public static FollowersFragment newInstance(Bundle args) {
    FollowersFragment fragment = new FollowersFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void showInfo(@NonNull List<User> users) {
    UserAdapter adapter = new UserAdapter(getContext(), rxBus);
    adapter.setData(users);
    data.setAdapter(adapter);
  }

  @Override
  protected void inject() {
    DaggerViewComponent.builder()
            .applicationComponent(App.appInstance().appComponent())
            .presenterModule(new PresenterModule())
            .build().inject(this);
  }

  @Override
  public void showEmpty() {
    title.setText(R.string.no_followers_message);
    handler.postDelayed(this::close, 2000);
  }

  @Override
  public void showTitle() {
    title.setText(R.string.followers_title);
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull UserInfoContract.Presenter<User> presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }
}
