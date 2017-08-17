package com.vpaliy.melophile.ui.track;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.ohoussein.playpause.PlayPauseView;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.playlist.PlaylistFragment;
import com.vpaliy.melophile.ui.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;
import android.support.annotation.Nullable;
import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class TrackFragment extends BaseFragment {

    @BindView(R.id.background)
    protected ImageView background;

    @BindView(R.id.end_time)
    protected TextView endTime;

    @BindView(R.id.start_time)
    protected TextView startTime;

    @BindView(R.id.circle)
    protected CircleImageView smallImage;

    @BindView(R.id.artist)
    protected TextView artist;

    @BindView(R.id.track_name)
    protected TextView trackName;

    @BindView(R.id.progressView)
    protected ProgressBar progress;

    @BindView(R.id.play_pause)
    protected PlayPauseView playPause;

    private String id;

    public static TrackFragment newInstance(Bundle args){
        TrackFragment fragment=new TrackFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().supportPostponeEnterTransition();
        extractId(savedInstanceState);
    }

    private void extractId(Bundle bundle){
        if(bundle==null) bundle=getArguments();
        id=bundle.getString(Constants.EXTRA_ID);
        Log.d(PlaylistFragment.class.getSimpleName(),"ID:"+id);
        showArt(bundle.getString(Constants.EXTRA_DATA));
    }

    public void showArt(String artUrl){
        Glide.with(getContext())
                .load(artUrl)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .into(new ImageViewTarget<Bitmap>(smallImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        smallImage.setImageBitmap(resource);
                        Blurry.with(getContext())
                                .async()
                                .from(resource)
                                .into(background);
                        getActivity().supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_player;
    }
}
