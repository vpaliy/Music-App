package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.interactor.GetRecentPlaylists;
import com.vpaliy.domain.interactor.GetRecentTracks;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import java.util.List;
import static com.vpaliy.melophile.ui.personal.PersonalContract.View;
import static dagger.internal.Preconditions.checkNotNull;
import com.vpaliy.melophile.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;

@ViewScope
public class PersonalPresenter implements PersonalContract.Presenter{

    private View view;
    private GetRecentPlaylists playlistHistoryUseCase;
    private GetRecentTracks trackHistoryUseCase;

    @Inject
    public PersonalPresenter(GetRecentTracks trackHistoryUseCase,
                             GetRecentPlaylists playlistHistoryUseCase){
        this.trackHistoryUseCase=trackHistoryUseCase;
        this.playlistHistoryUseCase=playlistHistoryUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void start() {
        trackHistoryUseCase.execute(this::catchTrackHistory,this::catchError,null);
        playlistHistoryUseCase.execute(this::catchPlaylistHistory,this::catchError,null);
    }

    @Override
    public void stop() {
        playlistHistoryUseCase.dispose();
        trackHistoryUseCase.dispose();
    }

    private void catchPlaylistHistory(List<Playlist> playlistList){
        if(playlistList==null||playlistList.isEmpty()){
            //show empty view
            return;
        }
        view.showPlaylistHistory(playlistList);
    }

    private void catchTrackHistory(List<Track> tracks){
        if(tracks==null||tracks.isEmpty()){
            //TODO show empty
            return;
        }
        view.showTrackHistory(tracks);
    }

    private void catchError(Throwable ex){
        //TODO view show an error message
        ex.printStackTrace();
    }
}
