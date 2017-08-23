package com.vpaliy.domain.repository;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import java.util.List;
import io.reactivex.Single;

public interface SearchRepository {
    Single<List<Track>> searchTrack(String query);
    Single<List<Playlist>> searchPlaylist(String query);
    Single<List<User>> searchUser(String query);

    //request more data from the previous query
    Single<List<User>> moreUsers();
    Single<List<Track>> moreTracks();
    Single<List<Playlist>> morePlaylists();
}
