package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import java.util.List;
import io.reactivex.Single;

public class GetPlaylists extends SingleUseCase<List<Playlist>,Void>{

    public GetPlaylists(BaseSchedulerProvider schedulerProvider){
        super(schedulerProvider);
    }

    @Override
    public Single<List<Playlist>> buildUseCase(Void aVoid) {
        return null;
    }
}
