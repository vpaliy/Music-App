package com.vpaliy.melophile.ui.user.info;

import com.vpaliy.domain.interactor.SingleInteractor;
import com.vpaliy.domain.model.Track;

import java.util.List;

import javax.inject.Inject;

import com.vpaliy.melophile.di.scope.ViewScope;

@ViewScope
public class FavoritePresenter extends UserInfoPresenter<Track> {

  @Inject
  public FavoritePresenter(SingleInteractor<List<Track>, String> favoritesInteractor) {
    super(favoritesInteractor);
  }
}
