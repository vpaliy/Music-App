package com.vpaliy.melophile.ui.user.info;

import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.User;

import java.util.List;

import javax.inject.Inject;

import com.vpaliy.melophile.di.scope.ViewScope;

@ViewScope
public class FollowersPresenter extends UserInfoPresenter<User> {

  @Inject
  public FollowersPresenter(SingleInteractor<List<User>, String> followersInteractor) {
    super(followersInteractor);
  }
}
