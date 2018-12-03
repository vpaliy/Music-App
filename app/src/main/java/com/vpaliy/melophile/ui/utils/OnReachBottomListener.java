package com.vpaliy.melophile.ui.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class OnReachBottomListener extends RecyclerView.OnScrollListener {

  private static final int VISIBLE_THRESHOLD = 5;

  private final RecyclerView.LayoutManager layoutManager;
  private final SwipeRefreshLayout dataLoading;

  public OnReachBottomListener(@NonNull RecyclerView recyclerView,
                               @Nullable SwipeRefreshLayout dataLoading) {
    this.layoutManager = recyclerView.getLayoutManager();
    this.dataLoading = dataLoading;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    // bail out if scrolling upward or already loading data
    final int firstVisibleItem = fetchFirstVisibleItemPosition();
    if (dy < 0 || (dataLoading != null && dataLoading.isRefreshing()) || firstVisibleItem == -1)
      return;

    final int visibleItemCount = recyclerView.getChildCount();
    final int totalItemCount = layoutManager.getItemCount();

    if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
      onLoadMore();
    }
  }

  private int fetchFirstVisibleItemPosition() {
    if (layoutManager instanceof LinearLayoutManager) {
      return LinearLayoutManager.class.cast(layoutManager).findFirstVisibleItemPosition();
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      StaggeredGridLayoutManager manager = StaggeredGridLayoutManager.class.cast(layoutManager);
      int[] result = manager.findFirstVisibleItemPositions(null);
      if (result != null && result.length > 0) {
        return result[0];
      }
    }
    return -1;
  }

  public abstract void onLoadMore();
}