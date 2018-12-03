package com.vpaliy.domain.executor;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

  @NonNull
  public Scheduler computation() {
    return Schedulers.computation();
  }

  @NonNull
  public Scheduler io() {
    return Schedulers.io();
  }

  @NonNull
  public Scheduler multi() {
    return Schedulers.newThread();
  }

  @NonNull
  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}