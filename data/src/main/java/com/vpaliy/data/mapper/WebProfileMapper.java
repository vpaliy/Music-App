package com.vpaliy.data.mapper;

import com.vpaliy.domain.model.WebProfile;
import com.vpaliy.soundcloud.model.WebProfileEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

//TODO implement this class
@Singleton
public class WebProfileMapper extends Mapper<WebProfile, WebProfileEntity> {

  @Inject
  public WebProfileMapper() {
  }

  @Override
  public WebProfile map(WebProfileEntity webProfileEntity) {
    if (webProfileEntity == null) return null;
    WebProfile profile = new WebProfile();
    return profile;
  }

  @Override
  public WebProfileEntity reverse(WebProfile webProfile) {
    if (webProfile == null) return null;
    return new WebProfileEntity();
  }
}
