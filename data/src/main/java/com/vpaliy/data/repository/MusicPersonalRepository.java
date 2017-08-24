package com.vpaliy.data.repository;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.soundcloud.SoundCloudService;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class MusicPersonalRepository implements PersonalRepository {

    private SoundCloudService service;

    @Inject
    public MusicPersonalRepository(SoundCloudService service){
        this.service=service;
    }

    @Override
    public Completable likeTrack(@NonNull Track track) {
        return service.loveTrack(track.getId());
    }

    @Override
    public Completable unfollow(@NonNull User user) {
        return service.unfollowUser(user.getId());
    }

    @Override
    public Completable follow(@NonNull User user) {
        return service.followUser(user.getId());
    }

    @Override
    public Completable unlikeTrack(@NonNull Track track) {
        return service.unloveTrack(track.getId());
    }

    //database stuff

    @Override
    public Single<List<Playlist>> fetchPlaylistHistory() {
        return null;
    }

    @Override
    public Single<List<Track>> fetchTrackHistory() {
        return null;
    }

    @Override
    public void savePlaylist(@NonNull Playlist playlist) {

    }

    @Override
    public void saveTrack(@NonNull Track track) {

    }
}
