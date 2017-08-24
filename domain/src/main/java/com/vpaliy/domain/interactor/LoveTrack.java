package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import io.reactivex.Completable;

public class LoveTrack extends CompletableUseCase<String> {

    public LoveTrack(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Completable buildCompletable(String s) {
        return null;
    }
}
