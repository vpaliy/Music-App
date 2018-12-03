package com.vpaliy.melophile.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.bus.RxBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import javax.inject.Inject;

public abstract class BaseFragment extends Fragment {

  private Unbinder unbinder;

  @Inject
  protected RxBus rxBus;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    initializeDependencies();
  }

  public void bind(View root) {
    unbinder = ButterKnife.bind(this, root);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(layoutId(), container, false);
    bind(view);
    return view;
  }

  protected void showMessage(@StringRes int res) {
    if (getView() != null) {
      Snackbar.make(getView(), res, getResources().getInteger(R.integer.message_duration));
    }
  }

  @LayoutRes
  protected int layoutId() {
    return -1;
  }

  public void initializeDependencies() {
  }

  ;
}