package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class FollowUser extends CompletableUseCase<User> {

    private PersonalRepository repository;

    @Inject
    public FollowUser(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Completable buildCompletable(User user) {
        if(user!=null){
            return repository.follow(user);
        }
        return Completable.error(new IllegalArgumentException("User is null!"));
    }
}