package com.vpaliy.data.repository;

import com.google.common.cache.CacheBuilder;
import com.vpaliy.data.cache.CacheStore;
import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.data.source.PersonalInfo;
import com.vpaliy.data.source.RemoteSource;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MusicRepository implements Repository {

    private static final int DEFAULT_CACHE_SIZE=150; //max 150 items
    private static final int DEFAULT_CACHE_DURATION=20; //20 minutes

    private Mapper<Playlist,PlaylistEntity> playlistMapper;
    private Mapper<Track,TrackEntity> trackMapper;
    private Mapper<User,UserEntity> userMapper;
    private Mapper<UserDetails,UserDetailsEntity> detailsMapper;
    private RemoteSource remoteRemoteSource;

    private CacheStore<String,Playlist> playlistCacheStore;
    private CacheStore<String,Track> trackCacheStore;
    private CacheStore<String,User> userCacheStore;

    private PersonalInfo personalInfo;

    @Inject
    public MusicRepository(Mapper<Playlist,PlaylistEntity> playlistMapper,
                           Mapper<Track,TrackEntity> trackMapper,
                           Mapper<User,UserEntity> userMapper,
                           Mapper<UserDetails,UserDetailsEntity> detailsMapper,
                           RemoteSource remoteRemoteSource, PersonalInfo personalInfo){
        this.playlistMapper=playlistMapper;
        this.trackMapper=trackMapper;
        this.userMapper=userMapper;
        this.detailsMapper=detailsMapper;
        this.remoteRemoteSource = remoteRemoteSource;
        this.personalInfo=personalInfo;
        //initialize cache
        playlistCacheStore=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(DEFAULT_CACHE_SIZE)
                .expireAfterAccess(DEFAULT_CACHE_DURATION, TimeUnit.MINUTES)
                .build());
        trackCacheStore=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(DEFAULT_CACHE_SIZE)
                .expireAfterAccess(DEFAULT_CACHE_DURATION, TimeUnit.MINUTES)
                .build());
        userCacheStore=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(DEFAULT_CACHE_SIZE)
                .expireAfterAccess(DEFAULT_CACHE_DURATION, TimeUnit.MINUTES)
                .build());
    }

    @Override
    public Single<List<Playlist>> getPlaylistsBy(List<String> categories) {
        return remoteRemoteSource.getPlaylistsBy(categories)
                .map(playlistMapper::map);
    }

    @Override
    public Single<List<Track>> getTracksBy(List<String> categories) {
        return remoteRemoteSource.getTracksBy(categories)
                .map(trackMapper::map)
                .map(personalInfo::didLike);
    }

    @Override
    public Single<List<User>> getUsersBy(List<String> categories) {
        return remoteRemoteSource.getUsersBy(categories)
                .map(userMapper::map)
                .map(personalInfo::amFollowing);
    }

    @Override
    public Single<Playlist> getPlaylistBy(String id) {
        if(playlistCacheStore.isInCache(id)){
            return playlistCacheStore.getStream(id);
        }
        return remoteRemoteSource.getPlaylistBy(id)
                .map(playlistMapper::map);
    }

    @Override
    public Single<Track> getTrackBy(String id) {
        if(trackCacheStore.isInCache(id)){
            return trackCacheStore.getStream(id)
                    .map(personalInfo::didLike);
        }
        return remoteRemoteSource.getTrackBy(id)
                .map(trackMapper::map)
                .map(personalInfo::didLike);
    }

    @Override
    public Single<List<Track>> getUserFavorites(String id) {
        return remoteRemoteSource.getUserFavorites(id)
                .map(trackMapper::map)
                .map(personalInfo::didLike);
    }

    @Override
    public Single<UserDetails> getUserBy(String id) {
        return remoteRemoteSource.getUserBy(id)
                .map(detailsMapper::map)
                .map(details -> {
                    if(details!=null){
                        personalInfo.amFollowing(details.getUser());
                    }
                    return details;
                });
    }

    @Override
    public Single<List<User>> getUserFollowers(String id) {
        return remoteRemoteSource.getUserFollowers(id)
                .map(userMapper::map)
                .map(personalInfo::amFollowing);
    }
}
