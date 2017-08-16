package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.domain.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetUserDetails extends SingleUseCase<UserDetails,String> {

    private Repository repository;

    @Inject
    public GetUserDetails(BaseSchedulerProvider schedulerProvider,
                          Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<UserDetails> buildUseCase(String id) {
        return repository.getUserBy(id);
    }
}
