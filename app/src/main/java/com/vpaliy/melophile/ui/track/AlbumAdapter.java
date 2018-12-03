package com.vpaliy.melophile.ui.track;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AlbumAdapter extends PagerAdapter {

  private List<Track> albums;
  private LayoutInflater inflater;
  private volatile boolean isLoaded = false;
  private Callback callback;
  private volatile int current;

  public AlbumAdapter(Context context) {
    this.albums = new ArrayList<>();
    this.inflater = LayoutInflater.from(context);
  }

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  @Override
  public View instantiateItem(ViewGroup container, int position) {
    View view = inflater.inflate(R.layout.adapter_album, container, false);
    ImageView image = ButterKnife.findById(view, R.id.image);

    Glide.with(container.getContext())
            .load(albums.get(position).getArtworkUrl())
            .asBitmap()
            .priority(Priority.IMMEDIATE)
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .into(new ImageViewTarget<Bitmap>(image) {
              @Override
              protected void setResource(Bitmap resource) {
                image.setImageBitmap(resource);
                if (position == current && !isLoaded) {
                  isLoaded = true;
                  if (callback != null) {
                    callback.onTransitionImageLoaded(image, resource);
                  }
                }
              }
            });
    container.addView(view);
    return view;
  }

  public void appendData(List<Track> data) {
    this.albums = data;
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    if (albums.size() >= 5) return 5;
    return albums.size();
  }

  @Override
  public float getPageWidth(int position) {
    return 0.5f;
  }

  public void setData(@NonNull List<Track> tracks, int position) {
    this.albums = tracks;
    this.current = position;
    notifyDataSetChanged();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    View view = View.class.cast(object);
    container.removeView(view);
  }

  public interface Callback {
    void onTransitionImageLoaded(ImageView image, Bitmap bitmap);
  }
}