package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;

import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.Presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import butterknife.BindView;

import javax.inject.Inject;

public class PlaylistsFragment extends BaseFragment
        implements PlaylistsContract.View {

  private Presenter presenter;
  private CategoryAdapter adapter;

  @BindView(R.id.categories)
  protected RecyclerView categories;

  @BindView(R.id.progress_bar)
  protected ProgressBar progressBar;

  @Override
  protected int layoutId() {
    return R.layout.fragment_music;
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
  public void showEmptyMessage() {
    showMessage(R.string.empty_message);
  }

  @Override
  public void showErrorMessage() {
    showMessage(R.string.error_message);
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
  public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showPlaylists(@NonNull PlaylistSet playlistSet) {
    PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext(), rxBus);
    playlistAdapter.setData(playlistSet.getPlaylists());
    adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(playlistSet.getThemeString(), playlistAdapter, 1));
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    presenter.attachView(this);
  }

  @Override
  public void initializeDependencies() {
    DaggerViewComponent.builder()
            .applicationComponent(App.appInstance().appComponent())
            .presenterModule(new PresenterModule())
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
