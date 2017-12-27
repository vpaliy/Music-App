package com.vpaliy.domain.interactor;

import android.text.TextUtils;
import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.Repository;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetPlaylist extends SingleInteractor<Playlist,String> {

    private Repository repository;

    @Inject
    public GetPlaylist(BaseSchedulerProvider schedulerProvider, Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<Playlist> buildUseCase(String id) {
        return !TextUtils.isEmpty(id)
                ? repository.getPlaylistBy(id)
                :Single.error(new IllegalArgumentException("Id is null"));
    }
}
