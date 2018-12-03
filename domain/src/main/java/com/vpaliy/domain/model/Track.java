package com.vpaliy.domain.model;


import android.text.TextUtils;
import android.text.format.DateUtils;

import java.util.List;

public class Track {

  private String id;
  private String streamUrl;
  private User user;
  private String artworkUrl;
  private String duration;
  private List<String> tags;
  private String releaseDate;
  private String title;
  private String artist;
  private boolean isLiked;

  public String getId() {
    return id;
  }

  public List<String> getTags() {
    return tags;
  }

  public String getArtist() {
    return artist;
  }

  public String getArtworkUrl() {
    return artworkUrl;
  }

  public String getDuration() {
    return duration;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getStreamUrl() {
    return streamUrl;
  }

  public String getTitle() {
    return title;
  }

  public User getUser() {
    return user;
  }

  public boolean isLiked() {
    return isLiked;
  }

  public void setLiked(boolean liked) {
    isLiked = liked;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public void setArtworkUrl(String artworkUrl) {
    this.artworkUrl = artworkUrl;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public void setStreamUrl(String streamUrl) {
    this.streamUrl = streamUrl;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getFormatedDuration() {
    if (!TextUtils.isEmpty(duration)) {
      long time = Long.parseLong(duration);
      return DateUtils.formatElapsedTime(time / 1000);
    }
    return null;
  }

}
