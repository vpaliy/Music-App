package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.Repository;

import java.util.List;
import io.reactivex.Single;

public class GetRecentPlaylists extends SingleUseCase<List<Playlist>,String> {

    private Repository repository;

    public GetRecentPlaylists(BaseSchedulerProvider schedulerProvider, Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<List<Playlist>> buildUseCase(String s) {
        return null;
    }
}
