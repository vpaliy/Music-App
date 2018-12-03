package com.vpaliy.melophile.ui.user.info;

import android.os.Bundle;

import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.adapters.TracksAdapter;

import java.util.List;

import javax.inject.Inject;

import android.support.annotation.NonNull;

public class FavoriteFragment extends BaseInfoFragment<Track> {

  public static FavoriteFragment newInstance(Bundle args) {
    FavoriteFragment fragment = new FavoriteFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void showInfo(@NonNull List<Track> tracks) {
    TracksAdapter adapter = new TracksAdapter(getContext(), rxBus);
    adapter.setData(tracks);
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
    title.setText(R.string.no_loved_message);
    handler.postDelayed(this::close, 2000);
  }

  @Override
  public void showTitle() {
    title.setText(R.string.loved_title);
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull UserInfoContract.Presenter<Track> presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }
}
