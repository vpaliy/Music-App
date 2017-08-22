package com.vpaliy.melophile.ui.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.support.annotation.Nullable;
import butterknife.BindView;

public class SearchResult extends Fragment {

    @BindView(R.id.search_result)
    protected RecyclerView searchResult;

    private BaseAdapter<?> adapter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_search_result,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    public void setAdapter(BaseAdapter<?> adapter) {
        this.adapter=adapter;
        if(this.adapter!=null){
            searchResult.setAdapter(this.adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }
}
