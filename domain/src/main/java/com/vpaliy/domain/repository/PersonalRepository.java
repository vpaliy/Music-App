package com.vpaliy.domain.repository;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface PersonalRepository {
  Completable likeTrack(Track track);

  Completable unlikeTrack(Track track);

  Completable likePlaylist(Playlist playlist);

  Completable unlikePlaylist(Playlist playlist);

  Completable clearFavoriteTracks();

  Completable clearFavoritePlaylists();

  Completable follow(User user);

  Completable unfollow(User user);

  Completable saveTrack(Track track);

  Completable removeTrack(Track track);

  Completable savePlaylist(Playlist playlist);

  Completable removePlaylist(Playlist playlist);

  Completable clearTracks();

  Completable clearPlaylists();

  Completable unfollowAll();

  Single<List<User>> fetchFollowers();

  Single<List<Track>> fetchTrackHistory();

  Single<List<Track>> fetchFavoriteTracks();

  Single<List<Playlist>> fetchPlaylistHistory();

  Single<List<Playlist>> fetchFavoritePlaylist();

  Single<User> fetchMyself();
}

