package com.vpaliy.melophile.ui.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.utils.Constants;
import java.util.List;
import java.util.Locale;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;

import static com.vpaliy.melophile.ui.user.PersonContract.Presenter;

public class PersonFragment extends BaseFragment
        implements PersonContract.View{

    private Presenter presenter;
    private String id;

    @BindView(R.id.user_avatar)
    protected ImageView avatar;

    @BindView(R.id.username)
    protected TextView username;

    @BindView(R.id.followers)
    protected TextView followers;

    @BindView(R.id.likes)
    protected TextView likes;

    @BindView(R.id.playlists_count)
    protected TextView playlistCount;

    @BindView(R.id.description)
    protected TextView description;

    @BindView(R.id.media)
    protected RecyclerView media;

    @BindView(R.id.media_progress)
    protected ProgressBar progress;

    @BindView(R.id.empty_media_message)
    protected TextView emptyMessage;

    private MediaAdapter adapter;


    public static PersonFragment newInstance(Bundle args){
        PersonFragment fragment=new PersonFragment();
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
        return R.layout.fragment_user;
    }

    private void extractId(Bundle bundle){
        if(bundle==null) bundle=getArguments();
        this.id=bundle.getString(Constants.EXTRA_ID);
        Log.d(PersonFragment.class.getSimpleName(),id);
        presenter.start(id);
        showAvatar(bundle.getString(Constants.EXTRA_DATA));
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().supportPostponeEnterTransition();
        if(view!=null) {
            adapter = new MediaAdapter(getContext(), rxBus);
            media.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            media.setAdapter(adapter);
            extractId(savedInstanceState);
        }
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void showTracks(List<Track> tracks) {
        UserTracksAdapter tracksAdapter=new UserTracksAdapter(getContext(),rxBus);
        tracksAdapter.setData(tracks);
        adapter.addItem(MediaAdapter.CategoryWrapper.wrap(getString(R.string.tracks_label),tracksAdapter,0));
    }

    @Override
    public void showFollowersCount(int count) {
        followers.setText(String.format(Locale.US,"%d",count));
    }

    @Override
    public void showTitle(String title) {
        username.setText(title);
    }

    @Override
    public void showErrorMessage() {
        //TODO add an error message
    }

    @Override
    public void showLikedCount(int count) {
       likes.setText(String.format(Locale.US,"%d",count));
    }

    @Override
    public void showAvatar(String avatarUrl) {
        Glide.with(getContext())
                .load(avatarUrl)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .into(new ImageViewTarget<Bitmap>(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        avatar.setImageBitmap(resource);
                        avatar.post(()->{
                            media.post(()->{
                                View blank = adapter.getBlank();
                                ViewGroup.LayoutParams params = blank.getLayoutParams();
                                params.height = followers.getTop()+followers.getHeight()
                                        +getResources().getDimensionPixelOffset(R.dimen.spacing_large);
                                blank.setLayoutParams(params);
                            });
                            getActivity().supportStartPostponedEnterTransition();
                        });
                    }
                });
    }

    @Override
    public void showFollowers(List<User> followers) {
        //TODO add followers
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
                        progress.setVisibility(View.GONE);
                    }
                }).start();
    }

    @Override
    public void showEmptyMediaMessage() {
        emptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyMessage() {
        //TODO add empty message
    }

    @Override
    public void showPlaylists(List<Playlist> playlists) {
        UserPlaylistsAdapter playlistsAdapter=new UserPlaylistsAdapter(getContext(),rxBus);
        playlistsAdapter.setData(playlists);
        adapter.addItem(MediaAdapter.CategoryWrapper.wrap(getString(R.string.playlist_label),playlistsAdapter,1));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter!=null){
            presenter.stop();
        }
    }
}
