package com.vpaliy.melophile.ui.tracks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
import com.vpaliy.melophile.ui.utils.PresentationUtils;

import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

import android.support.annotation.NonNull;

import butterknife.BindView;

@SuppressWarnings("WeakerAccess")
public class TracksAdapter extends BaseAdapter<Track> {


  public TracksAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  public class TrackViewHolder extends BaseAdapter<Track>.GenericViewHolder {

    @BindView(R.id.track_art)
    ImageView artImage;

    @BindView(R.id.track_title)
    TextView trackTitle;

    public TrackViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(v -> {
        Track track = at(getAdapterPosition());
        QueueManager queueManager = QueueManager.createQueue(data, getAdapterPosition());
        Bundle data = new Bundle();
        Context context = inflater.getContext();
        ViewCompat.setTransitionName(artImage, context.getString(R.string.art_trans_name));
        ViewCompat.setTransitionName(itemView, context.getString(R.string.background_trans_name));
        data.putString(Constants.EXTRA_DATA, track.getArtworkUrl());
        data.putString(Constants.EXTRA_ID, track.getId());
        //pack the data
        BundleUtils.packHeavyObject(data, Constants.EXTRA_QUEUE, queueManager,
                new TypeToken<QueueManager>() {
                }.getType());
        rxBus.sendWithLock(ExposeEvent.exposeTrack(data,
                Pair.create(artImage, context.getString(R.string.art_trans_name)),
                Pair.create(artImage, context.getString(R.string.background_trans_name))));
      });
    }

    @Override
    public void onBindData() {
      Track track = at(getAdapterPosition());
      trackTitle.setText(track.getTitle());
      Glide.with(itemView.getContext())
              .load(track.getArtworkUrl())
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .into(artImage);
    }
  }

  @Override
  public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View root = inflater.inflate(R.layout.adapter_track, parent, false);
    return new TrackViewHolder(root);
  }

  @Override
  public void onBindViewHolder(GenericViewHolder holder, int position) {
    holder.onBindData();
  }
}
