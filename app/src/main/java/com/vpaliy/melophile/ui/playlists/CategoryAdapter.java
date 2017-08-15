package com.vpaliy.melophile.ui.playlists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends BaseAdapter<CategoryAdapter.CategoryWrapper>{

    private List<CategoryWrapper> categories;

    public CategoryAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    class TypeViewHolder extends GenericViewHolder
            implements View.OnClickListener{

        RecyclerView list;

        @BindView(R.id.title)
        TextView title;

        TextView more;

        TypeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            list.setNestedScrollingEnabled(false);
            title.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public void onBindData(){
        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {

    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_playlist_category,parent,false);
        return new TypeViewHolder(root);
    }

    public static class CategoryWrapper {
        private final String text;
        private final RecyclerView.Adapter<?> adapter;
        private final int color;

        private CategoryWrapper(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter, int color){
            this.text=text;
            this.adapter=adapter;
            this.color=color;
        }

        public static CategoryWrapper wrap(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter, int color){
            return new CategoryWrapper(text,adapter,color);
        }
    }
}
