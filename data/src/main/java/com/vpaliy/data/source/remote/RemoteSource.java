package com.vpaliy.data.source.remote;

import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.data.source.Source;
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

    @Inject
    public RemoteSource(SoundCloudService service){
        this.service=service;
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
            return start.map(list->{
                List<PlaylistEntity> result=new LinkedList<>();
                for(PlaylistEntity entity:list){
                    if(entity.artwork_url!=null){
                        result.add(entity);
                    }
                }
                return result;
            });
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
            return service.fetchPlaylist(id);
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
            Single<List<TrackEntity>> singleTracks=service.fetchUserTracks(id);
            Single<List<PlaylistEntity>> singlePlaylists=service.fetchUserPlaylists(id);
            Single<List<TrackEntity>> singleFavoriteTracks=service.fetchUserFavoriteTracks(id);
            Single<List<WebProfileEntity>> singleWebProfiles=service.fetchUserWebProfiles(id);
            Single<UserEntity> singleUser=service.fetchUser(id);
            return Single.zip(singleUser,
                    singleTracks.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singlePlaylists.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singleFavoriteTracks.onErrorResumeNext(Single.just(new ArrayList<>())),
                    singleWebProfiles.onErrorResumeNext(Single.just(new ArrayList<>())),
                    (user,tracks,playlists,favoriteTracks,webProfiles)->{
                        UserDetailsEntity userDetails=new UserDetailsEntity();
                        userDetails.setUserEntity(user);
                        userDetails.setFavoriteTracks(favoriteTracks);
                        userDetails.setTracks(tracks);
                        userDetails.setPlaylists(playlists);
                        userDetails.setWebProfiles(webProfiles);
                        return userDetails;
                    });

        }
        return Single.error(new IllegalArgumentException("id is null"));
    }

}
