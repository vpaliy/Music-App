package com.vpaliy.melophile.di.module;

import android.content.Context;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.executor.SchedulerProvider;
import com.vpaliy.melophile.ui.base.Navigator;
import com.vpaliy.melophile.ui.base.bus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private Context context;

  public ApplicationModule(Context context) {
    this.context = context;
  }

  @Singleton
  @Provides
  Context context() {
    return context;
  }

  @Singleton
  @Provides
  RxBus rxBus() {
    return new RxBus();
  }

  @Singleton
  @Provides
  BaseSchedulerProvider schedulerProvider() {
    return new SchedulerProvider();
  }

  @Singleton
  @Provides
  Navigator navigator() {
    return new Navigator();
  }
}
