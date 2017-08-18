package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.domain.repository.Repository;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetPlaylists extends SingleUseCase<PlaylistSet,MelophileTheme>{

    private Repository repository;

    @Inject
    public GetPlaylists(BaseSchedulerProvider schedulerProvider,
                        Repository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    public void cache(Playlist playlist){
        repository.cache(playlist);
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
