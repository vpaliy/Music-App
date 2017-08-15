package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.domain.repository.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleSource;

@Singleton
public class GetPlaylists extends SingleUseCase<PlaylistSet,MelophileTheme>{

    private Repository repository;

    @Inject
    public GetPlaylists(BaseSchedulerProvider schedulerProvider,
                        Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<PlaylistSet> buildUseCase(MelophileTheme theme) {
        if(theme!=null){
            return repository.getPlaylistsBy(theme.getTags())
                    .map(list -> new PlaylistSet(theme, list));
        }
        return Single.error(new IllegalArgumentException("Melophile theme is null!"));
    }
}
