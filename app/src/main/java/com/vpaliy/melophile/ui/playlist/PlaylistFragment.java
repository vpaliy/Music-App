package com.vpaliy.melophile.ui.playlist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.utils.Constants;
import com.vpaliy.melophile.ui.view.FabToggle;
import com.vpaliy.melophile.ui.view.ParallaxRatioImageView;
import com.vpaliy.melophile.ui.view.TranslatableLayout;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

import static com.vpaliy.melophile.ui.playlist.PlaylistContract.Presenter;

public class PlaylistFragment extends BaseFragment
        implements PlaylistContract.View{

    private Presenter presenter;
    private String id;

    @BindView(R.id.playlist_art)
    protected ParallaxRatioImageView playlistArt;

    @BindView(R.id.tracks)
    protected RecyclerView tracks;

    @BindView(R.id.love)
    protected FabToggle love;

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

    private PlaylistTrackAdapter adapter;

    private boolean loaded;

    public static PlaylistFragment newInstance(Bundle args){
        PlaylistFragment fragment=new PlaylistFragment();
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
    protected int layoutId() {
        return R.layout.fragment_playlist;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().supportPostponeEnterTransition();
        extractId(savedInstanceState);
        if(view!=null){
            adapter=new PlaylistTrackAdapter(getContext(),rxBus);
            tracks.setAdapter(adapter);
            tracks.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            presenter.start(id);
        }
    }

    private void extractId(Bundle bundle){
        if(bundle==null) bundle=getArguments();
        id=bundle.getString(Constants.EXTRA_ID);
        Log.d(PlaylistFragment.class.getSimpleName(),"ID:"+id);
        showPlaylistArt(bundle.getString(Constants.EXTRA_DATA));
    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showLikes(String likes) {
    }

    @Override
    public void showTrackNumber(String trackNumber) {
        this.trackNumber.setText(trackNumber);
    }

    @Override
    public void showTitle(String title) {
        playlistTitle.setText(title);
    }

    @Override
    public void showPlaylistArt(String artUrl) {
        if(!loaded) {
            loaded=true;
            Log.d(PlaylistFragment.class.getSimpleName(), artUrl);
            Glide.with(getContext())
                    .load(artUrl)
                    .asBitmap()
                    .priority(Priority.IMMEDIATE)
                    .into(new ImageViewTarget<Bitmap>(playlistArt) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            final int imageHeight=playlistArt.getHeight();
                            final int padding=getResources().getDimensionPixelOffset(R.dimen.spacing_large);
                            playlistArt.setImageBitmap(resource);
                            parent.setStaticOffset(imageHeight);
                            parent.setOffset(imageHeight);
                            love.setStaticOffset(imageHeight-love.getHeight()/2);
                            love.setOffset(imageHeight-love.getHeight()/2);
                            love.setMinOffset(ViewCompat.getMinimumHeight(playlistArt)-love.getHeight()/2);
                            View blank = adapter.getBlank();
                            ViewGroup.LayoutParams params = blank.getLayoutParams();
                            params.height = parent.getTop()+parent.getHeight()+imageHeight+padding;
                            blank.setLayoutParams(params);
                            tracks.addOnScrollListener(listener);
                            tracks.setOnFlingListener(flingListener);
                            new Palette.Builder(resource).generate(PlaylistFragment.this::applyPalette);
                            getActivity().supportStartPostponedEnterTransition();
                        }
                    });
        }
    }

    private void applyPalette(Palette palette){
        Palette.Swatch swatch=palette.getDarkVibrantSwatch();
        if(swatch==null) swatch=palette.getDominantSwatch();
        //apply if not null
        if(swatch!=null){
           // parent.setBackgroundColor(swatch.getRgb());
            //playlistTitle.setTextColor(swatch.getTitleTextColor());
            //trackNumber.setTextColor(swatch.getTitleTextColor());
            //shareButton.setTextColor(swatch.getTitleTextColor());
            //PresentationUtils.setDrawableColor(back,swatch.getTitleTextColor());
            //PresentationUtils.setDrawableColor(trackNumber,swatch.getTitleTextColor());
            //PresentationUtils.setDrawableColor(shareButton,swatch.getTitleTextColor());
        }
    }

    @Override
    public void showTracks(List<Track> tracks) {
        adapter.setData(tracks);
    }

    @Override
    public void showUser(User user) {
        Glide.with(getContext())
                .load(user.getAvatarUrl())
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .into(userAvatar);
        this.user.setText(user.getNickName());
    }

    @OnClick(R.id.back)
    public void goBack(){
        getActivity().supportFinishAfterTransition();
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    private RecyclerView.OnFlingListener flingListener = new RecyclerView.OnFlingListener() {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            playlistArt.setImmediatePin(true);
            return false;
        }
    };

    private RecyclerView.OnScrollListener listener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            playlistArt.setImmediatePin(newState==RecyclerView.SCROLL_STATE_SETTLING);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int scrollY= adapter.getBlank().getTop();
            playlistArt.setOffset(scrollY);
            parent.setOffset(parent.getStaticOffset()+scrollY);
            love.setOffset(love.getStaticOffset()+scrollY);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if(presenter!=null){
            presenter.stop();
        }
    }
}
