package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.ui.base.bus.event.OnClick;
import java.util.Arrays;
import java.util.List;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.View;
import static dagger.internal.Preconditions.checkNotNull;

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
            playlistsUseCase.execute(this::catchData,this::catchError,theme);
        }
    }

    private void catchData(PlaylistSet set){
        if(set!=null){
            view.showPlaylists(set);
            return;
        }
        view.showEmptyMessage();
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();
    }


    @Override
    public void stop() {
        playlistsUseCase.dispose();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }
}
