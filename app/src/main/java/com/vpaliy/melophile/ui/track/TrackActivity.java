package com.vpaliy.melophile.ui.track;


import android.os.Bundle;

import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.utils.Constants;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class TrackActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_playlist);
    getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    if (savedInstanceState == null) {
      if (getIntent().getExtras() != null) {
        savedInstanceState = getIntent().getExtras().getBundle(Constants.EXTRA_DATA);
      }
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.frame, TrackFragment.newInstance(savedInstanceState))
              .commit();
    }
  }

  @Override
  public void inject() {
    App.appInstance().appComponent().inject(this);
  }

  @Override
  public void handleEvent(@NonNull Object event) {
  }
}
