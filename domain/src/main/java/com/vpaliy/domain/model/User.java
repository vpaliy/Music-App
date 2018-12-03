package com.vpaliy.domain.model;

public class User {

  private String id;
  private String nickName;
  private String avatarUrl;
  private String fullName;
  private String description;
  private int followingCount;
  private int followersCount;
  private int playlistsCount;
  private int tracksCount;
  private int likedTracksCount;
  private boolean isFollowed;

  public String getDescription() {
    return description;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public int getFollowingCount() {
    return followingCount;
  }

  public int getPlaylistsCount() {
    return playlistsCount;
  }

  public int getTracksCount() {
    return tracksCount;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getFullName() {
    return fullName;
  }

  public String getId() {
    return id;
  }

  public void setLikedTracksCount(int likedTracksCount) {
    this.likedTracksCount = likedTracksCount;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  public int getLikedTracksCount() {
    return likedTracksCount;
  }

  public String getNickName() {
    return nickName;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setFollowersCount(int followersCount) {
    this.followersCount = followersCount;
  }

  public void setFollowingCount(int followingCount) {
    this.followingCount = followingCount;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public void setPlaylistsCount(int playlistsCount) {
    this.playlistsCount = playlistsCount;
  }

  public void setTracksCount(int tracksCount) {
    this.tracksCount = tracksCount;
  }
}
