package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;

import io.reactivex.Single;

public class GetTrack extends SingleUseCase<Track,Void> {

    public GetTrack(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Single<Track> buildUseCase(Void aVoid) {
        return null;
    }
}
