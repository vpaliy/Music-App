package com.vpaliy.data.repository;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.data.source.Source;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MusicRepository implements Repository {

    private Mapper<Playlist,PlaylistEntity> playlistMapper;
    private Mapper<Track,TrackEntity> trackMapper;
    private Mapper<User,UserEntity> userMapper;
    private Mapper<UserDetails,UserDetailsEntity> detailsMapper;
    private Source remoteSource;

    @Inject
    public MusicRepository(Mapper<Playlist,PlaylistEntity> playlistMapper,
                           Mapper<Track,TrackEntity> trackMapper,
                           Mapper<User,UserEntity> userMapper,
                           Mapper<UserDetails,UserDetailsEntity> detailsMapper,
                           Source remoteSource){
        this.playlistMapper=playlistMapper;
        this.trackMapper=trackMapper;
        this.userMapper=userMapper;
        this.detailsMapper=detailsMapper;
        this.remoteSource=remoteSource;
    }

    @Override
    public Single<List<Playlist>> getPlaylistsBy(List<String> categories) {
        return remoteSource.getPlaylistsBy(categories)
                .map(playlistMapper::map);
    }

    @Override
    public Single<List<Track>> getTracksBy(List<String> categories) {
        return remoteSource.getTracksBy(categories)
                .map(trackMapper::map);
    }

    @Override
    public Single<List<User>> getUsersBy(List<String> categories) {
        return remoteSource.getUsersBy(categories)
                .map(userMapper::map);
    }

    @Override
    public Single<Playlist> getPlaylistBy(String id) {
        return remoteSource.getPlaylistBy(id)
                .map(playlistMapper::map);
    }

    @Override
    public Single<Track> getTrackBy(String id) {
        return remoteSource.getTrackBy(id)
                .map(trackMapper::map);
    }

    @Override
    public Single<UserDetails> getUserBy(String id) {
        return remoteSource.getUserBy(id)
                .map(detailsMapper::map);
    }

    @Override
    public Single<List<User>> getUserFollowers(String id) {
        return remoteSource.getUserFollowers(id)
                .map(userMapper::map);
    }
}
