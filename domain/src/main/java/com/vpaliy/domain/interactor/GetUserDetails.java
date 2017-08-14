package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.UserDetails;
import io.reactivex.Single;

public class GetUserDetails extends SingleUseCase<UserDetails,Integer> {

    public GetUserDetails(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Single<UserDetails> buildUseCase(Integer integer) {
        return null;
    }
}
