package com.vpaliy.melophile.ui.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.utils.OnReachBottomListener;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

public class SearchResult extends Fragment {

    @BindView(R.id.search_result)
    protected RecyclerView searchResult;

    @Inject
    protected RxBus event;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_search_result,container,false);
        unbinder=ButterKnife.bind(this,root);
        App.appInstance().appComponent().inject(this);
        return root;
    }

    public void setAdapter(BaseAdapter<?> adapter) {
        if(adapter!=null){
            searchResult.setAdapter(adapter);
            searchResult.addOnScrollListener(new OnReachBottomListener(searchResult,null) {
                @Override
                public void onLoadMore() {
                    request();
                }
            });
        }
    }

    private void request(){
        event.send(MoreEvent.requestMore(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }
}
