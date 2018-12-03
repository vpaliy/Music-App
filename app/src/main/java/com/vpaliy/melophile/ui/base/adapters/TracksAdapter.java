package com.vpaliy.melophile.ui.base.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.playback.QueueManager;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.BundleUtils;
import com.vpaliy.melophile.ui.utils.Constants;

import butterknife.ButterKnife;

import android.support.annotation.LayoutRes;

import butterknife.BindView;

import android.support.annotation.NonNull;

/* Common adapter that is used across different packages  */
public class TracksAdapter extends BaseAdapter<Track> {

  private boolean white;

  public TracksAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  public TracksAdapter(@NonNull Context context, @NonNull RxBus rxBus, boolean white) {
    this(context, rxBus);
    this.white = white;
  }

  public class TrackViewHolder extends BaseAdapter<Track>.GenericViewHolder {

    @BindView(R.id.track_art)
    ImageView trackArt;
    @BindView(R.id.artist)
    TextView artist;
    @BindView(R.id.track_title)
    TextView trackTitle;
    @BindView(R.id.duration)
    TextView duration;

    public TrackViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(v -> {
        Track track = at(getAdapterPosition());
        QueueManager queueManager = QueueManager.createQueue(data, getAdapterPosition());
        Bundle data = new Bundle();
        Context context = inflater.getContext();
        ViewCompat.setTransitionName(trackArt, context.getString(R.string.art_trans_name));
        ViewCompat.setTransitionName(itemView, context.getString(R.string.background_trans_name));
        data.putString(Constants.EXTRA_DATA, track.getArtworkUrl());
        data.putString(Constants.EXTRA_ID, track.getId());
        //pack the data
        BundleUtils.packHeavyObject(data, Constants.EXTRA_QUEUE, queueManager,
                new TypeToken<QueueManager>() {
                }.getType());
        rxBus.sendWithLock(ExposeEvent.exposeTrack(data, Pair.create(trackArt, context.getString(R.string.art_trans_name)),
                Pair.create(trackArt, context.getString(R.string.background_trans_name))));
      });
    }

    @Override
    public void onBindData() {
      Track track = at(getAdapterPosition());
      artist.setText(track.getArtist());
      trackTitle.setText(track.getTitle());
      duration.setText(track.getFormatedDuration());
      Glide.with(itemView.getContext())
              .load(track.getArtworkUrl())
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .into(trackArt);
    }
  }

  @Override
  public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    @LayoutRes int resource = white ? R.layout.adapter_white_track : R.layout.adapter_playlist_track;
    return new TrackViewHolder(inflater.inflate(resource, parent, false));
  }

  @Override
  public void onBindViewHolder(GenericViewHolder holder, int position) {
    holder.onBindData();
  }
}
