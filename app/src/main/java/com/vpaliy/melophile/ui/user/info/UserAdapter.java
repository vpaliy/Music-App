package com.vpaliy.melophile.ui.user.info;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import android.support.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

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
