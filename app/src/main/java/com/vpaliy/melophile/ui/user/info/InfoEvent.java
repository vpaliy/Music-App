package com.vpaliy.melophile.ui.user.info;

import android.os.Bundle;

import com.vpaliy.melophile.ui.utils.Constants;

public class InfoEvent {

  public static final int FAVORITE = 0;
  public static final int FOLLOWERS = 1;

  public final String id;
  public final int code;

  public InfoEvent(String id, int code) {
    this.id = id;
    this.code = code;
  }

  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    bundle.putString(Constants.EXTRA_ID, id);
    return bundle;
  }

  public static InfoEvent showFollowers(String id) {
    return new InfoEvent(id, FOLLOWERS);
  }

  public static InfoEvent showFavorites(String id) {
    return new InfoEvent(id, FAVORITE);
  }
}
