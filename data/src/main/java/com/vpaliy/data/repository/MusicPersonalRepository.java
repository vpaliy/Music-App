package com.vpaliy.data.repository;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.source.remote.Filter;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class MusicPersonalRepository implements PersonalRepository {

    private SoundCloudService service;
    private Mapper<User,UserEntity> userMapper;
    private Mapper<Playlist,PlaylistEntity> playlistMapper;
    private Mapper<Track,TrackEntity> trackMapper;
    private Filter filter;

    @Inject
    public MusicPersonalRepository(SoundCloudService service,
                                   Mapper<User,UserEntity> userMapper,
                                   Mapper<Playlist,PlaylistEntity> playlistMapper,
                                   Mapper<Track,TrackEntity> trackMapper,
                                   Filter filter){
        this.service=service;
        this.userMapper=userMapper;
        this.playlistMapper=playlistMapper;
        this.trackMapper=trackMapper;
        this.filter=filter;
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
        return service.searchPlaylists(PlaylistEntity.Filter.start()
                .byName("Imagine").limit(100).createOptions())
                .map(filter::filterPlaylists)
                .map(playlistMapper::map);
    }

    @Override
    public Single<List<Track>> fetchTrackHistory() {
        return service.searchTracks(TrackEntity.Filter.start()
                .byName("Imagine Dragons").limit(100).createOptions())
                .map(filter::filterTracks)
                .map(trackMapper::map);
    }

    @Override
    public Single<User> fetchMe() {
        return service.me()
                .map(userMapper::map);
    }

    @Override
    public void savePlaylist(@NonNull Playlist playlist) {

    }

    @Override
    public void saveTrack(@NonNull Track track) {

    }

    @Override
    public void clearPlaylists() {

    }

    @Override
    public void clearTracks() {

    }
}
