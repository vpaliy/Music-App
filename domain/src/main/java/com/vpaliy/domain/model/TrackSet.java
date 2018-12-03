package com.vpaliy.domain.model;

import java.util.List;

public class TrackSet {
  private MelophileTheme theme;
  private List<Track> tracks;

  public TrackSet() {
  }

  public TrackSet(MelophileTheme theme, List<Track> tracks) {
    this.theme = theme;
    this.tracks = tracks;
  }

  public MelophileTheme getTheme() {
    return theme;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTheme(MelophileTheme theme) {
    this.theme = theme;
  }

  public String getThemeString() {
    if (theme != null) {
      return theme.getTheme();
    }
    return null;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}
