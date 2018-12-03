package com.vpaliy.domain.repository;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import java.util.List;

import io.reactivex.Single;

public interface SearchRepository {
  /* Search for data */
  Single<List<Track>> searchTrack(String query);

  Single<List<Playlist>> searchPlaylist(String query);

  Single<List<User>> searchUser(String query);

  /* Request more data based on previous query*/
  Single<List<User>> nextUserPage();

  Single<List<Track>> nextTrackPage();

  Single<List<Playlist>> nextPlaylistPage();
}
