package com.vpaliy.melophile.ui.search;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.utils.OnReachBottomListener;

import java.util.List;

import butterknife.BindView;

public class SearchResult<T> extends BaseFragment {

  @BindView(R.id.search_result)
  protected RecyclerView searchResult;

  @BindView(R.id.refresher)
  protected SwipeRefreshLayout refreshLayout;

  private BaseAdapter<T> adapter;

  @Override
  protected int layoutId() {
    return R.layout.fragment_search_result;
  }

  public void setAdapter(BaseAdapter<T> adapter) {
    this.adapter = adapter;
    if (adapter != null) {
      searchResult.setAdapter(adapter);
      searchResult.addOnScrollListener(new OnReachBottomListener(searchResult, refreshLayout) {
        @Override
        public void onLoadMore() {
          refreshLayout.setRefreshing(true);
          request();
        }
      });
    }
    //if the user pulls down, cancel
    refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));
  }

  @Override
  public void initializeDependencies() {
    App.appInstance().appComponent().inject(this);
  }

  public void appendData(List<T> data) {
    if (adapter != null) {
      adapter.appendData(data);
    }
  }

  public void stopRefreshing() {
    if (refreshLayout.isRefreshing()) {
      refreshLayout.setRefreshing(false);
    }
  }

  private void request() {
    rxBus.send(MoreEvent.requestMore(this));
  }
}
