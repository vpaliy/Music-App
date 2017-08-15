package com.vpaliy.data.source;

import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import java.util.List;
import io.reactivex.Single;

public interface SearchSource {
    Single<List<TrackEntity>> searchTrack(String query);
    Single<List<PlaylistEntity>> searchPlaylist(String query);
    Single<List<UserEntity>> searchUser(String query);
}
