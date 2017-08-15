package com.vpaliy.melophile.ui.playlists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import butterknife.BindView;

@SuppressWarnings("WeakerAccess")
public class PlaylistAdapter extends BaseAdapter<Playlist>{

    public PlaylistAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class PlaylistViewHolder extends GenericViewHolder {

        @BindView(R.id.playlist_art)
        ImageView artImage;

        @BindView(R.id.playlist_title)
        TextView playlistTitle;

        PlaylistViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData(){
            Playlist playlist=at(getAdapterPosition());
            playlistTitle.setText(playlist.getTitle());
            String url=playlist.getArtUrl();
            if(url!=null) url=url.replace("large","t500x500");
            Glide.with(itemView.getContext())
                    .load(url)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.placeholder(R.drawable.placeholder)
                   // .animate(R.anim.fade_in)
                    .into(artImage);
        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_playlist,parent,false);
        return new PlaylistViewHolder(root);
    }
}
