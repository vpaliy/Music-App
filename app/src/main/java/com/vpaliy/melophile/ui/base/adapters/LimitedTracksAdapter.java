package com.vpaliy.melophile.ui.base.adapters;

import android.content.Context;

import com.vpaliy.melophile.ui.base.bus.RxBus;

import android.support.annotation.NonNull;

public class LimitedTracksAdapter extends TracksAdapter {

  public int limit = 10;

  public LimitedTracksAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  @Override
  public int getItemCount() {
    int size = super.getItemCount();
    return size > limit ? limit : size;
  }
}
