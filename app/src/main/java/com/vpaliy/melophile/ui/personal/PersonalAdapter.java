package com.vpaliy.melophile.ui.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class PersonalAdapter  extends BaseAdapter<PersonalAdapter.CategoryWrapper>{

    public PersonalAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    class TypeViewHolder extends GenericViewHolder
            implements View.OnClickListener{

        @BindView(R.id.playlists) RecyclerView list;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.more) TextView more;

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
            CategoryWrapper wrapper=at(getAdapterPosition());
            list.setAdapter(wrapper.adapter);
            title.setText(wrapper.text);
        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_user_media,parent,false);
        return new TypeViewHolder(root);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CategoryWrapper {
        private final String text;
        private final RecyclerView.Adapter<?> adapter;

        private CategoryWrapper(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter){
            this.text=text;
            this.adapter=adapter;
        }

        public static CategoryWrapper wrap(@NonNull String text, @NonNull RecyclerView.Adapter<?> adapter){
            return new CategoryWrapper(text,adapter);
        }
    }
}