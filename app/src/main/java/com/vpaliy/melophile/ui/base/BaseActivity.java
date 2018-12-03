package com.vpaliy.melophile.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vpaliy.melophile.ui.base.bus.RxBus;

import io.reactivex.disposables.CompositeDisposable;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

  protected CompositeDisposable disposables;

  @Inject
  protected RxBus eventBus;

  @Inject
  protected Navigator navigator;

  /**
   * Handle the user events
   */
  public abstract void handleEvent(@NonNull Object event);

  /**
   * Initialize the dependencies
   */
  public abstract void inject();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    inject();
    disposables = new CompositeDisposable();
    super.onCreate(savedInstanceState);
  }

  @CallSuper
  @Override
  protected void onResume() {
    super.onResume();
    disposables.add(eventBus.asFlowable()
            .subscribe(this::processEvent));
  }

  private void processEvent(Object object) {
    if (object != null) {
      handleEvent(object);
    }
  }

  @Override
  @CallSuper
  protected void onPause() {
    super.onPause();
    disposables.clear();
  }

  @CallSuper
  @Override
  protected void onStop() {
    super.onStop();
    disposables.clear();
  }
}
