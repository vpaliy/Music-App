package com.vpaliy.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.common.cache.CacheBuilder;
import com.vpaliy.data.cache.CacheStore;
import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.data.source.LocalSource;
import com.vpaliy.data.source.PersonalInfo;
import com.vpaliy.data.source.RemoteSource;
import com.vpaliy.domain.model.MelophileTheme;
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

    private RemoteSource remoteSource;
    private LocalSource localSource;

    private CacheStore<String,Playlist> playlistCacheStore;
    private CacheStore<String,Track> trackCacheStore;
    private CacheStore<String,User> userCacheStore;

    private PersonalInfo personalInfo;
    private Context context;

    @Inject
    public MusicRepository(Context context,
                           Mapper<Playlist,PlaylistEntity> playlistMapper,
                           Mapper<Track,TrackEntity> trackMapper,
                           Mapper<User,UserEntity> userMapper,
                           Mapper<UserDetails,UserDetailsEntity> detailsMapper,
                           RemoteSource remoteSource, LocalSource localSource,
                           PersonalInfo personalInfo){
        this.playlistMapper=playlistMapper;
        this.trackMapper=trackMapper;
        this.userMapper=userMapper;
        this.detailsMapper=detailsMapper;
        this.remoteSource=remoteSource;
        this.localSource=localSource;
        this.personalInfo=personalInfo;
        this.context=context;
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
    public Single<List<Playlist>> getPlaylistsBy(MelophileTheme theme) {
        if(isNetworkConnection()) {
            return remoteSource.getPlaylistsBy(theme.getTags())
                    .map(playlistMapper::map)
                    .map(list -> savePlaylists(theme, list));
        }
        return localSource.getPlaylistsBy(theme);
    }

    @Override
    public Single<List<Track>> getTracksBy(MelophileTheme theme) {
        if(isNetworkConnection()) {
            return remoteSource.getTracksBy(theme.getTags())
                    .map(trackMapper::map)
                    .map(personalInfo::didLike)
                    .map(list -> saveTracks(theme, list));
        }
        return localSource.getTracksBy(theme)
                .map(personalInfo::didLike);
    }

    @Override
    public Single<Playlist> getPlaylistBy(String id) {
        if(playlistCacheStore.isInCache(id)){
            return playlistCacheStore.getStream(id);
        }else if(isNetworkConnection()) {
            return remoteSource.getPlaylistBy(id)
                    .map(playlistMapper::map)
                    .map(this::cache);
        }
        return localSource.getPlaylistBy(id)
                .map(this::cache);
    }

    @Override
    public Single<Track> getTrackBy(String id) {
        if(trackCacheStore.isInCache(id)){
            return trackCacheStore.getStream(id)
                    .map(personalInfo::didLike);
        }else if(isNetworkConnection()) {
            return remoteSource.getTrackBy(id)
                    .map(trackMapper::map)
                    .map(personalInfo::didLike)
                    .map(this::cache);
        }
        return localSource.getTrackBy(id)
                .map(personalInfo::didLike)
                .map(this::cache);
    }

    @Override
    public Single<List<Track>> getUserFavorites(String id) {
        if(isNetworkConnection()) {
            return remoteSource.getUserFavorites(id)
                    .map(trackMapper::map)
                    .map(personalInfo::didLike);
        }
        return localSource.getUserFavorites(id)
                .map(personalInfo::didLike);
    }

    @Override
    public Single<UserDetails> getUserBy(String id) {
        if(isNetworkConnection()) {
            return remoteSource.getUserBy(id)
                    .map(detailsMapper::map)
                    .map(details -> {
                        if (details != null) {
                            personalInfo.amFollowing(details.getUser());
                        }
                        return details;
                    });
        }
        return localSource.getUserBy(id);
    }

    @Override
    public Single<List<User>> getUserFollowers(String id) {
        if(isNetworkConnection()) {
            return remoteSource.getUserFollowers(id)
                    .map(userMapper::map)
                    .map(personalInfo::amFollowing);
        }
        return localSource.getUserFollowers(id)
                .map(personalInfo::amFollowing);
    }

    private List<Playlist> savePlaylists(MelophileTheme theme, List<Playlist> list){
        if(list!=null){
            for(Playlist playlist:list){
                localSource.insert(playlist);
                localSource.insert(theme,playlist);
            }
        }
        return list;
    }

    private List<Track> saveTracks(MelophileTheme theme, List<Track> list){
        if(list!=null){
            for(Track track:list){
                localSource.insert(track);
                localSource.insert(theme,track);
            }
        }
        return list;
    }

    private Playlist cache(Playlist playlist){
        if (playlist != null) {
            playlistCacheStore.put(playlist.getId(),playlist);
        }
        return playlist;
    }

    private Track cache(Track track){
        if(track!=null){
            trackCacheStore.put(track.getId(),track);
        }
        return track;
    }

    private User cache(User user){
        if(user!=null){
            userCacheStore.put(user.getId(),user);
        }
        return user;
    }

    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}
