package com.vpaliy.data.source.local;

import com.vpaliy.data.source.LocalSource;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import java.util.List;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalMusicSource implements LocalSource {

    @Inject
    public LocalMusicSource(){

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
    public Single<List<Track>> getUserFavorites(String id) {
        return null;
    }

    @Override
    public Single<List<User>> getUserFollowers(String id) {
        return null;
    }

    @Override
    public Single<List<User>> getUsersBy(List<String> categories) {
        return null;
    }

    @Override
    public Single<Playlist> getPlaylistBy(String id) {
        return null;
    }

    @Override
    public Single<Track> getTrackBy(String id) {
        return null;
    }

    @Override
    public Single<UserDetails> getUserBy(String id) {
        return null;
    }

    @Override
    public void insert(Playlist playlist) {

    }

    @Override
    public void insert(Track track) {

    }

    @Override
    public void insert(User user) {

    }

}
