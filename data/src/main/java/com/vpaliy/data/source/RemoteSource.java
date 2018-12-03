package com.vpaliy.data.source;


import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.List;

import io.reactivex.Single;

public interface RemoteSource {
  Single<List<TrackEntity>> getTracksBy(List<String> categories);

  Single<List<PlaylistEntity>> getPlaylistsBy(List<String> categories);

  Single<UserDetailsEntity> getUserBy(String id);

  Single<TrackEntity> getTrackBy(String id);

  Single<PlaylistEntity> getPlaylistBy(String id);

  Single<List<UserEntity>> getUserFollowers(String id);

  Single<List<TrackEntity>> getUserFavorites(String id);
}
