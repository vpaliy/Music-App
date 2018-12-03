package com.vpaliy.melophile.ui.tracks;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.playlists.CategoryAdapter;

import android.widget.ProgressBar;

import static com.vpaliy.melophile.ui.tracks.TracksContract.Presenter;

import butterknife.BindView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public class TracksFragment extends BaseFragment
        implements TracksContract.View {

  @BindView(R.id.categories)
  protected RecyclerView categories;

  @BindView(R.id.progress_bar)
  protected ProgressBar progressBar;

  private CategoryAdapter adapter;
  private Presenter presenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_music, container, false);
    bind(root);
    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (view != null) {
      adapter = new CategoryAdapter(getContext(), rxBus);
      categories.setAdapter(adapter);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (presenter != null) {
      if (adapter.getItemCount() == 0) {
        presenter.start();
      }
    }
  }

  @Override
  public void showErrorMessage() {
    showMessage(R.string.error_message);
  }

  @Override
  public void showEmptyMessage() {
    showMessage(R.string.empty_message);
  }

  @Override
  public void showTrackSet(@NonNull TrackSet trackSet) {
    TracksAdapter tracksAdapter = new TracksAdapter(getContext(), rxBus);
    tracksAdapter.setData(trackSet.getTracks());
    adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(trackSet.getThemeString(), tracksAdapter, 0));
  }

  @Override
  public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }

  @Override
  public void initializeDependencies() {
    DaggerViewComponent.builder()
            .presenterModule(new PresenterModule())
            .applicationComponent(App.appInstance().appComponent())
            .build().inject(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (presenter != null) {
      presenter.stop();
    }
  }
}
