package com.vpaliy.data.repository;

import android.support.annotation.NonNull;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.source.SearchSource;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.SearchRepository;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.List;

import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MusicSearchRepository implements SearchRepository {

  private SearchSource searchSource;
  private Mapper<Track, TrackEntity> trackMapper;
  private Mapper<Playlist, PlaylistEntity> playlistMapper;
  private Mapper<User, UserEntity> userMapper;

  @Inject
  public MusicSearchRepository(@NonNull SearchSource searchSource,
                               Mapper<Track, TrackEntity> trackMapper,
                               Mapper<Playlist, PlaylistEntity> playlistMapper,
                               Mapper<User, UserEntity> userMapper) {
    this.searchSource = searchSource;
    this.trackMapper = trackMapper;
    this.playlistMapper = playlistMapper;
    this.userMapper = userMapper;
  }

  @Override
  public Single<List<Playlist>> searchPlaylist(String query) {
    return searchSource.searchPlaylists(query)
            .map(playlistMapper::map);
  }

  @Override
  public Single<List<Track>> searchTrack(String query) {
    return searchSource.searchTracks(query)
            .map(trackMapper::map);
  }

  @Override
  public Single<List<User>> searchUser(String query) {
    return searchSource.searchUsers(query)
            .map(userMapper::map);
  }

  @Override
  public Single<List<Playlist>> nextPlaylistPage() {
    return searchSource.morePlaylists()
            .map(playlistMapper::map);
  }

  @Override
  public Single<List<Track>> nextTrackPage() {
    return searchSource.moreTracks()
            .map(trackMapper::map);
  }

  @Override
  public Single<List<User>> nextUserPage() {
    return searchSource.moreUsers()
            .map(userMapper::map);
  }
}
