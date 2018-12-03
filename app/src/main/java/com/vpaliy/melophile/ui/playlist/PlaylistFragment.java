package com.vpaliy.melophile.ui.playlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.gson.reflect.TypeToken;
import com.vpaliy.chips_lover.ChipsLayout;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.playback.QueueManager;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.BundleUtils;
import com.vpaliy.melophile.ui.utils.Constants;
import com.vpaliy.melophile.ui.view.Chips;
import com.vpaliy.melophile.ui.view.FabToggle;
import com.vpaliy.melophile.ui.view.ParallaxRatioImageView;
import com.vpaliy.melophile.ui.view.TranslatableLayout;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vpaliy.melophile.ui.playlist.PlaylistContract.Presenter;

public class PlaylistFragment extends BaseFragment
        implements PlaylistContract.View {

  private Presenter presenter;
  private String id;

  @BindView(R.id.playlist_art)
  protected ParallaxRatioImageView playlistArt;

  @BindView(R.id.tracks)
  protected RecyclerView tracks;

  @BindView(R.id.play)
  protected FabToggle toggle;

  @BindView(R.id.parent)
  protected TranslatableLayout parent;

  @BindView(R.id.playlist_title)
  protected TextView playlistTitle;

  @BindView(R.id.tracks_number)
  protected TextView trackNumber;

  @BindView(R.id.share)
  protected TextView shareButton;

  @BindView(R.id.back)
  protected ImageView back;

  @BindView(R.id.author)
  protected TextView user;

  @BindView(R.id.user_avatar)
  protected ImageView userAvatar;

  @BindView(R.id.time)
  protected TextView duration;

  @BindView(R.id.title_background)
  protected View titleBackground;

  @BindView(R.id.chips)
  protected Chips chipsLayout;

  private PlaylistTrackAdapter adapter;

  private boolean loaded;

  private User userModel;

  public static PlaylistFragment newInstance(Bundle args) {
    PlaylistFragment fragment = new PlaylistFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void initializeDependencies() {
    DaggerViewComponent.builder()
            .applicationComponent(App.appInstance().appComponent())
            .presenterModule(new PresenterModule())
            .build().inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    extractId(savedInstanceState);
  }

  @Override
  protected int layoutId() {
    return R.layout.fragment_playlist;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getActivity().supportPostponeEnterTransition();
    if (view != null) {
      parent.addOnLayoutChangeListener(((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
        View blank = adapter.getBlank();
        if (blank != null) {
          ViewGroup.LayoutParams params = blank.getLayoutParams();
          params.height = playlistArt.getHeight() + parent.getHeight();
          blank.setLayoutParams(params);
        }
      }));
      adapter = new PlaylistTrackAdapter(getContext(), rxBus);
      tracks.setAdapter(adapter);
      loadCover(savedInstanceState);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (presenter != null) {
      presenter.start(id);
    }
  }

  private void loadCover(Bundle bundle) {
    if (bundle == null) {
      bundle = getArguments();
    }
    showPlaylistArt(bundle.getString(Constants.EXTRA_DATA));
  }

  private void extractId(Bundle bundle) {
    if (bundle == null) {
      bundle = getArguments();
    }
    id = bundle.getString(Constants.EXTRA_ID);
  }

  @Override
  public void showEmptyMessage() {
    showMessage(R.string.empty_message);
  }

  @Override
  public void showErrorMessage() {
    showMessage(R.string.error_message);
  }

  @Override
  public void showTrackNumber(int trackNumber) {
    this.trackNumber.setText(getResources().getQuantityString(R.plurals.likes, trackNumber, trackNumber));
  }

  @Override
  public void showTitle(String title) {
    playlistTitle.setText(title);
    playlistTitle.setScaleX(0);
    playlistTitle.setScaleY(0);
    titleBackground.post(() -> {
      int cx = titleBackground.getWidth() / 2;
      int cy = titleBackground.getHeight() / 2;
      Animator animator = ViewAnimationUtils.createCircularReveal(titleBackground, cx, cy, 0,
              (int) Math.hypot(titleBackground.getWidth(), titleBackground.getHeight()));
      animator.setDuration(400);
      animator.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
          super.onAnimationStart(animation);
          titleBackground.setVisibility(View.VISIBLE);
          playlistTitle.animate()
                  .setDuration(400)
                  .scaleX(1).scaleY(1)
                  .setInterpolator(new OvershootInterpolator())
                  .start();
        }
      });
      animator.start();
    });
  }

  @Override
  public void showTags(List<String> tags) {
    chipsLayout.setTags(tags);
    chipsLayout.setOnTouchListener((view, event) -> {
      tracks.dispatchTouchEvent(event);
      return false;
    });
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(id, Constants.EXTRA_ID);
  }

  @Override
  public void showButtons() {
    List<View> buttons = Arrays.asList(shareButton, trackNumber, duration);
    for (int index = 0; index < buttons.size(); index++) {
      View view = buttons.get(index);
      view.animate()
              .scaleX(1)
              .scaleY(1)
              .setDuration(200)
              .setStartDelay(index * 100)
              .start();
    }
  }

  @Override
  public void showPlaylistArt(String artUrl) {
    if (!loaded) {
      loaded = true;
      Glide.with(getContext())
              .load(artUrl)
              .asBitmap()
              .priority(Priority.IMMEDIATE)
              .into(new ImageViewTarget<Bitmap>(playlistArt) {
                @Override
                protected void setResource(Bitmap resource) {
                  final int imageHeight = playlistArt.getHeight();
                  playlistArt.setImageBitmap(resource);
                  parent.setStaticOffset(imageHeight);
                  parent.setOffset(imageHeight);
                  toggle.setStaticOffset(imageHeight - toggle.getHeight() / 2);
                  toggle.setOffset(imageHeight - toggle.getHeight() / 2);
                  toggle.setMinOffset(ViewCompat.getMinimumHeight(playlistArt) - toggle.getHeight() / 2);
                  tracks.addOnScrollListener(listener);
                  tracks.setOnFlingListener(flingListener);
                  new Palette.Builder(resource).generate(PlaylistFragment.this::applyPalette);
                  getActivity().supportStartPostponedEnterTransition();
                }
              });
    }
  }

  @OnClick(R.id.play)
  public void play() {
    List<Track> tracks = adapter.getTracks();
    if (tracks != null && !tracks.isEmpty()) {
      QueueManager queueManager = QueueManager.createQueue(tracks, 0);
      Bundle data = new Bundle();
      BundleUtils.packHeavyObject(data, Constants.EXTRA_QUEUE, queueManager,
              new TypeToken<QueueManager>() {
              }.getType());
      rxBus.sendWithLock(ExposeEvent.exposeTrack(data, null));
    }
  }

  private void applyPalette(Palette palette) {
    Palette.Swatch swatch = palette.getDarkVibrantSwatch();
    if (swatch == null) swatch = palette.getDominantSwatch();
    if (swatch != null) {
      toggle.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
    }
  }

  @Override
  public void showDuration(String duration) {
    this.duration.setText(duration);
  }

  @Override
  public void showTracks(List<Track> tracks) {
    adapter.setData(tracks);
  }

  @Override
  public void showUser(User user) {
    this.userModel = user;
    Glide.with(getContext())
            .load(user.getAvatarUrl())
            .asBitmap()
            .priority(Priority.IMMEDIATE)
            .into(userAvatar);
    this.user.setText(user.getNickName());
  }

  @OnClick(R.id.back)
  public void goBack() {
    getActivity().supportFinishAfterTransition();
  }

  @Inject
  @Override
  public void attachPresenter(@NonNull Presenter presenter) {
    this.presenter = presenter;
    this.presenter.attachView(this);
  }

  private RecyclerView.OnFlingListener flingListener = new RecyclerView.OnFlingListener() {
    @Override
    public boolean onFling(int velocityX, int velocityY) {
      playlistArt.setImmediatePin(true);
      return false;
    }
  };

  private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      playlistArt.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      final int scrollY = adapter.getBlank().getTop();
      playlistArt.setOffset(scrollY);
      parent.setOffset(parent.getStaticOffset() + scrollY);
      toggle.setOffset(toggle.getStaticOffset() + scrollY);
    }
  };

  @Override
  public void onStop() {
    super.onStop();
    if (presenter != null) {
      presenter.stop();
    }
  }

  @OnClick(R.id.user_avatar)
  public void userClick() {
    if (userModel != null) {
      Bundle data = new Bundle();
      data.putString(Constants.EXTRA_ID, userModel.getId());
      data.putString(Constants.EXTRA_DATA, userModel.getAvatarUrl());
      preserveParent();
      rxBus.send(ExposeEvent.exposeUser(data,
              Pair.create(userAvatar, getString(R.string.user_trans_name))));
    }
  }

  private void preserveParent() {
    ViewGroup.LayoutParams params = parent.getLayoutParams();
    if (params.height != parent.getBottom()) {
      params.height = parent.getBottom();
      parent.setLayoutParams(params);
    }
  }
}
