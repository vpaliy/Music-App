package com.vpaliy.domain.interactor;


import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.domain.repository.Repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetTracks extends SingleUseCase<TrackSet,MelophileTheme>{

    private Repository repository;

    @Inject
    public GetTracks(BaseSchedulerProvider schedulerProvider,
                     Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    public void cache(Track track){
        repository.cache(track);
    }

    @Override
    public Single<TrackSet> buildUseCase(MelophileTheme theme) {
        if(theme!=null){
            return repository.getTracksBy(theme.getTags())
                    .map(list -> new TrackSet(theme, list));
        }
        return Single.error(new IllegalArgumentException("Melophile theme is null!"));
    }
}
