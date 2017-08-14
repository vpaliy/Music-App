package com.vpaliy.domain.interactor;


import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;

import java.util.List;

import io.reactivex.Single;

public class GetTracks extends SingleUseCase<List<Track>,Void>{

    public GetTracks(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Single<List<Track>> buildUseCase(Void aVoid) {
        return null;
    }
}
