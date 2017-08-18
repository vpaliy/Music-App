package com.vpaliy.melophile.ui.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class UserTracksAdapter extends BaseAdapter<Track> {

    public UserTracksAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class TrackViewHolder extends BaseAdapter<Track>.GenericViewHolder {

        @BindView(R.id.track_art)
        ImageView trackArt;

        @BindView(R.id.artist)
        TextView artist;

        @BindView(R.id.track_title)
        TextView trackTitle;

        public TrackViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData() {
            Track track=at(getAdapterPosition());
            artist.setText(track.getArtist());
            trackTitle.setText(track.getTitle());

            Glide.with(itemView.getContext())
                    .load(track.getArtworkUrl())
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(trackArt);
        }
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrackViewHolder(inflate(R.layout.adapter_playlist_track,parent));
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }
}
