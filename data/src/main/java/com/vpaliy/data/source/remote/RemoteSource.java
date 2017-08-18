package com.vpaliy.data.source.remote;

import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.data.source.Source;
import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import com.vpaliy.soundcloud.model.WebProfileEntity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RemoteSource implements Source{

    private SoundCloudService service;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteSource(SoundCloudService service,
                        BaseSchedulerProvider schedulerProvider){
        this.service=service;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public Single<List<PlaylistEntity>> getPlaylistsBy(List<String> categories) {
        if(categories!=null) {
            Single<List<PlaylistEntity>> start = Single.just(new LinkedList<>());
            for(String category:categories){
                start=Single.zip(start,service.searchPlaylists(PlaylistEntity
                        .Filter.start()
                        .byName(category)
                        .limit(100)
                        .createOptions())
                        .onErrorResumeNext(Single.just(new ArrayList<>())),(first,second)->{
                    if(second!=null){
                        first.addAll(second);
                    }
                    return first;
                });
            }
            return start.map(this::fixPlaylistImage);
        }
        return Single.error(new IllegalArgumentException("categories are null"));
    }

    @Override
    public Single<List<TrackEntity>> getTracksBy(List<String> categories) {
        if(categories!=null) {
            Single<List<TrackEntity>> start = Single.just(new LinkedList<>());
            for(String category:categories){
                start=Single.zip(start,service.searchTracks(TrackEntity
                        .Filter.start()
                        .byTags(category)
                        .createOptions())
                        .onErrorResumeNext(Single.just(new ArrayList<>())),(first,second)->{
                    if(second!=null){
                        first.addAll(second);
                    }
                    return first;
                });
            }
            return start.map(this::fixTrackImage);
        }
        return Single.error(new IllegalArgumentException("categories are null"));
    }

    @Override
    public Single<List<UserEntity>> getUsersBy(List<String> categories) {
        if(categories!=null) {
            Single<List<UserEntity>> start = Single.just(new LinkedList<>());
            for(String category:categories){
                start=Single.zip(start,service.searchUsers(UserEntity
                        .Filter.start()
                        .byName(category)
                        .createOptions())
                        .onErrorResumeNext(Single.just(new ArrayList<>())),(first,second)->{
                    if(second!=null){
                        first.addAll(second);
                    }
                    return first;
                });
            }
            return start;
        }
        return Single.error(new IllegalArgumentException("categories are null"));
    }

    @Override
    public Single<PlaylistEntity> getPlaylistBy(String id) {
        if(id!=null){
            return service.fetchPlaylist(id)
                    .map(playlistEntity ->{
                        if(playlistEntity.artwork_url!=null){
                            playlistEntity.artwork_url=playlistEntity.artwork_url.replace("large","t500x500");
                        }
                        return playlistEntity;
                    });
        }
        return Single.error(new IllegalArgumentException("id is null"));
    }

    @Override
    public Single<TrackEntity> getTrackBy(String id) {
        return null;
    }

    @Override
    public Single<UserDetailsEntity> getUserBy(String id) {
        if(id!=null){
            Single<List<TrackEntity>> singleTracks=service.fetchUserTracks(id)
                    .subscribeOn(schedulerProvider.multi());
            Single<List<PlaylistEntity>> singlePlaylists=service.fetchUserPlaylists(id)
                    .subscribeOn(schedulerProvider.multi());
            Single<List<TrackEntity>> singleFavoriteTracks=service.fetchUserFavoriteTracks(id)
                    .subscribeOn(schedulerProvider.multi());
            Single<List<WebProfileEntity>> singleWebProfiles=service.fetchUserWebProfiles(id)
                    .subscribeOn(schedulerProvider.multi());
            Single<UserEntity> singleUser=service.fetchUser(id)
                    .subscribeOn(schedulerProvider.multi());
            return Single.zip(singleUser,
                    singleTracks.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singlePlaylists.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singleFavoriteTracks.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singleWebProfiles.onErrorResumeNext(Single.just(new ArrayList<>())),
                    (user,tracks,playlists,favoriteTracks,webProfiles)->{
                        UserDetailsEntity userDetails=new UserDetailsEntity();
                        userDetails.setUserEntity(fixUserImage(user));
                        userDetails.setFavoriteTracks(fixTrackImage(favoriteTracks));
                        userDetails.setTracks(fixTrackImage(tracks));
                        userDetails.setPlaylists(fixPlaylistImage(playlists));
                        userDetails.setWebProfiles(webProfiles);
                        return userDetails;
                    });

        }
        return Single.error(new IllegalArgumentException("id is null"));
    }

    private List<PlaylistEntity> fixPlaylistImage(List<PlaylistEntity> list){
        if (list != null) {
            List<PlaylistEntity> result=new ArrayList<>(list.size());
            for(PlaylistEntity entity:list){
                if(entity.artwork_url!=null){
                    entity.artwork_url=entity.artwork_url.replace("large","t500x500");
                    result.add(entity);
                }
            }
            return result;
        }
        return null;
    }

    private List<TrackEntity> fixTrackImage(List<TrackEntity> list){
        if (list != null) {
            List<TrackEntity> result=new ArrayList<>(list.size());
            for(TrackEntity entity:list){
                if(entity.artwork_url!=null){
                    entity.artwork_url=entity.artwork_url.replace("large","t500x500");
                    result.add(entity);
                }
            }
            return result;
        }
        return null;
    }

    private UserEntity fixUserImage(UserEntity entity){
        if(entity!=null){
            if(entity.avatar_url!=null){
                entity.avatar_url=entity.avatar_url.replace("large","t500x500");
                return entity;
            }
        }
        return entity;
    }

    @Override
    public Single<List<UserEntity>> getUserFollowers(String id) {
        if(id!=null){
            return service.fetchUserFollowers(id);
        }
        return Single.error(new IllegalArgumentException("id is null"));
    }

    @Override
    public Single<List<TrackEntity>> getUserFavorites(String id) {
        if(id!=null){
            return service.fetchUserFavoriteTracks(id);
        }
        return Single.error(new IllegalArgumentException("id is null"));
    }
}
