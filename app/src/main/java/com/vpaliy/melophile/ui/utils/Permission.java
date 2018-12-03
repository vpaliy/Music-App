package com.vpaliy.melophile.ui.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public final class Permission {

  private Permission() {
    throw new UnsupportedOperationException();
  }

  @TargetApi(Build.VERSION_CODES.M)
  public static boolean checkPermission(Context context, String permission) {
    return !checkForVersion(Build.VERSION_CODES.M)
            || context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean checkForVersion(int version) {
    return Build.VERSION.SDK_INT >= version;
  }
}