package com.vpaliy.domain.interactor;


import android.text.TextUtils;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.SearchRepository;

import java.util.List;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlaylistSearch extends SingleUseCase<List<Playlist>,String>{

    private SearchRepository repository;

    @Inject
    public PlaylistSearch(BaseSchedulerProvider schedulerProvider,
                          SearchRepository searchRepository){
        super(schedulerProvider);
        this.repository=searchRepository;
    }

    @Override
    public Single<List<Playlist>> buildUseCase(String query) {
        if(query==null || TextUtils.isEmpty(query)) {
            return Single.error(new IllegalArgumentException("Query is null"));
        }
        return repository.searchPlaylist(query);
    }
}
