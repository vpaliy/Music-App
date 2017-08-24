package com.vpaliy.domain.repository;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import android.support.annotation.NonNull;

public interface PersonalRepository {
    Completable likeTrack(@NonNull Track track);
    Completable unlikeTrack(@NonNull Track track);
    Completable follow(@NonNull User user);
    Completable unfollow(@NonNull User user);
    Single<List<Track>> fetchTrackHistory();
    Single<List<Playlist>> fetchPlaylistHistory();
    Single<User> fetchMe();
    void saveTrack(@NonNull Track track);
    void savePlaylist(@NonNull Playlist playlist);
    void clearTracks();
    void clearPlaylists();
}
