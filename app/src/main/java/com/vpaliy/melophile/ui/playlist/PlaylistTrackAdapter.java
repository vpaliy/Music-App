package com.vpaliy.melophile.ui.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ServiceWorkerClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistTrackAdapter extends BaseAdapter<Track> {

    private static final int BLANK_TYPE=0;
    private static final int TRACK_TYPE=2;

    private View blank;

    public PlaylistTrackAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class Blank extends GenericViewHolder{
        public Blank(View itemView){
            super(itemView);
        }
        @Override
        public void onBindData() {}
    }

    public class TrackViewHolder extends GenericViewHolder{

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

        public int current(){
            return getAdapterPosition()-1;
        }

        @Override
        public void onBindData() {
            Track track=at(current());
            Log.d(PlaylistTrackAdapter.class.getSimpleName(),"Artist:"+track.getArtist());
            artist.setText(track.getArtist());
            trackTitle.setText(track.getTitle());

            Glide.with(itemView.getContext())
                    .load(track.getArtworkUrl())
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(trackArt);
        }
    }

    public View getBlank() {
        return blank;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?BLANK_TYPE:TRACK_TYPE;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BLANK_TYPE:
                return new Blank(blank=inflate(R.layout.layout_blank,parent));
            default:
                return new TrackViewHolder(inflate(R.layout.adapter_playlist_track,parent));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+1;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }
}
