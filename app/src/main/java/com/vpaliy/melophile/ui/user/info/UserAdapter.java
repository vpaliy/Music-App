package com.vpaliy.melophile.ui.user.info;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.Constants;

import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import butterknife.BindView;

@SuppressWarnings("WeakerAccess")
public class UserAdapter extends BaseAdapter<User> {

    public UserAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class UserViewHolder extends GenericViewHolder{

        @BindView(R.id.user_art) ImageView art;
        @BindView(R.id.user_name) TextView username;

        public UserViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(view->{
                final Context context=itemView.getContext();
                final User userModel=at(getAdapterPosition());
                Bundle data = new Bundle();
                data.putString(Constants.EXTRA_ID,userModel.getId());
                data.putString(Constants.EXTRA_DATA,userModel.getAvatarUrl());
                rxBus.send(ExposeEvent.exposeUser(data, Pair.create(art,context.getString(R.string.background_trans_name)),
                        Pair.create(art,context.getString(R.string.user_trans_name))));
            });
        }

        @Override
        public void onBindData() {
            User user=at(getAdapterPosition());
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .into(art);
            username.setText(user.getNickName());
        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(inflate(R.layout.adapter_follower,parent));
    }
}
