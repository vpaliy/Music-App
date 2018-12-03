package com.vpaliy.melophile.ui.user.info;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
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

  private boolean white = true;

  public UserAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  public UserAdapter(@NonNull Context context, @NonNull RxBus rxBus, boolean white) {
    super(context, rxBus);
    this.white = white;
  }

  public class UserViewHolder extends GenericViewHolder {

    @BindView(R.id.user_art)
    ImageView art;
    @BindView(R.id.user_name)
    TextView username;
    @BindView(R.id.followers_count)
    TextView followers;

    public UserViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(view -> {
        final Context context = itemView.getContext();
        final User userModel = at(getAdapterPosition());
        Bundle data = new Bundle();
        data.putString(Constants.EXTRA_ID, userModel.getId());
        data.putString(Constants.EXTRA_DATA, userModel.getAvatarUrl());
        rxBus.sendWithLock(ExposeEvent.exposeUser(data, Pair.create(art, context.getString(R.string.background_trans_name)),
                Pair.create(art, context.getString(R.string.user_trans_name))));
      });
    }

    @Override
    public void onBindData() {
      Resources resources = itemView.getResources();
      User user = at(getAdapterPosition());
      Glide.with(itemView.getContext())
              .load(user.getAvatarUrl())
              .priority(Priority.IMMEDIATE)
              .into(art);
      final int count = user.getFollowersCount();
      username.setText(user.getNickName());
      followers.setText(resources.getQuantityString(R.plurals.followers_horizontal, count, count));
    }
  }

  @Override
  public void onBindViewHolder(GenericViewHolder holder, int position) {
    holder.onBindData();
  }

  @Override
  public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    @LayoutRes int resource = white ? R.layout.adapter_white_follower : R.layout.adapter_follower;
    return new UserViewHolder(inflate(resource, parent));
  }
}
