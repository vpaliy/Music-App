package com.vpaliy.melophile.ui.user;

import com.vpaliy.domain.interactor.ModifyInteractor;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;

import java.util.List;

import static com.vpaliy.melophile.ui.user.PersonContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;

import android.support.annotation.NonNull;

@ViewScope
public class PersonPresenter implements PersonContract.Presenter {

  private View view;
  private User user;
  private ModifyInteractor<User> modifyInteractor;

  @Inject
  public PersonPresenter(ModifyInteractor<User> modifyInteractor) {
    this.modifyInteractor = modifyInteractor;
  }

  @Override
  public void attachView(@NonNull View view) {
    this.view = checkNotNull(view);
  }

  @Override
  public void start(String id) {
    view.showLoading();
    //  userDetailsUseCase.execute(this::catchData,this::catchError,id);
  }

  private void catchData(UserDetails details) {
    view.hideLoading();
    if (details != null) {
      boolean isEmpty = isEmpty(details.getTracks());
      if (!isEmpty) {
        view.showTracks(details.getTracks());
      }
      //check the playlists
      if (!isEmpty(details.getPlaylists())) {
        view.showPlaylists(details.getPlaylists());
      } else if (isEmpty) {
        view.showEmptyMediaMessage();
      }
      user = details.getUser();
      if (user != null) {
        view.showFollowersCount(user.getFollowersCount());
        view.showTitle(user.getNickName());
        view.showDescription(user.getDescription());
        view.showTracksCount(user.getTracksCount());
        view.showLikedCount(user.getLikedTracksCount());
        manageFollowing();
      }
    } else {
      view.showEmptyMessage();
    }
  }

  private <T> boolean isEmpty(List<T> items) {
    return items == null || items.isEmpty();
  }

  private void catchError(Throwable ex) {
    view.hideLoading();
    ex.printStackTrace();
    view.showErrorMessage();
  }

  private void manageFollowing() {
    if (user.isFollowed()) {
      view.disableFollow();
    } else {
      view.enableFollow();
    }
  }

  @Override
  public void follow() {
    //TODO implement
  }

  private void catchFollowRequest() {
    user.setFollowed(!user.isFollowed());
    manageFollowing();
  }

  @Override
  public void stop() {
    //userDetailsUseCase.dispose();
  }
}
