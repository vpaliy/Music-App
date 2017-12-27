package com.vpaliy.melophile.ui.search;

import com.vpaliy.domain.interactor.SearchInteractor;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import java.util.List;
import com.vpaliy.melophile.R;

import static com.vpaliy.melophile.ui.search.SearchContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;

@ViewScope
public class SearchPresenter implements SearchContract.Presenter {

    private SearchInteractor<List<Track>> trackSearch;
    private SearchInteractor<List<Playlist>> playlistSearch;
    private SearchInteractor<List<User>> userSearch;
    private View view;

    @Inject
    public SearchPresenter(SearchInteractor<List<Track>> trackSearch,
                           SearchInteractor<List<Playlist>> playlistSearch,
                           SearchInteractor<List<User>> userSearch){
        this.trackSearch = trackSearch;
        this.playlistSearch = playlistSearch;
        this.userSearch = userSearch;
    }

    @Override
    public void attachView(View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void query(String query) {
        trackSearch.search(query,this::catchTracks,this::catchError);
        playlistSearch.search(query,this::catchPlaylists,this::catchError);
        userSearch.search(query,this::catchUsers,this::catchError);
    }

    private void catchTracks(List<Track> result){
        if(result!=null){
            view.showTracks(result);
        }
    }

    private void catchPlaylists(List<Playlist> playlists){
        if(playlists!=null){
            view.showPlaylists(playlists);
        }
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showMessage(R.string.error_message);
    }

    private void catchUsers(List<User> result){
        if(result!=null){
            view.showUsers(result);
        }
    }

    @Override
    public void nextPlaylistPage() {
        playlistSearch.nextPage(this::appendPlaylists,this::catchError);
    }

    @Override
    public void nextTrackPage() {
        trackSearch.nextPage(this::appendTracks,this::catchError);
    }

    @Override
    public void nextUserPage() {
        userSearch.nextPage(this::appendUsers,this::catchError);
    }

    private void appendTracks(List<Track> tracks){
        if(tracks==null||tracks.isEmpty()) return;
        view.updateTrackPage(tracks);
    }

    private void appendPlaylists(List<Playlist> playlists){
        if(playlists==null||playlists.isEmpty()) return;
        view.updatePlaylistPage(playlists);
    }

    private void appendUsers(List<User> users){
        if(users==null||users.isEmpty()) return;
        view.updateUserPage(users);
    }

    @Override
    public void stop() {
        view=null;
    }
}
