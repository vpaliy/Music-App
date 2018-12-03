package com.vpaliy.domain.model;

import java.util.List;

public class PlaylistSet {
  private MelophileTheme theme;
  private List<Playlist> playlists;

  public PlaylistSet(MelophileTheme theme, List<Playlist> playlists) {
    this.theme = theme;
    this.playlists = playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
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

  public List<Playlist> getPlaylists() {
    return playlists;
  }

  public MelophileTheme getTheme() {
    return theme;
  }
}
