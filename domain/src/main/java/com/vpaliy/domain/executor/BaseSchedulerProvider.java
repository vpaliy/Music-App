package com.vpaliy.domain.executor;


import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {
  @NonNull
  Scheduler computation();

  @NonNull
  Scheduler multi();

  @NonNull
  Scheduler io();

  @NonNull
  Scheduler ui();
}