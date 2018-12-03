package com.vpaliy.data.repository;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.source.PersonalInfo;
import com.vpaliy.data.source.local.handler.PlaylistHandler;
import com.vpaliy.data.source.local.handler.TrackHandler;
import com.vpaliy.data.source.remote.Filter;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.support.annotation.NonNull;

@Singleton
public class MusicPersonalRepository
        implements PersonalRepository {

  private SoundCloudService service;
  private Mapper<User, UserEntity> userMapper;
  private PersonalInfo personalInfo;
  private PlaylistHandler playlistHandler;
  private TrackHandler trackHandler;
  private Filter filter;

  @Inject
  public MusicPersonalRepository(SoundCloudService service,
                                 Mapper<User, UserEntity> userMapper,
                                 PlaylistHandler playlistHandler,
                                 TrackHandler trackHandler, Filter filter,
                                 PersonalInfo personalInfo) {
    this.service = service;
    this.userMapper = userMapper;
    this.personalInfo = personalInfo;
    this.trackHandler = trackHandler;
    this.playlistHandler = playlistHandler;
    this.filter = filter;
  }

  @Override
  public Completable likeTrack(@NonNull Track track) {
    personalInfo.like(track.getId());
    return service.loveTrack(track.getId());
  }

  @Override
  public Completable unfollow(@NonNull User user) {
    personalInfo.removeFollower(user.getId());
    return service.unfollowUser(user.getId());
  }

  @Override
  public Completable follow(@NonNull User user) {
    personalInfo.follow(user.getId());
    return service.followUser(user.getId());
  }

  @Override
  public Single<List<Playlist>> fetchPlaylistHistory() {
    return Single.fromCallable(() -> playlistHandler.querySaved());
  }

  @Override
  public Single<List<Track>> fetchTrackHistory() {
    return Single.fromCallable(() -> trackHandler.queryHistory());
  }

  @Override
  public Single<User> fetchMyself() {
    return service.me()
            .map(filter::filter)
            .map(userMapper::map);
  }

  @Override
  public Completable clearFavoritePlaylists() {
    return null;
  }

  @Override
  public Completable clearFavoriteTracks() {
    return null;
  }

  @Override
  public Completable likePlaylist(Playlist playlist) {
    return null;
  }

  @Override
  public Completable unfollowAll() {
    return null;
  }

  @Override
  public Completable unlikePlaylist(Playlist playlist) {
    return null;
  }

  @Override
  public Completable unlikeTrack(Track track) {
    return null;
  }

  @Override
  public Completable removePlaylist(Playlist playlist) {
    return null;
  }

  @Override
  public Completable removeTrack(Track track) {
    return null;
  }

  @Override
  public Single<List<Playlist>> fetchFavoritePlaylist() {
    return null;
  }

  @Override
  public Single<List<Track>> fetchFavoriteTracks() {
    return null;
  }

  @Override
  public Completable savePlaylist(@NonNull Playlist playlist) {
    return null;
  }

  @Override
  public Completable saveTrack(@NonNull Track track) {
    return null;
  }

  @Override
  public Completable clearPlaylists() {
    return null;
  }

  @Override
  public Completable clearTracks() {
    return null;
  }

  @Override
  public Single<List<User>> fetchFollowers() {
    return null;
  }
}
