package com.vpaliy.melophile.ui.base;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.vpaliy.melophile.ui.base.bus.RxBus;

@SuppressWarnings("WeakerAccess")
public abstract class BaseAdapter<T> extends
        RecyclerView.Adapter<BaseAdapter<T>.GenericViewHolder> {

  protected List<T> data;
  protected LayoutInflater inflater;
  protected RxBus rxBus;

  public BaseAdapter(@NonNull Context context,
                     @NonNull RxBus rxBus) {
    this.inflater = LayoutInflater.from(context);
    this.rxBus = rxBus;
    this.data = new ArrayList<>();
  }

  public void setData(List<T> data) {
    if (data == null) return;
    this.data = data;
    notifyDataSetChanged();
  }

  public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
    public GenericViewHolder(View itemView) {
      super(itemView);
    }

    public abstract void onBindData();
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  protected View inflate(@LayoutRes int id, ViewGroup container) {
    return inflater.inflate(id, container, false);
  }

  protected T at(int index) {
    return data.get(index);
  }

  public void appendData(@NonNull List<T> data) {
    int size = getItemCount();
    this.data.addAll(data);
    notifyItemRangeInserted(size, getItemCount());
  }

  public BaseAdapter<T> addItem(T item) {
    int size = getItemCount();
    data.add(item);
    notifyItemRangeInserted(size, getItemCount());
    return this;
  }
}