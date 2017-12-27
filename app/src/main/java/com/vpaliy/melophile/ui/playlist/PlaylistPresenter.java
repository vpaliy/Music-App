package com.vpaliy.melophile.ui.playlist;

import com.vpaliy.domain.interactor.ModifyInteractor;
import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.Playlist;
import java.util.LinkedList;
import java.util.List;
import com.vpaliy.melophile.R;
import static com.vpaliy.melophile.ui.playlist.PlaylistContract.View;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;

@ViewScope
public class PlaylistPresenter implements PlaylistContract.Presenter {

    private SingleInteractor<Playlist,String> playlistInteractor;

    private View view;
    private Playlist playlist;

    @Inject
    public PlaylistPresenter(SingleInteractor<Playlist,String> playlistUseCase){
        this.playlistInteractor =playlistUseCase;
    }

    @Override
    public void start(String id) {
        playlistInteractor.execute(this::catchData,this::catchError,id);
    }

    private void catchData(Playlist playlist){
        if(playlist!=null){
            this.playlist=playlist;
            List<String> tags=tags(playlist);
            if(!tags.isEmpty()) {
                view.showTags(tags);
            }
            view.showButtons();
            view.showUser(playlist.getUser());
            view.showTitle(playlist.getTitle());
            view.showTrackNumber(playlist.getTrackCount());
            view.showTracks(playlist.getTracks());
            view.showDuration(playlist.getDuration());
            view.showPlaylistArt(playlist.getArtUrl());
        }else{
            view.showMessage(R.string.empty_message);
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

    }

    @Override
    public void stop() {
        playlistInteractor.dispose();
    }

    @Override
    public void like() {
    }

    @Override
    public void unlike() {

    }

    @Override
    public void save() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }
}
