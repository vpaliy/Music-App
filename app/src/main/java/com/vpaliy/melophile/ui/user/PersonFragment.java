package com.vpaliy.melophile.ui.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.base.adapters.PlaylistsAdapter;
import com.vpaliy.melophile.ui.base.adapters.TracksAdapter;
import com.vpaliy.melophile.ui.user.info.InfoEvent;
import com.vpaliy.melophile.ui.utils.Constants;

import java.util.List;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vpaliy.melophile.ui.user.PersonContract.Presenter;

public class PersonFragment extends BaseFragment
        implements PersonContract.View {

  private Presenter presenter;
  private String id;
  private String avatarUrl;

  @BindView(R.id.user_avatar)
  protected ImageView avatar;

  @BindView(R.id.username)
  protected TextView username;

  @BindView(R.id.followers)
  protected TextView followers;

  @BindView(R.id.follow)
  protected TextView follow;

  @BindView(R.id.likes)
  protected TextView likes;

  @BindView(R.id.time)
  protected TextView playlistCount;

  @BindView(R.id.description)
  protected TextView description;

  @BindView(R.id.media)
  protected RecyclerView media;

  @BindView(R.id.media_progress)
  protected ProgressBar progress;

  @BindView(R.id.empty_media_message)
  protected TextView emptyMessage;

  @BindView(R.id.container)
  protected ViewGroup container;

  private MediaAdapter adapter;

  public static PersonFragment newInstance(Bundle args) {
    PersonFragment fragment = new PersonFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void initializeDependencies() {
    DaggerViewComponent.builder()
            .presenterModule(new PresenterModule())
            .applicationComponent(App.appInstance().appComponent())
            .build().inject(this);
  }

  @Override
  protected int layoutId() {
    return R.layout.fragment_c_playlist;
  }

  private void extractExtra(Bundle bundle) {
    if (bundle == null) bundle = getArguments();
    this.id = bundle.getString(Constants.EXTRA_ID);
    showAvatar(bundle.getString(Constants.EXTRA_DATA));
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getActivity().supportPostponeEnterTransition();
    if (view != null) {
      final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
      adapter = new MediaAdapter(getContext(), rxBus);
      media.setLayoutManager(layoutManager);
      media.setAdapter(adapter);
      extractExtra(savedInstanceState);
      media.setOnTouchListener((v, event) -> {
        final int firstVisible = layoutManager.findFirstVisibleItemPosition();
        if (firstVisible > 0) return false;

        final RecyclerView.ViewHolder vh = media.findViewHolderForAdapterPosition(1);
        if (vh == null) return false;
        final int firstTop = vh.itemView.getTop();
        if (event.getY() < firstTop) {
          return container.dispatchTouchEvent(event);
        }
        return false;
      });
    }
  }

  @Override
  public void showDescription(String description) {
    this.description.setText(description);
  }

  @Override
  public void showFollowersCount(int count) {
    followers.setText(getResources().getQuantityString(R.plurals.followers, count, count));
    scaleAnimation(followers);
  }

  private void scaleAnimation(View view) {
    view.setScaleY(0);
    view.setScaleX(0);
    view.animate().setDuration(300)
            .scaleX(1)
            .scaleY(1)
            .setInterpolator(new OvershootInterpolator())
            .start();
  }

  @Override
  public void showTitle(String title) {
    username.setText(title);
    scaleAnimation(username);
  }

  @Override
  public void showErrorMessage() {
    showMessage(R.string.error_message);
  }

  @Override
  public void showLikedCount(int count) {
    likes.setText(getResources().getQuantityString(R.plurals.likes, count, count));
    scaleAnimation(likes);
  }

  @OnClick(R.id.likes)
  public void showFavorites() {
    rxBus.sendWithLock(InfoEvent.showFavorites(id));
  }

  @OnClick(R.id.followers)
  public void showFollowers() {
    rxBus.sendWithLock(InfoEvent.showFollowers(id));
  }

  @Override
  public void showAvatar(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    Glide.with(getContext())
            .load(avatarUrl)
            .asBitmap()
            .priority(Priority.IMMEDIATE)
            .into(new ImageViewTarget<Bitmap>(avatar) {
              @Override
              protected void setResource(Bitmap resource) {
                avatar.setImageBitmap(resource);
                avatar.post(() -> {
                  media.post(() -> {
                    View blank = adapter.getBlank();
                    ViewGroup.LayoutParams params = blank.getLayoutParams();
                    params.height = followers.getTop() + followers.getHeight()
                            + 2 * getResources().getDimensionPixelOffset(R.dimen.spacing_large);
                    blank.setLayoutParams(params);
                  });
                  media.setVisibility(View.INVISIBLE);
                  getActivity().supportStartPostponedEnterTransition();
                  presenter.start(id);
                });
              }
            });
  }

  @Override
  public void showLoading() {
    progress.setScaleX(0);
    progress.setScaleY(0);
    progress.setVisibility(View.VISIBLE);
    progress.animate()
            .scaleX(1)
            .scaleY(1)
            .setDuration(300)
            .setListener(null)
            .start();
  }

  @OnClick(R.id.follow)
  public void follow() {
    presenter.follow();
    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.pump);
    follow.startAnimation(animation);
  }

  @Override
  public void enableFollow() {
    follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_add, 0, 0, 0);
    follow.setText(R.string.follow_label);
    animateFollow();
  }

  @Override
  public void disableFollow() {
    follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
    follow.setText(R.string.following_label);
    animateFollow();
  }

  @Override
  public void showTracksCount(int count) {
    playlistCount.setText(getResources().getQuantityString(R.plurals.tracks, count, count));
    scaleAnimation(playlistCount);
  }

  private void animateFollow() {
    if (follow.getAnimation() != null) {
      follow.getAnimation().cancel();
    }
    AnimatorSet scaleSet = new AnimatorSet();
    scaleSet.playTogether(ObjectAnimator.ofFloat(follow, View.SCALE_X, 0, 1),
            ObjectAnimator.ofFloat(follow, View.SCALE_Y, 0, 1));
    scaleSet.setDuration(300);
    scaleSet.setStartDelay(100);
    scaleSet.setInterpolator(new OvershootInterpolator());
    scaleSet.start();
  }

  @Override
  public void hideLoading() {
    progress.animate()
            .scaleX(0)
            .scaleY(0)
            .setDuration(300)
            .setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //if the animation is still running when the back button has been pressed
                if (progress != null) {
                  progress.setVisibility(View.GONE);
                }
              }
            }).start();
  }

  @Override
  public void showEmptyMediaMessage() {
    emptyMessage.setVisibility(View.VISIBLE);
  }

  @Override
  public void showEmptyMessage() {
    showMessage(R.string.empty_message);
  }

  @Override
  public void showPlaylists(List<Playlist> playlists) {
    media.setVisibility(View.VISIBLE);
    PlaylistsAdapter playlistsAdapter = new PlaylistsAdapter(getContext(), rxBus);
    playlistsAdapter.setData(playlists);
    adapter.addItem(MediaAdapter.CategoryWrapper.wrap(getString(R.string.playlist_label), playlistsAdapter));
    media.post(() -> {
      media.scrollToPosition(0);
      media.animate()
              .alpha(1)
              .setDuration(400)
              .start();
    });
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(Constants.EXTRA_ID, id);
    outState.putString(Constants.EXTRA_DATA, avatarUrl);
  }

  @Override
  public void showTracks(List<Track> tracks) {
    media.setVisibility(View.VISIBLE);
    TracksAdapter tracksAdapter = new TracksAdapter(getContext(), rxBus);
    tracksAdapter.setData(tracks);
    adapter.addItem(MediaAdapter.CategoryWrapper.wrap(getString(R.string.tracks_label), tracksAdapter));
    media.post(() -> media.scrollToPosition(0));
  }

  @Override
  public void onPause() {
    super.onPause();
    if (presenter != null) {
      presenter.stop();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(PersonFragment.class.getSimpleName(), "onStop");
    if (presenter != null) {
      presenter.stop();
    }
  }
}
