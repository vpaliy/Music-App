package com.vpaliy.data.source.remote;

import com.vpaliy.soundcloud.model.MiniUserEntity;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Filter {

  @Inject
  public Filter() {
  }

  public List<TrackEntity> filterTracks(List<TrackEntity> tracks) {
    if (tracks != null) {
      List<TrackEntity> list = new LinkedList<>();
      for (TrackEntity entity : tracks) {
        entity = filter(entity);
        if (entity != null) list.add(entity);
      }
      return list.isEmpty() ? null : list;
    }
    return null;
  }

  public List<PlaylistEntity> filterPlaylists(List<PlaylistEntity> playlistEntities) {
    if (playlistEntities != null) {
      List<PlaylistEntity> result = new LinkedList<>();
      for (PlaylistEntity entity : playlistEntities) {
        entity = filter(entity);
        if (entity != null) {
          result.add(entity);
        }
      }
      return result.isEmpty() ? null : result;
    }
    return null;
  }

  public List<UserEntity> filterUsers(List<UserEntity> users) {
    if (users != null) {
      List<UserEntity> result = new ArrayList<>(users.size());
      for (UserEntity entity : users) {
        entity = filter(entity);
        if (entity != null) {
          result.add(entity);
        }
      }
      return result.isEmpty() ? null : result;
    }
    return null;
  }

  public TrackEntity filter(TrackEntity trackEntity) {
    if (trackEntity != null) {
      if (trackEntity.artwork_url != null && trackEntity.is_streamable) {
        trackEntity.artwork_url = trackEntity.artwork_url.replace("large", "t500x500");
        return trackEntity;
      }
    }
    return null;
  }

  public PlaylistEntity filter(PlaylistEntity playlistEntity) {
    if (playlistEntity != null) {
      if (playlistEntity.artwork_url != null) {
        playlistEntity.artwork_url = playlistEntity.artwork_url.replace("large", "t500x500");
        playlistEntity.tracks = filterTracks(playlistEntity.tracks);
        playlistEntity.user = filter(playlistEntity.user);
        return playlistEntity;
      }
    }
    return null;
  }

  public UserEntity filter(UserEntity user) {
    if (user != null) {
      if (user.avatar_url != null) {
        user.avatar_url = user.avatar_url.replace("large", "t500x500");
        return user;
      }
    }
    return null;
  }

  public MiniUserEntity filter(MiniUserEntity user) {
    if (user != null) {
      if (user.avatar_url != null) {
        user.avatar_url = user.avatar_url.replace("large", "t500x500");
        return user;
      }
    }
    return null;
  }
}
