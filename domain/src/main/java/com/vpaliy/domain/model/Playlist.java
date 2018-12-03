package com.vpaliy.domain.model;

import java.util.List;

public class Playlist {

  private String id;
  private String artUrl;
  private String title;
  private List<Track> tracks;
  private User user;
  private String description;
  private int trackCount;
  private List<String> genres;
  private String releaseDate;
  private List<String> tags;
  private String duration;

  public User getUser() {
    return user;
  }

  public String getId() {
    return id;
  }

  public List<String> getTags() {
    return tags;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public String getArtUrl() {
    return artUrl;
  }

  public String getDescription() {
    return description;
  }

  public String getDuration() {
    return duration;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setArtUrl(String artUrl) {
    this.artUrl = artUrl;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTrackCount(int trackCount) {
    this.trackCount = trackCount;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }

  public void setGenres(List<String> genres) {
    this.genres = genres;
  }

  public List<String> getGenres() {
    return genres;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public int getTrackCount() {
    return trackCount;
  }
}
