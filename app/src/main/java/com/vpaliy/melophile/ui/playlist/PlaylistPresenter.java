package com.vpaliy.melophile.ui.playlist;

import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.SaveInteractor;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.vpaliy.melophile.ui.playlist.PlaylistContract.View;

@ViewScope
public class PlaylistPresenter implements PlaylistContract.Presenter {

    private GetPlaylist playlistUseCase;
    private SaveInteractor saveInteractor;
    private View view;

    @Inject
    public PlaylistPresenter(GetPlaylist playlistUseCase, SaveInteractor saveInteractor){
        this.playlistUseCase=playlistUseCase;
        this.saveInteractor=saveInteractor;
    }

    @Override
    public void start(String id) {
        playlistUseCase.execute(this::catchData,this::catchError,id);
    }

    private void catchData(Playlist playlist){
        if(playlist!=null){
            saveInteractor.savePlaylist(playlist);
            List<String> tags=tags(playlist);
            if(!tags.isEmpty()) {
                view.showTags(tags);
            }
            view.showTitle(playlist.getTitle());
            view.showButtons();
            view.showUser(playlist.getUser());
            view.showTrackNumber(playlist.getTrackCount());
            view.showTracks(playlist.getTracks());
            view.showDuration(playlist.getDuration());
            view.showPlaylistArt(playlist.getArtUrl());
        }else{
            view.showEmptyMessage();
        }
    }

    private List<String> tags(Playlist playlist){
        List<String> list=new LinkedList<>();
        if(playlist.getTags()!=null){
            list.addAll(playlist.getTags());
        }
        //get the genres
        if(playlist.getGenres()!=null){
            list.addAll(playlist.getGenres());
        }
        return list;
    }
    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();
    }

    @Override
    public void stop() {
        playlistUseCase.dispose();
        view=null;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }
}
