package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.base.adapters.LimitedTracksAdapter;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.vpaliy.melophile.ui.personal.PersonalContract.Presenter;

import android.support.annotation.Nullable;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;

public class PersonalFragment extends BaseFragment
        implements PersonalContract.View {

  private Presenter presenter;

  @BindView(R.id.personal_media)
  protected RecyclerView personalMedia;

  private PersonalAdapter adapter;

  @Override
  public void initializeDependencies() {
    DaggerViewComponent.builder()
            .applicationComponent(App.appInstance().appComponent())
            .presenterModule(new PresenterModule())
            .build().inject(this);
  }

  @Override
  protected int layoutId() {
    return R.layout.fragment_personal;
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (view != null) {
      adapter = new PersonalAdapter(getContext(), rxBus);
      personalMedia.setAdapter(adapter);
      presenter.start();
    }
  }

  @Override
  public void showEmptyHistoryMessage() {
    adapter.addItem(PersonalAdapter.CategoryWrapper.wrap(getString(R.string.no_played_history_label), null));
  }

  @Override
  public void showErrorMessage() {

  }

  @Override
  public void showTrackHistory(@NonNull List<Track> tracks) {
    LimitedTracksAdapter tracksAdapter = new LimitedTracksAdapter(getContext(), rxBus);
    tracksAdapter.setData(tracks);
    adapter.addItem(PersonalAdapter.CategoryWrapper.wrap(getString(R.string.track_history_label), tracksAdapter));
  }

  @Override
  public void showMyself(User user) {
    adapter.setUser(user);
  }
}
