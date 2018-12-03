package com.vpaliy.data.mapper;

import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserMapper extends Mapper<User, UserEntity> {

  @Inject
  public UserMapper() {
  }

  @Override
  public User map(UserEntity userEntity) {
    if (userEntity == null) return null;
    User user = new User();
    user.setId(userEntity.id);
    user.setNickName(userEntity.username);
    user.setFullName(userEntity.full_name);
    user.setDescription(userEntity.description);
    user.setAvatarUrl(userEntity.avatar_url);
    user.setFollowersCount(MapperUtils.convertToInt(userEntity.followers_count));
    user.setFollowingCount(MapperUtils.convertToInt(userEntity.followings_count));
    user.setPlaylistsCount(MapperUtils.convertToInt(userEntity.playlist_count));
    user.setTracksCount(MapperUtils.convertToInt(userEntity.track_count));
    user.setLikedTracksCount(MapperUtils.convertToInt(userEntity.public_favorites_count));
    return user;
  }

  @Override
  public UserEntity reverse(User user) {
    if (user == null) return null;
    UserEntity entity = new UserEntity();
    entity.id = user.getId();
    entity.avatar_url = user.getAvatarUrl();
    entity.username = user.getNickName();
    entity.full_name = user.getFullName();
    entity.description = user.getDescription();
    entity.followers_count = Integer.toString(user.getFollowersCount());
    entity.followings_count = Integer.toString(user.getFollowingCount());
    entity.playlist_count = Integer.toString(user.getPlaylistsCount());
    entity.track_count = Integer.toString(user.getTracksCount());
    entity.public_favorites_count = Integer.toBinaryString(user.getLikedTracksCount());
    return entity;
  }
}
