package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetMe extends SingleUseCase<User,Void>{

    private PersonalRepository repository;

    @Inject
    public GetMe(BaseSchedulerProvider schedulerProvider,
                 PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<User> buildUseCase(Void aVoid) {
        return repository.fetchMe();
    }
}
