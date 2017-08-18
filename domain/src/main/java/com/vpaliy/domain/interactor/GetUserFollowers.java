package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.Repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetUserFollowers extends SingleUseCase<List<User>,String>{

    private Repository repository;

    @Inject
    public GetUserFollowers(Repository repository, BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<List<User>> buildUseCase(String id) {
        return repository.getUserFollowers(id);
    }
}
