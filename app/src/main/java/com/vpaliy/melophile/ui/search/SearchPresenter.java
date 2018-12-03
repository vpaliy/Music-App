package com.vpaliy.melophile.ui.search;

import com.vpaliy.domain.interactor.SearchInteractor;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import java.util.List;

import static com.vpaliy.melophile.ui.search.SearchContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;

import android.support.annotation.NonNull;

import javax.inject.Inject;

@ViewScope
public class SearchPresenter implements SearchContract.Presenter {

  private SearchInteractor<List<Track>> trackSearchUseCase;
  private SearchInteractor<List<Playlist>> playlistSearchUseCase;
  private SearchInteractor<List<User>> userSearchUseCase;
  private View view;

  @Inject
  public SearchPresenter(SearchInteractor<List<Track>> trackSearchUseCase,
                         SearchInteractor<List<Playlist>> playlistSearchUseCase,
                         SearchInteractor<List<User>> userSearchUseCase) {
    this.trackSearchUseCase = trackSearchUseCase;
    this.playlistSearchUseCase = playlistSearchUseCase;
    this.userSearchUseCase = userSearchUseCase;
  }

  @Override
  public void attachView(@NonNull View view) {
    this.view = checkNotNull(view);
  }

  @Override
  public void query(String query) {
    trackSearchUseCase.search(query, this::catchTracks, this::catchError);
    playlistSearchUseCase.search(query, this::catchPlaylists, this::catchError);
    userSearchUseCase.search(query, this::catchUsers, this::catchError);
  }

  private void catchTracks(List<Track> result) {
    if (result != null) {
      view.showTracks(result);
    }
  }

  private void catchPlaylists(List<Playlist> playlists) {
    if (playlists != null) {
      view.showPlaylists(playlists);
    }
  }

  private void catchError(Throwable ex) {
    ex.printStackTrace();
    view.showErrorMessage();
  }

  private void catchUsers(List<User> result) {
    if (result != null) {
      view.showUsers(result);
    }
  }

  @Override
  public void morePlaylists() {
    playlistSearchUseCase.nextPage(this::appendPlaylists, this::catchError);
  }

  @Override
  public void moreTracks() {
    trackSearchUseCase.nextPage(this::appendTracks, this::catchError);
  }

  @Override
  public void moreUsers() {
    userSearchUseCase.nextPage(this::appendUsers, this::catchError);
  }

  private void appendTracks(List<Track> tracks) {
    if (tracks == null || tracks.isEmpty()) return;
    view.appendTracks(tracks);
  }

  private void appendPlaylists(List<Playlist> playlists) {
    if (playlists == null || playlists.isEmpty()) return;
    view.showPlaylists(playlists);
  }

  private void appendUsers(List<User> users) {
    if (users == null || users.isEmpty()) return;
    view.showUsers(users);
  }

  @Override
  public void stop() {
       /* trackSearchUseCase.dispose();
        playlistSearchUseCase.dispose();
        userSearchUseCase.dispose();    */
    view = null;
  }
}
