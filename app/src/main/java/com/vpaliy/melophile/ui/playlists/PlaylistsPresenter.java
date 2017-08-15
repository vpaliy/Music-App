package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.View;

@ViewScope
public class PlaylistsPresenter implements PlaylistsContract.Presenter{

    private GetPlaylists playlistsUseCase;
    private View view;

    @Inject
    public PlaylistsPresenter(GetPlaylists playlistsUseCase){
        this.playlistsUseCase = playlistsUseCase;
    }

    @Override
    public void start() {
        List<MelophileTheme> themes= Arrays.asList(MelophileTheme.create("Motivation","sport","motivation"),
                MelophileTheme.create("Sleeping","Dream","travel","road"),
                MelophileTheme.create("Relaxing","relax","relaxing","chills"),
                MelophileTheme.create("Chilling","chills","party","friends"),
                MelophileTheme.create("Working out","working out","sweet","moment"));
        for(MelophileTheme theme:themes){
            playlistsUseCase.execute(new DisposableSingleObserver<PlaylistSet>() {
                @Override
                public void onSuccess(PlaylistSet value) {
                    view.showPlaylists(value.getTheme().getTheme(),value.getPlaylists());
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            },theme);
        }
    }

    @Override
    public void stop() {
        playlistsUseCase.dispose();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }
}
