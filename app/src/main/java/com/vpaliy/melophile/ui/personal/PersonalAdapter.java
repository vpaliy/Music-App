package com.vpaliy.melophile.ui.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.vpaliy.melophile.ui.user.info.InfoEvent;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.BindView;

import android.support.annotation.NonNull;

public class PersonalAdapter extends BaseAdapter<PersonalAdapter.CategoryWrapper> {

  private static final int ME = 1;
  private static final int INFO = 2;

  private User user;

  public PersonalAdapter(@NonNull Context context, @NonNull RxBus rxBus) {
    super(context, rxBus);
  }

  class UserViewHolder extends GenericViewHolder {

    @BindView(R.id.avatar)
    ImageView image;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.likes)
    TextView likes;

    UserViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      followers.setOnClickListener(view -> rxBus.sendWithLock(InfoEvent.showFollowers(user.getId())));
      likes.setOnClickListener(view -> rxBus.sendWithLock(InfoEvent.showFavorites(user.getId())));
    }

    @Override
    public void onBindData() {
      if (user != null) {
        final Context context = itemView.getContext();
        Glide.with(itemView.getContext())
                .load(user.getAvatarUrl())
                .priority(Priority.IMMEDIATE)
                .into(image);
        nickname.setText(user.getNickName());
        followers.setText(context.getResources().getQuantityString(R.plurals.followers,
                user.getFollowersCount(), user.getFollowersCount()));
        likes.setText(context.getResources().getQuantityString(R.plurals.likes,
                user.getLikedTracksCount(), user.getLikedTracksCount()));
      }
    }
  }

  class TypeViewHolder extends GenericViewHolder {

    @BindView(R.id.playlists)
    RecyclerView list;
    @BindView(R.id.title)
    TextView title;

    TypeViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      list.setNestedScrollingEnabled(false);
    }

    @Override
    public void onBindData() {
      CategoryWrapper wrapper = at(getAdapterPosition() - 1);
      if (wrapper.adapter != null) {
        list.setAdapter(wrapper.adapter);
      }
      title.setText(wrapper.text);
    }
  }

  @Override
  public void onBindViewHolder(GenericViewHolder holder, int position) {
    holder.onBindData();
  }

  @Override
  public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ME:
        return new UserViewHolder(inflate(R.layout.layout_me, parent));
      default:
        View root = inflater.inflate(R.layout.adapter_my_media, parent, false);
        return new TypeViewHolder(root);
    }
  }

  @Override
  public int getItemCount() {
    return data.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    return position == 0 ? ME : INFO;
  }

  public void setUser(User user) {
    this.user = user;
    notifyDataSetChanged();
  }

  public static class CategoryWrapper {
    private final String text;
    private final RecyclerView.Adapter<?> adapter;

    private CategoryWrapper(@NonNull String text, RecyclerView.Adapter<?> adapter) {
      this.text = text;
      this.adapter = adapter;
    }

    public static CategoryWrapper wrap(@NonNull String text, RecyclerView.Adapter<?> adapter) {
      return new CategoryWrapper(text, adapter);
    }
  }
}