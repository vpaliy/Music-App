package com.vpaliy.domain.interactor;

import android.text.TextUtils;
import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.SearchRepository;
import java.util.List;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class TrackSearch extends SingleUseCase<List<Track>,String>{

    private SearchRepository repository;

    @Inject
    public TrackSearch(BaseSchedulerProvider schedulerProvider,
                       SearchRepository searchRepository){
        super(schedulerProvider);
        this.repository=searchRepository;
    }

    @Override
    public Single<List<Track>> buildUseCase(String query) {
        if(query==null || TextUtils.isEmpty(query)) {
            return Single.error(new IllegalArgumentException("Query is null"));
        }
        return repository.searchTrack(query);
    }
}
