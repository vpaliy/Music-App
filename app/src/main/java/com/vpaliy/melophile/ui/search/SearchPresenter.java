package com.vpaliy.melophile.ui.search;

import com.vpaliy.domain.interactor.PlaylistSearch;
import com.vpaliy.domain.interactor.TrackSearch;
import com.vpaliy.domain.interactor.UserSearch;
import static com.vpaliy.melophile.ui.search.SearchContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.di.scope.ViewScope;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

@ViewScope
public class SearchPresenter implements SearchContract.Presenter {

    private TrackSearch trackSearchUseCase;
    private PlaylistSearch playlistSearchUseCase;
    private UserSearch userSearchUseCase;
    private View view;

    @Inject
    public SearchPresenter(TrackSearch trackSearchUseCase,
                           PlaylistSearch playlistSearchUseCase,
                           UserSearch userSearchUseCase){
        this.trackSearchUseCase=trackSearchUseCase;
        this.playlistSearchUseCase=playlistSearchUseCase;
        this.userSearchUseCase=userSearchUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void query(String query) {
        trackSearchUseCase.execute(this::catchTracks,this::catchError,query);
        playlistSearchUseCase.execute(this::catchPlaylists,this::catchError,query);
        userSearchUseCase.execute(this::catchUsers,this::catchError,query);
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
        view.showErrorMessage();
    }

    private void catchUsers(List<User> result){
        if(result!=null){
            view.showUsers(result);
        }
    }

    @Override
    public void morePlaylists() {
        playlistSearchUseCase.more(this::catchPlaylists,this::catchError);
    }

    @Override
    public void moreTracks() {
        trackSearchUseCase.more(this::catchTracks,this::catchError);
    }

    @Override
    public void moreUsers() {
        userSearchUseCase.more(this::catchUsers,this::catchError);
    }

    @Override
    public void stop() {
        trackSearchUseCase.dispose();
        playlistSearchUseCase.dispose();
        userSearchUseCase.dispose();
        view=null;
    }
}
