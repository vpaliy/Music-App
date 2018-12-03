package com.vpaliy.data.source;

import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.List;

import io.reactivex.Single;

import android.support.annotation.NonNull;

public interface SearchSource {
  Single<List<TrackEntity>> searchTracks(@NonNull String query);

  Single<List<PlaylistEntity>> searchPlaylists(@NonNull String query);

  Single<List<UserEntity>> searchUsers(@NonNull String query);

  Single<List<TrackEntity>> moreTracks();

  Single<List<PlaylistEntity>> morePlaylists();

  Single<List<UserEntity>> moreUsers();
}
