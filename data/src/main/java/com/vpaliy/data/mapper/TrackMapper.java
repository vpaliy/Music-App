package com.vpaliy.data.mapper;


import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.MiniUserEntity;
import com.vpaliy.soundcloud.model.TrackEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrackMapper extends Mapper<Track, TrackEntity> {

  private Mapper<User, MiniUserEntity> userMapper;

  @Inject
  public TrackMapper(Mapper<User, MiniUserEntity> userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public Track map(TrackEntity trackEntity) {
    if (trackEntity == null) return null;
    Track track = new Track();
    track.setId(trackEntity.id);
    track.setUser(userMapper.map(trackEntity.user));
    track.setArtworkUrl(trackEntity.artwork_url);
    track.setDuration(trackEntity.duration);
    track.setStreamUrl(MapperUtils.convertToStream(trackEntity.stream_url));
    track.setTags(MapperUtils.splitString(trackEntity.tags_list));
    track.setReleaseDate(trackEntity.release);
    track.setTitle(trackEntity.title);
    MiniUserEntity userEntity = trackEntity.user;
    if (userEntity != null) {
      track.setArtist(userEntity.username);
    }
    return track;
  }

  @Override
  public TrackEntity reverse(Track track) {
    if (track == null) return null;
    TrackEntity trackEntity = new TrackEntity();
    trackEntity.id = track.getId();
    trackEntity.user = userMapper.reverse(track.getUser());
    trackEntity.artwork_url = track.getArtworkUrl();
    trackEntity.duration = track.getDuration();
    trackEntity.stream_url = MapperUtils.convertFromStream(track.getStreamUrl());
    trackEntity.tags_list = MapperUtils.toString(track.getTags());
    trackEntity.release = track.getReleaseDate();
    trackEntity.title = track.getTitle();
    return trackEntity;
  }
}
