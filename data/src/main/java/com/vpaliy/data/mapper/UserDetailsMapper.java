package com.vpaliy.data.mapper;

import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.domain.model.WebProfile;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import com.vpaliy.soundcloud.model.WebProfileEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDetailsMapper extends Mapper<UserDetails, UserDetailsEntity> {

  private Mapper<Track, TrackEntity> trackMapper;
  private Mapper<Playlist, PlaylistEntity> playlistMapper;
  private Mapper<User, UserEntity> userMapper;
  private Mapper<WebProfile, WebProfileEntity> webMapper;

  @Inject
  public UserDetailsMapper(Mapper<Track, TrackEntity> trackMapper,
                           Mapper<Playlist, PlaylistEntity> playlistMapper,
                           Mapper<User, UserEntity> userMapper,
                           Mapper<WebProfile, WebProfileEntity> webMapper) {
    this.trackMapper = trackMapper;
    this.playlistMapper = playlistMapper;
    this.userMapper = userMapper;
    this.webMapper = webMapper;
  }

  @Override
  public UserDetails map(UserDetailsEntity userDetailsEntity) {
    if (userDetailsEntity == null) return null;
    UserDetails details = new UserDetails();
    details.setUser(userMapper.map(userDetailsEntity.getUserEntity()));
    details.setTracks(trackMapper.map(userDetailsEntity.getTracks()));
    details.setPlaylists(playlistMapper.map(userDetailsEntity.getPlaylists()));
    details.setFavoriteTracks(trackMapper.map(userDetailsEntity.getFavoriteTracks()));
    details.setWebProfiles(webMapper.map(userDetailsEntity.getWebProfiles()));
    return details;
  }

  @Override
  public UserDetailsEntity reverse(UserDetails userDetails) {
    if (userDetails == null) return null;
    UserDetailsEntity entity = new UserDetailsEntity();
    entity.setUserEntity(userMapper.reverse(userDetails.getUser()));
    entity.setTracks(trackMapper.reverse(userDetails.getTracks()));
    entity.setPlaylists(playlistMapper.reverse(userDetails.getPlaylists()));
    entity.setFavoriteTracks(trackMapper.reverse(userDetails.getFavoriteTracks()));
    entity.setWebProfiles(webMapper.reverse(userDetails.getWebProfiles()));
    return entity;
  }
}
