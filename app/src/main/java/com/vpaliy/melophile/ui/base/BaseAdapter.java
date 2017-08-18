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
    private Handler handler = new Handler();
    private volatile boolean lock;

    protected final static long UNLOCK_TIMEOUT = 500;

    public BaseAdapter(@NonNull Context context,
                       @NonNull RxBus rxBus) {
        this.inflater = LayoutInflater.from(context);
        this.rxBus = rxBus;
        this.data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
        public GenericViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBindData();
    }

    public boolean isLocked() {
        return lock;
    }

    protected void unlockAfter(long milliSec) {
        handler.postDelayed(this::unlock, milliSec);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void unlock() {
        lock = false;
    }

    protected View inflate(@LayoutRes int id, ViewGroup container){
        return inflater.inflate(id,container,false);
    }

    public void lock() {
        lock = true;
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