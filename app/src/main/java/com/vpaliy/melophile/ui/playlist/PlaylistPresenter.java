package com.vpaliy.melophile.ui.playlist;

import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;
import android.support.annotation.NonNull;

import static com.vpaliy.melophile.ui.playlist.PlaylistContract.View;

@ViewScope
public class PlaylistPresenter implements PlaylistContract.Presenter {

    private GetPlaylist playlistUseCase;
    private View view;

    @Inject
    public PlaylistPresenter(GetPlaylist playlistUseCase){
        this.playlistUseCase=playlistUseCase;
    }

    @Override
    public void start(String id) {
        playlistUseCase.execute(this::catchData,this::catchError,id);
    }

    private void catchData(Playlist playlist){
        if(playlist!=null){
            view.showPlaylistArt(playlist.getArtUrl());
            view.showTracks(playlist.getTracks());
            view.showUser(playlist.getUser());
        }else{
            view.showEmptyMessage();
        }
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();
    }

    @Override
    public void stop() {
        playlistUseCase.dispose();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }
}
