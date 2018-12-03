package com.vpaliy.melophile.di.module;

import android.support.v4.media.MediaMetadataCompat;

import com.vpaliy.data.mapper.CommentMapper;
import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.data.mapper.MiniUserMapper;
import com.vpaliy.data.mapper.PlaylistMapper;
import com.vpaliy.data.mapper.TrackMapper;
import com.vpaliy.data.mapper.UserDetailsMapper;
import com.vpaliy.data.mapper.UserMapper;
import com.vpaliy.data.mapper.WebProfileMapper;
import com.vpaliy.data.model.UserDetailsEntity;
import com.vpaliy.domain.model.Comment;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.domain.model.WebProfile;
import com.vpaliy.melophile.playback.MetadataMapper;
import com.vpaliy.soundcloud.model.CommentEntity;
import com.vpaliy.soundcloud.model.MiniUserEntity;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import com.vpaliy.soundcloud.model.WebProfileEntity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapperModule {

  @Singleton
  @Provides
  Mapper<User, UserEntity> userMapper(UserMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<UserDetails, UserDetailsEntity> userDetailsMapper(UserDetailsMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<Comment, CommentEntity> commentMapper(CommentMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<Playlist, PlaylistEntity> playlistMapper(PlaylistMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<User, MiniUserEntity> miniUserMapper(MiniUserMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<Track, TrackEntity> trackMapper(TrackMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<WebProfile, WebProfileEntity> webProfileMapper(WebProfileMapper mapper) {
    return mapper;
  }

  @Singleton
  @Provides
  Mapper<MediaMetadataCompat, Track> metadataMapper(MetadataMapper mapper) {
    return mapper;
  }
}
