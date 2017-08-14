package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.PlaylistType;
import com.vpaliy.domain.model.User;

import io.reactivex.Single;

public class GetUser extends SingleUseCase<User,Void>{

    public GetUser(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Single<User> buildUseCase(Void aVoid) {
        return null;
    }
}
