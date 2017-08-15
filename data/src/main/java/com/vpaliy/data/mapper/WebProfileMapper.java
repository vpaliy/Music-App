package com.vpaliy.data.mapper;

import com.vpaliy.domain.model.WebProfile;
import com.vpaliy.soundcloud.model.WebProfileEntity;

//TODO implement this class
public class WebProfileMapper extends Mapper<WebProfile,WebProfileEntity> {

    @Override
    public WebProfile map(WebProfileEntity webProfileEntity) {
        if(webProfileEntity==null) return null;
        WebProfile profile=new WebProfile();
        return profile;
    }

    @Override
    public WebProfileEntity reverse(WebProfile webProfile) {
        if(webProfile==null) return null;
        return new WebProfileEntity();
    }
}
