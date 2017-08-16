package com.vpaliy.melophile.ui.tracks;

import android.support.annotation.NonNull;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.melophile.di.scope.ViewScope;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import static com.vpaliy.melophile.ui.tracks.TracksContract.View;

@ViewScope
public class TracksPresenter implements TracksContract.Presenter {

    private GetTracks tracksUseCase;
    private View view;

    @Inject
    public TracksPresenter(GetTracks tracksUseCase){
        this.tracksUseCase=tracksUseCase;
    }

    @Override
    public void start() {
        List<MelophileTheme> themes= Arrays.asList(MelophileTheme.create("Top50","top50","best"),
                MelophileTheme.create("Sleeping","Dream","travel","road"),
                MelophileTheme.create("Relaxing","relax","relaxing","chills"),
                MelophileTheme.create("Chilling","chills","party","friends"),
                MelophileTheme.create("Working out","working out","sweet","moment"));
        for(MelophileTheme theme:themes){
            tracksUseCase.execute(this::catchData,this::catchError,theme);
        }
    }

    private void catchData(TrackSet trackSet){
        if(trackSet!=null){
            view.showTrackSet(trackSet);
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
        tracksUseCase.dispose();
    }

    @Override
    public void attachView(@NonNull TracksContract.View view) {
        this.view=view;
    }
}
