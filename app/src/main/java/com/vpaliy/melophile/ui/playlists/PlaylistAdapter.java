package com.vpaliy.melophile.ui.playlists;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.base.bus.event.OnClick;
import com.vpaliy.melophile.ui.utils.Constants;

import butterknife.ButterKnife;

import android.support.annotation.NonNull;

import butterknife.BindView;

@SuppressWarnings("WeakerAccess")
public class PlaylistAdapter extends BaseAdapter<Playlist> {

  public PlaylistAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  public class PlaylistViewHolder extends GenericViewHolder {

    @BindView(R.id.playlist_art)
    ImageView artImage;

    @BindView(R.id.playlist_title)
    TextView playlistTitle;

    PlaylistViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(v -> {
        Playlist playlist = at(getAdapterPosition());
        Bundle data = new Bundle();
        Context context = inflater.getContext();
        ViewCompat.setTransitionName(artImage, context.getString(R.string.art_trans_name));
        ViewCompat.setTransitionName(itemView, context.getString(R.string.background_trans_name));
        data.putString(Constants.EXTRA_DATA, playlist.getArtUrl());
        data.putString(Constants.EXTRA_ID, playlist.getId());
        rxBus.sendWithLock(ExposeEvent.exposePlaylist(data, Pair.create(artImage, context.getString(R.string.art_trans_name)),
                Pair.create(artImage, context.getString(R.string.background_trans_name))));
      });
    }

    @Override
    public void onBindData() {
      Playlist playlist = at(getAdapterPosition());
      playlistTitle.setText(playlist.getTitle());
      Glide.with(itemView.getContext())
              .load(playlist.getArtUrl())
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .into(artImage);
    }
  }

  @Override
  public void onBindViewHolder(GenericViewHolder holder, int position) {
    holder.onBindData();
  }

  @Override
  public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View root = inflater.inflate(R.layout.adapter_playlist, parent, false);
    return new PlaylistViewHolder(root);
  }
}
