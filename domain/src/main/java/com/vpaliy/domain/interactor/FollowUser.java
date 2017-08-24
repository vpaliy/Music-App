package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;

import io.reactivex.Completable;

public class FollowUser extends CompletableUseCase<String> {

    public FollowUser(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Completable buildCompletable(String s) {
        return null;
    }
}
