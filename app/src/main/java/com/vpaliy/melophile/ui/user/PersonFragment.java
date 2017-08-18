package com.vpaliy.melophile.ui.user;

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
import com.vpaliy.melophile.ui.playlists.CategoryAdapter;
import com.vpaliy.melophile.ui.utils.Constants;
import java.util.List;
import java.util.Locale;
import android.widget.ImageView;
import android.widget.TextView;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.media)
    protected RecyclerView media;

    private CategoryAdapter adapter;


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
        if(view!=null){
            adapter=new CategoryAdapter(getContext(),rxBus);
            media.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            media.setAdapter(adapter);
            extractId(savedInstanceState);
        }
    }

    @Override
    public void showTracks(List<Track> tracks) {
        Log.d(PersonFragment.class.getSimpleName(),Integer.toString(tracks.size()));
        UserTracksAdapter tracksAdapter=new UserTracksAdapter(getContext(),rxBus);
        tracksAdapter.setData(tracks);
        media.setAdapter(tracksAdapter);
        //adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(getString(R.string.tracks_label),adapter,0));
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
                        getActivity().supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    public void showFollowers(List<User> followers) {

    }

    @Override
    public void showEmptyMessage() {
        //TODO add empty message
    }

    @Override
    public void showPlaylists(List<Playlist> playlists) {
        Log.d(PersonFragment.class.getSimpleName(),Integer.toString(playlists.size()));
        UserPlaylistsAdapter playlistsAdapter=new UserPlaylistsAdapter(getContext(),rxBus);
        playlistsAdapter.setData(playlists);
      //  adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(getString(R.string.tracks_label),adapter,1));
    }

    @OnClick(R.id.follow)
    public void follow(){

    }

    @OnClick(R.id.followers)
    public void requestFollowers(){
        presenter.requestFollowers(id);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter!=null){
            presenter.stop();
        }
    }
}
