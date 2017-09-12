package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;

import java.util.List;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrackHistory extends SingleInteractor<List<Track>,Void> {

    private PersonalRepository repository;

    @Inject
    public TrackHistory(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<List<Track>> buildUseCase(Void aVoid) {
        return repository.fetchTrackHistory();
    }

    public void clearHistory(){
        repository.clearTracks();
    }
}
