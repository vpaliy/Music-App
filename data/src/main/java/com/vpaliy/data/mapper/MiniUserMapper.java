package com.vpaliy.data.mapper;

import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.MiniUserEntity;

public class MiniUserMapper extends Mapper<User,MiniUserEntity> {

    @Override
    public User map(MiniUserEntity miniUserEntity) {
        if(miniUserEntity==null) return null;
        User user=new User();
        user.setId(miniUserEntity.id);
        user.setAvatarUrl(miniUserEntity.avatar_url);
        user.setNickName(miniUserEntity.username);
        user.setFullName(miniUserEntity.username);
        return user;
    }

    @Override
    public MiniUserEntity reverse(User user) {
        if(user==null) return null;
        MiniUserEntity userEntity=new MiniUserEntity();
        userEntity.avatar_url=user.getAvatarUrl();
        userEntity.id=user.getId();
        userEntity.username=user.getNickName();
        return userEntity;
    }
}
