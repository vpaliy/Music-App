package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.repository.PersonalRepository;
import java.util.List;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlaylistHistory extends SingleUseCase<List<Playlist>,Void> {

    private PersonalRepository repository;

    @Inject
    public PlaylistHistory(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        super(schedulerProvider);
        this.repository=repository;
    }

    @Override
    public Single<List<Playlist>> buildUseCase(Void aVoid) {
        return repository.fetchPlaylistHistory();
    }

    public void clearHistory(){
        repository.clearPlaylists();
    }
}
