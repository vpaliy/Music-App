package com.vpaliy.data.source.local;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class MusicUriMatcher {

  private UriMatcher uriMatcher;
  private SparseArray<MusicMatchEnum> codeMap;

  public MusicUriMatcher() {
    buildUriMatcher();
    buildUriMap();
  }

  private void buildUriMatcher() {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    MusicMatchEnum[] enums = MusicMatchEnum.values();
    for (MusicMatchEnum uriEnum : enums) {
      uriMatcher.addURI(MusicContract.CONTENT_AUTHORITY, uriEnum.path, uriEnum.code);
    }
  }

  private void buildUriMap() {
    codeMap = new SparseArray<>();
    MusicMatchEnum[] enums = MusicMatchEnum.values();
    for (MusicMatchEnum uriEnum : enums) {
      codeMap.put(uriEnum.code, uriEnum);
    }
  }

  public MusicMatchEnum match(Uri uri) {
    final int code = uriMatcher.match(uri);
    if (codeMap.get(code) == null) {
      throw new UnsupportedOperationException("Unknown uri:" + uri.toString() + " with code " + code);
    }
    return codeMap.get(code);
  }

  public String getType(Uri uri) {
    final int code = uriMatcher.match(uri);
    if (codeMap.get(code) == null) {
      throw new UnsupportedOperationException("Unknown uri with code " + code);
    }
    return codeMap.get(code).contentType;
  }
}
