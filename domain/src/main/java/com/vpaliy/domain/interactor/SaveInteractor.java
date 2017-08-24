package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SaveInteractor {

    private BaseSchedulerProvider schedulerProvider;
    private PersonalRepository repository;

    @Inject
    public SaveInteractor(BaseSchedulerProvider schedulerProvider, PersonalRepository repository){
        this.schedulerProvider=schedulerProvider;
        this.repository=repository;
    }

    public void saveTrack(Track track){
        if(track!=null){
            repository.saveTrack(track);
        }
    }

    public void savePlaylist(Playlist playlist){
        if (playlist != null) {
            repository.savePlaylist(playlist);
        }
    }
}
