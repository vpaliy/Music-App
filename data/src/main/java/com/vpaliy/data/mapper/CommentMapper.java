package com.vpaliy.data.mapper;


import com.vpaliy.domain.model.Comment;
import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.CommentEntity;
import com.vpaliy.soundcloud.model.MiniUserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommentMapper extends Mapper<Comment, CommentEntity> {

  private Mapper<User, MiniUserEntity> miniUserMapper;

  @Inject
  public CommentMapper(Mapper<User, MiniUserEntity> mapper) {
    this.miniUserMapper = mapper;
  }

  @Override
  public Comment map(CommentEntity commentEntity) {
    if (commentEntity == null) return null;
    Comment comment = new Comment();
    comment.setBody(commentEntity.body);
    comment.setId(commentEntity.id);
    comment.setTime(commentEntity.timestamp);
    comment.setUri(commentEntity.uri);
    comment.setUser(miniUserMapper.map(commentEntity.user));
    return comment;
  }

  @Override
  public CommentEntity reverse(Comment comment) {
    if (comment == null) return null;
    CommentEntity entity = new CommentEntity();
    entity.body = comment.getBody();
    entity.id = comment.getId();
    entity.timestamp = comment.getTime();
    entity.uri = comment.getUri();
    entity.user = miniUserMapper.reverse(comment.getUser());
    return entity;
  }
}
