package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.domain.repository.Repository;
import java.util.List;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetRecentTracks extends SingleUseCase<List<Track>,Void>{

    private PersonalRepository repository;

    @Inject
    public GetRecentTracks(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<List<Track>> buildUseCase(Void aVoid) {
        return repository.fetchTrackHistory();
    }
}
