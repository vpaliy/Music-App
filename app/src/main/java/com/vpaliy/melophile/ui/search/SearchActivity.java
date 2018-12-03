package com.vpaliy.melophile.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.adapters.PlaylistsAdapter;
import com.vpaliy.melophile.ui.base.adapters.TracksAdapter;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.user.info.UserAdapter;
import com.vpaliy.melophile.ui.view.TransitionAdapterListener;

import java.util.List;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import butterknife.ButterKnife;

import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import static com.vpaliy.melophile.ui.search.SearchContract.Presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import android.support.annotation.TransitionRes;

public class SearchActivity extends BaseActivity
        implements SearchContract.View {

  private Presenter presenter;
  private SearchAdapter searchAdapter;

  @BindView(R.id.search_view)
  protected SearchView searchView;

  @BindView(R.id.pager)
  protected ViewPager pager;

  @BindView(R.id.progress)
  protected ProgressBar progressBar;

  @BindView(R.id.tabs)
  protected TabLayout tabs;

  @BindView(R.id.back)
  protected View back;

  @BindView(R.id.root)
  protected ViewGroup root;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    setupSearch();
    setupPager();
    onNewIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    if (intent.hasExtra(SearchManager.QUERY)) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      if (!TextUtils.isEmpty(query)) {
        searchView.setQuery(query, false);
        searchView.clearFocus();
        hideKeyboard();
        presenter.query(query);
      }
    }
  }

  @OnClick(R.id.back)
  public void close() {
    clear(true);
  }

  @Override
  public void onBackPressed() {
    clear(true);
  }

  private void setupPager() {
    searchAdapter = new SearchAdapter(this, getSupportFragmentManager());
    pager.setOffscreenPageLimit(4);
    pager.setAdapter(searchAdapter);
    tabs.setupWithViewPager(pager);
  }

  private void setupSearch() {
    SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setQueryHint(getString(R.string.search_hint));
    searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
            EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        presenter.query(query);
        progressBar.setVisibility(View.VISIBLE);
        searchView.clearFocus();
        hideKeyboard();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String query) {
        if (TextUtils.isEmpty(query)) {
          clear(false);
        }
        return true;
      }
    });
  }

  @Override
  public void inject() {
    DaggerViewComponent.builder()
            .presenterModule(new PresenterModule())
            .applicationComponent(App.appInstance().appComponent())
            .build().inject(this);
  }

  @Override
  public void handleEvent(@NonNull Object event) {
    if (event instanceof ExposeEvent) {
      navigator.navigate(this, (ExposeEvent) (event));
    } else if (event instanceof MoreEvent) {
      handleRequest((MoreEvent) event);
    }
  }

  private void handleRequest(MoreEvent event) {
    switch (searchAdapter.getType(event.result)) {
      case SearchAdapter.TYPE_TRACKS:
        presenter.moreTracks();
        break;
      case SearchAdapter.TYPE_PLAYLISTS:
        presenter.morePlaylists();
        break;
      case SearchAdapter.TYPE_USERS:
        presenter.moreUsers();
        break;
    }
  }

  private void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  private void clear(boolean finish) {
    if (pager.getVisibility() != View.GONE) {
      Transition transition = getTransition(R.transition.search_hide_result);
      transition.addListener(new TransitionAdapterListener() {
        @Override
        public void onTransitionEnd(Transition transition) {
          super.onTransitionEnd(transition);
          if (finish) {
            finishAfterTransition();
          }
        }
      });
      TransitionManager.beginDelayedTransition(root, transition);
      pager.setVisibility(View.GONE);
      tabs.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (presenter != null) {
      presenter.stop();
    }
  }

  private void gotResult() {
    progressBar.setVisibility(View.GONE);
    if (pager.getVisibility() != View.VISIBLE) {
      TransitionManager.beginDelayedTransition(root, getTransition(R.transition.search_show_result));
      tabs.setVisibility(View.VISIBLE);
      pager.setVisibility(View.VISIBLE);
    }
  }

  private Transition getTransition(@TransitionRes int transitionId) {
    TransitionInflater inflater = TransitionInflater.from(this);
    return inflater.inflateTransition(transitionId);
  }

  @Override
  @Inject
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }

  @Override
  public void showTracks(@NonNull List<Track> tracks) {
    gotResult();
    TracksAdapter adapter = new TracksAdapter(this, eventBus);
    adapter.setData(tracks);
    searchAdapter.setTracks(adapter);
  }

  @Override
  public void showPlaylists(@NonNull List<Playlist> playlists) {
    gotResult();
    PlaylistsAdapter adapter = new PlaylistsAdapter(this, eventBus);
    adapter.setData(playlists);
    searchAdapter.setPlaylists(adapter);
  }

  @Override
  public void showUsers(@NonNull List<User> users) {
    gotResult();
    UserAdapter adapter = new UserAdapter(this, eventBus, false);
    adapter.setData(users);
    searchAdapter.setUsers(adapter);
  }

  @Override
  public void appendPlaylists(@NonNull List<Playlist> playlists) {
    gotResult();
    searchAdapter.appendPlaylists(playlists);
  }

  @Override
  public void appendTracks(@NonNull List<Track> tracks) {
    gotResult();
    searchAdapter.appendTracks(tracks);
  }

  @Override
  public void appendUsers(@NonNull List<User> users) {
    gotResult();
    searchAdapter.appendUsers(users);
  }

  @Override
  public void showEmptyMessage() {
    Snackbar.make(root, R.string.empty_message,
            getResources().getInteger(R.integer.message_duration));
  }

  @Override
  public void showErrorMessage() {
    Snackbar.make(root, R.string.error_message,
            getResources().getInteger(R.integer.message_duration));
  }
}
