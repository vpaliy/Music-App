package com.vpaliy.domain.repository;

import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;

import java.util.List;

import io.reactivex.Single;

public interface Repository {
  Single<List<Track>> getTracksBy(MelophileTheme theme);

  Single<List<Playlist>> getPlaylistsBy(MelophileTheme theme);

  Single<UserDetails> getUserBy(String id);

  Single<Track> getTrackBy(String id);

  Single<Playlist> getPlaylistBy(String id);

  Single<List<Track>> getUserFavorites(String id);

  Single<List<User>> getUserFollowers(String id);
}
