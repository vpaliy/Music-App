package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.interactor.GetMe;
import com.vpaliy.domain.interactor.PlaylistHistory;
import com.vpaliy.domain.interactor.TrackHistory;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import java.util.List;
import static com.vpaliy.melophile.ui.personal.PersonalContract.View;
import static dagger.internal.Preconditions.checkNotNull;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;

@ViewScope
public class PersonalPresenter implements PersonalContract.Presenter{

    private View view;
    private PlaylistHistory playlistHistoryUseCase;
    private TrackHistory trackHistoryUseCase;
    private GetMe fetchMeUseCase;

    @Inject
    public PersonalPresenter(TrackHistory trackHistoryUseCase,
                             PlaylistHistory playlistHistoryUseCase,
                             GetMe fetchMeUseCase){
        this.trackHistoryUseCase=trackHistoryUseCase;
        this.playlistHistoryUseCase=playlistHistoryUseCase;
        this.fetchMeUseCase=fetchMeUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void start() {
        fetchMeUseCase.execute(this::catchMyself,this::catchError,null);
        trackHistoryUseCase.execute(this::catchTrackHistory,this::catchError,null);
    }

    @Override
    public void stop() {
        playlistHistoryUseCase.dispose();
        trackHistoryUseCase.dispose();
    }

    private void catchTrackHistory(List<Track> tracks){
        if(tracks==null||tracks.isEmpty()){
            view.showEmptyHistoryMessage();
            return;
        }
        view.showTrackHistory(tracks);
    }

    private void catchMyself(User user){
        if(user!=null){
            view.showMyself(user);
        }
    }

    @Override
    public void clearTracks() {
        trackHistoryUseCase.clearHistory();
    }

    private void catchError(Throwable ex){
        view.showErrorMessage();
        ex.printStackTrace();
    }
}
