package com.vpaliy.data.source.local;

import android.text.TextUtils;
import com.vpaliy.data.source.LocalSource;
import com.vpaliy.data.source.local.handler.PlaylistHandler;
import com.vpaliy.data.source.local.handler.TrackHandler;
import com.vpaliy.data.source.local.handler.UserHandler;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import java.util.List;
import io.reactivex.Single;

//@Singleton
public class LocalMusicSource implements LocalSource {

    private PlaylistHandler playlistHandler;
    private TrackHandler trackHandler;
    private UserHandler userHandler;

    //@Inject
    public LocalMusicSource(PlaylistHandler playlistHandler,
                            TrackHandler trackHandler,
                            UserHandler userHandler){
        this.playlistHandler=playlistHandler;
        this.trackHandler=trackHandler;
        this.userHandler=userHandler;
    }

    @Override
    public Single<List<Playlist>> getPlaylistsBy(List<String> categories) {
        return null;
    }

    @Override
    public Single<List<Track>> getTracksBy(List<String> categories) {
        return null;
    }


    @Override
    public Single<List<User>> getUsersBy(List<String> categories) {
        return null;
    }

    @Override
    public Single<List<Track>> getUserFavorites(String id) {
        if(!TextUtils.isEmpty(id)){
            return Single.fromCallable(()->userHandler.queryFavorites(id));
        }
        return Single.error(new IllegalArgumentException("Id is empty"));
    }

    @Override
    public Single<List<User>> getUserFollowers(String id) {
        if(!TextUtils.isEmpty(id)){
            return Single.fromCallable(()->userHandler.queryFollowers(id));
        }
        return Single.error(new IllegalArgumentException("Id is empty"));
    }

    @Override
    public Single<Playlist> getPlaylistBy(String id) {
        if(!TextUtils.isEmpty(id)){
            return Single.fromCallable(()->playlistHandler.query(id));
        }
        return Single.error(new IllegalArgumentException("Id is empty or null"));
    }

    @Override
    public Single<Track> getTrackBy(String id) {
        if(!TextUtils.isEmpty(id)){
            return Single.fromCallable(()->trackHandler.query(id));
        }
        return Single.error(new IllegalArgumentException("Id is null"));
    }

    @Override
    public Single<UserDetails> getUserBy(String id) {
        return null;
    }

    @Override
    public void insert(Playlist playlist) {
        playlistHandler.insert(playlist);
    }

    @Override
    public void insert(Track track) {
        trackHandler.insert(track);
    }

    @Override
    public void insert(User user) {
        userHandler.insert(user);
    }
}
