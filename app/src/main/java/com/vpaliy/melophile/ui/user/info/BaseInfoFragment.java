package com.vpaliy.melophile.ui.user.info;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.utils.Constants;

import butterknife.ButterKnife;

import static com.vpaliy.melophile.ui.user.info.UserInfoContract.Presenter;

import javax.inject.Inject;

import butterknife.BindView;

import android.support.annotation.Nullable;

import butterknife.OnClick;

public abstract class BaseInfoFragment<T> extends BottomSheetDialogFragment
        implements UserInfoContract.View<T> {
  //TODO private access ??
  protected String id;

  @BindView(R.id.favorites)
  protected RecyclerView data;

  @Inject
  protected RxBus rxBus;

  @BindView(R.id.title)
  protected TextView title;

  @BindView(R.id.action_bar)
  protected View actionBar;

  protected Presenter<T> presenter;

  protected Handler handler = new Handler();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    inject();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_user_favorite, container, false);
    ButterKnife.bind(this, root);
    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    extractId(savedInstanceState);
    if (view != null) {
      presenter.start(id);
      data.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          actionBar.setActivated(dy > 0 || (data.computeVerticalScrollOffset() != 0));
        }
      });
    }
  }

  protected abstract void inject();

  @OnClick(R.id.close)
  public void close() {
    getActivity().getSupportFragmentManager()
            .beginTransaction().remove(this).commit();
  }

  @Override
  public void showError() {
    //TODO add an error message
  }

  private void extractId(Bundle bundle) {
    if (bundle == null) bundle = getArguments();
    id = bundle.getString(Constants.EXTRA_ID);
  }

  @Override
  public void onStop() {
    super.onStop();
    handler.removeCallbacksAndMessages(null);
    if (presenter != null) {
      presenter.stop();
    }
  }
}
