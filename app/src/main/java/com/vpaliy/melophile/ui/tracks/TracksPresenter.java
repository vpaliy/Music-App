package com.vpaliy.melophile.ui.tracks;

import android.support.annotation.NonNull;

import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;

@ViewScope
public class TracksPresenter implements TracksContract.Presenter {

    private GetTracks tracksUseCase;

    @Inject
    public TracksPresenter(GetTracks tracksUseCase){
        this.tracksUseCase=tracksUseCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void attachView(@NonNull TracksContract.View view) {

    }
}
