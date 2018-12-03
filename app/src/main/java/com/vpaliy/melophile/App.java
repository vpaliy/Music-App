package com.vpaliy.melophile;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vpaliy.melophile.di.component.ApplicationComponent;
import com.vpaliy.melophile.di.component.DaggerApplicationComponent;
import com.vpaliy.melophile.di.component.DaggerPlayerComponent;
import com.vpaliy.melophile.di.component.PlayerComponent;
import com.vpaliy.melophile.di.module.ApplicationModule;
import com.vpaliy.melophile.di.module.DataModule;
import com.vpaliy.melophile.di.module.InteractorModule;
import com.vpaliy.melophile.di.module.MapperModule;
import com.vpaliy.melophile.di.module.NetworkModule;
import com.vpaliy.melophile.di.module.PlaybackModule;
import com.vpaliy.soundcloud.model.Token;

public class App extends Application {

  private ApplicationComponent applicationComponent;
  private PlayerComponent playerComponent;
  private static App instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
  }

  private void initializePlayerComponent() {
    playerComponent = DaggerPlayerComponent.builder()
            .applicationComponent(applicationComponent)
            .playbackModule(new PlaybackModule()).build();
  }

  public void setApplicationComponent(ApplicationComponent applicationComponent) {
    this.applicationComponent = applicationComponent;
  }

  public void appendToken(Token token) {
    initializeAppComponent(token);
  }

  private void initializeAppComponent(Token token) {
    if (applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent.builder()
              .applicationModule(new ApplicationModule(this))
              .dataModule(new DataModule())
              .networkModule(new NetworkModule(token))
              .mapperModule(new MapperModule())
              .interactorModule(new InteractorModule())
              .build();
      initializePlayerComponent();
    }
  }

  @NonNull
  public static App appInstance() {
    return instance;
  }

  public ApplicationComponent appComponent() {
    return applicationComponent;
  }

  public PlayerComponent playerComponent() {
    return playerComponent;
  }
}