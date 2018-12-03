package com.vpaliy.data.source.local.handler;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.vpaliy.data.source.local.MusicContract;
import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.vpaliy.data.source.local.MusicContract.Users;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings({"unused", "WeakerAccess"})
@Singleton
public class UserHandler {

  private ContentResolver provider;

  @Inject
  public UserHandler(@NonNull Context context) {
    this.provider = context.getContentResolver();
  }

  public UserHandler(@NonNull ContentResolver resolver) {
    this.provider = resolver;
  }

  public List<User> queryAll(Query query) {
    if (query != null) {
      Cursor cursor = provider.query(Users.CONTENT_URI, null, query.selection(), query.args(), null);
      return queryAll(cursor);
    }
    return queryAll();
  }

  public User query(String id) {
    if (!TextUtils.isEmpty(id)) {
      Cursor cursor = provider.query(Users.buildUserUri(id), null, null, null, null);
      if (cursor != null && cursor.moveToFirst()) {
        User user = DatabaseUtils.toUser(cursor);
        if (!cursor.isClosed()) cursor.close();
        return user;
      }
      return null;
    }
    throw new IllegalArgumentException("Id is null");
  }

  private List<User> queryAll() {
    Cursor cursor = provider.query(Users.CONTENT_URI, null, null, null, null);
    return queryAll(cursor);
  }

  private List<User> queryAll(Cursor cursor) {
    if (cursor != null) {
      List<User> users = new ArrayList<>(cursor.getCount());
      while (cursor.moveToNext()) {
        User user = DatabaseUtils.toUser(cursor);
        users.add(user);
      }
      if (!cursor.isClosed()) cursor.close();
      return users;
    }
    return null;
  }

  public List<User> queryFollowers(String id) {
    if (!TextUtils.isEmpty(id)) {
      Cursor cursor = provider.query(Users.buildUserFollowersUri(id), null, null, null, null);
      if (cursor != null) {
        List<User> users = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          User user = DatabaseUtils.toUser(cursor);
          users.add(user);
        }
        if (!cursor.isClosed()) cursor.close();
        return users;
      }
      return null;
    }
    throw new IllegalArgumentException("Id is null");
  }

  public List<Track> queryFavorites(String id) {
    if (!TextUtils.isEmpty(id)) {
      Cursor cursor = provider.query(Users.buildFavoritesUri(id), null, null, null, null);
      if (cursor != null) {
        List<Track> tracks = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          Track track = DatabaseUtils.toTrack(cursor);
          tracks.add(track);
        }
        if (!cursor.isClosed()) cursor.close();
        return tracks;
      }
      return null;
    }
    throw new IllegalArgumentException("Id is null");
  }

  public List<User> queryFollowings() {
    Cursor cursor = provider.query(MusicContract.Me.buildMyFollowingsUri(), null, null, null, null);
    return queryAll(cursor);
  }

  public void insert(User user) {
    if (user != null) {
      ContentValues values = DatabaseUtils.toValues(user);
      provider.insert(Users.CONTENT_URI, values);
    }
  }

  public static UserHandler build(ContentResolver resolver) {
    return new UserHandler(resolver);
  }
}
