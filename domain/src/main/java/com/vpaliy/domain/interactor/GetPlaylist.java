package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.Repository;
import io.reactivex.Single;

public class GetPlaylist extends SingleUseCase<Playlist,String> {

    private Repository repository;

    public GetPlaylist(BaseSchedulerProvider schedulerProvider,
                        Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<Playlist> buildUseCase(String id) {
        return repository.getPlaylistBy(id);
    }
}
