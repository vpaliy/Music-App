package com.vpaliy.data.source;

import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.Repository;

public interface LocalSource extends Repository {
  void insert(Playlist playlist);

  void insert(Track track);

  void insert(User user);

  void insert(MelophileTheme theme, Track track);

  void insert(MelophileTheme theme, Playlist playlist);
}
