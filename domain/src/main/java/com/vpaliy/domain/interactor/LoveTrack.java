package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class LoveTrack extends CompletableUseCase<Track> {

    private PersonalRepository repository;

    @Inject
    public LoveTrack(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Completable buildCompletable(Track track) {
        if(track!=null){
            return repository.likeTrack(track);
        }
        return Completable.error(new IllegalArgumentException("Track is null!"));
    }
}
