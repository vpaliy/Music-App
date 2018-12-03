package com.vpaliy.data.model;

import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import com.vpaliy.soundcloud.model.WebProfileEntity;

import java.util.List;

public class UserDetailsEntity {

  private UserEntity userEntity;
  private List<TrackEntity> tracks;
  private List<PlaylistEntity> playlists;
  private List<TrackEntity> favoriteTracks;
  private List<WebProfileEntity> webProfiles;

  public List<PlaylistEntity> getPlaylists() {
    return playlists;
  }

  public List<TrackEntity> getFavoriteTracks() {
    return favoriteTracks;
  }

  public List<TrackEntity> getTracks() {
    return tracks;
  }

  public List<WebProfileEntity> getWebProfiles() {
    return webProfiles;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setWebProfiles(List<WebProfileEntity> webProfiles) {
    this.webProfiles = webProfiles;
  }

  public void setTracks(List<TrackEntity> tracks) {
    this.tracks = tracks;
  }

  public void setPlaylists(List<PlaylistEntity> playlists) {
    this.playlists = playlists;
  }

  public void setFavoriteTracks(List<TrackEntity> favoriteTracks) {
    this.favoriteTracks = favoriteTracks;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }
}
