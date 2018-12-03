package com.vpaliy.data.mapper;

import android.content.Context;
import android.util.Log;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.MiniUserEntity;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlaylistMapper extends Mapper<Playlist, PlaylistEntity> {

  private Mapper<Track, TrackEntity> trackMapper;
  private Mapper<User, MiniUserEntity> userMapper;
  private Context context;

  @Inject
  public PlaylistMapper(Mapper<Track, TrackEntity> trackMapper,
                        Mapper<User, MiniUserEntity> userMapper,
                        Context context) {
    this.trackMapper = trackMapper;
    this.userMapper = userMapper;
    this.context = context;
  }

  @Override
  public Playlist map(PlaylistEntity playlistEntity) {
    if (playlistEntity == null) return null;
    Playlist playlist = new Playlist();
    playlist.setId(playlistEntity.id);
    playlist.setArtUrl(playlistEntity.artwork_url);
    playlist.setTitle(playlistEntity.title);
    playlist.setTracks(trackMapper.map(playlistEntity.tracks));
    playlist.setUser(userMapper.map(playlistEntity.user));
    playlist.setDescription(playlistEntity.description);
    playlist.setTrackCount(MapperUtils.convertToInt(playlistEntity.track_count));
    playlist.setGenres(MapperUtils.splitString(playlistEntity.genre));
    playlist.setReleaseDate(playlistEntity.release);
    playlist.setTags(MapperUtils.splitString(playlistEntity.tag_list));
    playlist.setDuration(MapperUtils.convertDuration(context, playlistEntity.duration));
    return playlist;
  }

  @Override
  public PlaylistEntity reverse(Playlist playlist) {
    if (playlist == null) return null;
    PlaylistEntity entity = new PlaylistEntity();
    entity.id = playlist.getId();
    entity.artwork_url = playlist.getArtUrl();
    entity.title = playlist.getTitle();
    entity.tracks = trackMapper.reverse(playlist.getTracks());
    entity.user = userMapper.reverse(playlist.getUser());
    entity.description = playlist.getDescription();
    entity.track_count = Integer.toString(playlist.getTrackCount());
    entity.genre = MapperUtils.toString(playlist.getGenres());
    entity.release = playlist.getReleaseDate();
    entity.tag_list = MapperUtils.toString(playlist.getTags());
    entity.duration = MapperUtils.convertToRuntime(context, playlist.getDuration());
    return entity;
  }
}
