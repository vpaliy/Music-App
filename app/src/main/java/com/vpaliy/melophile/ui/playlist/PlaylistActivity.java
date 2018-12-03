package com.vpaliy.melophile.ui.playlist;

import android.os.Bundle;

import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.Constants;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class PlaylistActivity extends BaseActivity {

  private Bundle data;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_playlist);
    getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    if (savedInstanceState == null) {
      savedInstanceState = getIntent().getExtras();
    }
    data = savedInstanceState.getBundle(Constants.EXTRA_DATA);
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame, PlaylistFragment.newInstance(data))
            .commit();
  }

  @Override
  public void inject() {
    App.appInstance().appComponent().inject(this);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBundle(Constants.EXTRA_DATA, data);
  }

  @Override
  public void handleEvent(@NonNull Object event) {
    if (event instanceof ExposeEvent) {
      navigator.navigate(this, (ExposeEvent) (event));
    }
  }
}
