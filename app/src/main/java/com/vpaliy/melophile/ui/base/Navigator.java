package com.vpaliy.melophile.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.playlist.PlaylistActivity;
import com.vpaliy.melophile.ui.search.SearchActivity;
import com.vpaliy.melophile.ui.track.TrackActivity;
import com.vpaliy.melophile.ui.user.PersonActivity;
import com.vpaliy.melophile.ui.utils.Constants;
import com.vpaliy.melophile.ui.utils.Permission;

public class Navigator {

  public void navigate(Activity activity, ExposeEvent exposeEvent) {
    Class<?> clazz = PlaylistActivity.class;
    switch (exposeEvent.code) {
      case ExposeEvent.PLAYER:
        clazz = TrackActivity.class;
        break;
      case ExposeEvent.USER:
        clazz = PersonActivity.class;
    }
    Intent intent = new Intent(activity, clazz);
    intent.putExtra(Constants.EXTRA_DATA, exposeEvent.data);
    if (Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
      ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
              .makeSceneTransitionAnimation(activity, exposeEvent.pack);
      activity.startActivity(intent, optionsCompat.toBundle());
      return;
    }
    //in case if I wanted to switch to a lower API
    activity.startActivity(intent);
  }

  @SuppressWarnings("unchecked")
  public void search(Activity activity, Pair<View, String> pair) {
    Intent intent = new Intent(activity, SearchActivity.class);
    if (Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
      ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
              .makeSceneTransitionAnimation(activity, pair);
      activity.startActivity(intent, optionsCompat.toBundle());
      return;
    }
    //in case if I wanted to switch to a lower API
    activity.startActivity(intent);
  }
}
