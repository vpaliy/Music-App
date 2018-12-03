package com.vpaliy.data.source.local.handler;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.vpaliy.data.source.local.MusicContract;
import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.MelophileThemes;

@SuppressWarnings({"unused", "WeakerAccess"})
@Singleton
public class TrackHandler {

  private ContentResolver provider;

  @Inject
  public TrackHandler(@NonNull Context context) {
    this.provider = context.getContentResolver();
  }

  public List<Track> queryAll(Query query) {
    if (query != null) {
      Cursor cursor = provider.query(Tracks.CONTENT_URI, null, query.selection(), query.args(), null);
      return queryAll(cursor);
    }
    return queryAll();
  }

  private List<Track> queryAll(Cursor cursor) {
    if (cursor != null) {
      List<Track> tracks = new ArrayList<>();
      while (cursor.moveToNext()) {
        Track track = DatabaseUtils.toTrack(cursor);
        String userId = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_USER_ID));
        if (!TextUtils.isEmpty(userId)) {
          User user = UserHandler.build(provider).query(userId);
          track.setUser(user);
        }
        if (track != null) {
          tracks.add(track);
        }
      }
      if (!cursor.isClosed()) cursor.close();
      return tracks;
    }
    return null;
  }

  public Track query(String id) {
    if (!TextUtils.isEmpty(id)) {
      Cursor cursor = provider.query(Tracks.buildTrackUri(id), Tracks.COLUMNS, null, null, null);
      if (cursor != null && cursor.moveToFirst()) {
        Track track = DatabaseUtils.toTrack(cursor);
        String userId = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_USER_ID));
        if (!TextUtils.isEmpty(userId)) {
          User user = UserHandler.build(provider).query(userId);
          track.setUser(user);
        }
        if (!cursor.isClosed()) cursor.close();
        return track;
      }
      return null;
    }
    throw new IllegalArgumentException("Id is null");
  }

  public List<Track> queryAll() {
    Cursor cursor = provider.query(Tracks.CONTENT_URI, null, null, null, null);
    return queryAll(cursor);
  }

  public List<Track> queryByTheme(MelophileTheme theme) {
    if (theme != null) {
      Cursor cursor = provider.query(MelophileThemes.buildTracksTheme(theme.getTheme()), null, null, null, null);
      if (cursor != null) {
        List<Track> tracks = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          Track track = query(cursor.getString(cursor.getColumnIndex(MelophileThemes.MELOPHILE_ITEM_ID)));
          if (track != null) {
            tracks.add(track);
          }
        }
        if (!cursor.isClosed()) cursor.close();
        return tracks;
      }
      return null;
    }
    throw new IllegalArgumentException("Theme is null");
  }

  public void insert(Track track) {
    if (track != null) {
      User user = track.getUser();
      if (user != null) {
        provider.insert(MusicContract.Users.CONTENT_URI, DatabaseUtils.toValues(user));
      }
      ContentValues values = DatabaseUtils.toValues(track);
      provider.insert(Tracks.CONTENT_URI, values);
    }
  }

  public void insert(MelophileTheme theme, Track track) {
    ContentValues values = DatabaseUtils.toValues(theme, track);
    if (values != null) {
      provider.insert(MelophileThemes.buildTracksTheme(), values);
    }
  }

  public void save(Track track) {
    if (track != null) {
      insert(track);
      ContentValues values = new ContentValues();
      values.put(MusicContract.History.HISTORY_ITEM_ID, track.getId());
      provider.insert(MusicContract.History.buildTracksHistoryUri(), values);
    }
  }

  public List<Track> queryLiked() {
    Cursor cursor = provider.query(MusicContract.Me.buildMyLikedUri(), null, null, null, null);
    return queryAll(cursor);
  }

  public List<Track> queryHistory() {
    Cursor cursor = provider.query(MusicContract.History.buildTrackGetUri(), null, null, null, null);
    return queryAll(cursor);
  }

  public void clearHistory() {
    provider.delete(MusicContract.History.buildTracksHistoryUri(), null, null);
  }
}
