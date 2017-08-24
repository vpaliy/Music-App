package com.vpaliy.domain.interactor;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.repository.PersonalRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SaveInteractor {

    private PersonalRepository repository;

    @Inject
    public SaveInteractor(PersonalRepository repository){
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
