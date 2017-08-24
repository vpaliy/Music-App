package com.vpaliy.data.repository;

import android.support.annotation.NonNull;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.soundcloud.SoundCloudService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class MusicPersonalRepository implements PersonalRepository {

    private SoundCloudService service;

    @Inject
    public MusicPersonalRepository(SoundCloudService service){
        this.service=service;
    }

    @Override
    public Completable likeTrack(@NonNull Track track) {
        return null;
    }

    @Override
    public Completable unfollow(@NonNull User user) {
        return null;
    }

    @Override
    public Completable follow(@NonNull User user) {
        return null;
    }

    @Override
    public Completable unlikeTrack(@NonNull Track track) {
        return null;
    }

    @Override
    public Single<List<Playlist>> fetchPlaylistHistory() {
        return null;
    }

    @Override
    public Single<List<Track>> fetchTrackHistory() {
        return null;
    }
}
