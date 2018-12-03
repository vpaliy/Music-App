package com.vpaliy.data.source.remote;

import com.vpaliy.data.source.SearchSource;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.Page;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RemoteSearchSource implements SearchSource {

  private SoundCloudService service;
  private Filter filter;
  private Map<Type, Page<?>> queryMap;

  @Inject
  public RemoteSearchSource(@NonNull SoundCloudService soundCloudService,
                            @NonNull Filter filter) {
    this.service = soundCloudService;
    this.filter = filter;
    this.queryMap = new HashMap<>();
  }

  @Override
  public Single<List<PlaylistEntity>> searchPlaylists(@NonNull String query) {
    return service.searchPlaylistsPage(PlaylistEntity.Filter.start()
            .byName(query).limit(100)
            .withPagination().createOptions())
            .map(page -> {
              if (page != null) {
                queryMap.put(Type.PLAYLIST, page);
                return page.collection;
              }
              return null;
            }).map(filter::filterPlaylists);
  }

  @Override
  public Single<List<TrackEntity>> searchTracks(@NonNull String query) {
    return service.searchTracksPage(TrackEntity.Filter.start()
            .byName(query).withPagination()
            .limit(100).createOptions())
            .map(page -> {
              if (page != null) {
                queryMap.put(Type.TRACK, page);
                return page.collection;
              }
              return null;
            }).map(filter::filterTracks);
  }

  @Override
  public Single<List<UserEntity>> searchUsers(@NonNull String query) {
    return service.searchUsersPage(UserEntity.Filter.start()
            .byName(query).withPagination()
            .limit(100).createOptions())
            .map(page -> {
              if (page != null) {
                queryMap.put(Type.USER, page);
                return page.collection;
              }
              return null;
            });
  }

  @Override
  public Single<List<PlaylistEntity>> morePlaylists() {
    Page<?> page = queryMap.get(Type.PLAYLIST);
    if (page != null) {
      if (page.isLast) {
        return Single.just(new ArrayList<>());
      }
      return service.searchPlaylistsPage(PlaylistEntity.Filter.start()
              .nextPage(page).limit(100).createOptions())
              .map(result -> {
                if (result != null) {
                  queryMap.put(Type.PLAYLIST, result);
                  return result.collection;
                }
                return null;
              }).map(filter::filterPlaylists);
    }
    return Single.error(new IllegalArgumentException("You haven't made a query!"));
  }

  @Override
  public Single<List<TrackEntity>> moreTracks() {
    Page<?> page = queryMap.get(Type.TRACK);
    if (page != null) {
      if (page.isLast) {
        return Single.just(new ArrayList<>());
      }
      return service.searchTracksPage(TrackEntity.Filter.start()
              .nextPage(page).limit(100).createOptions())
              .map(result -> {
                if (result != null) {
                  queryMap.put(Type.TRACK, result);
                  return result.collection;
                }
                return null;
              }).map(filter::filterTracks);
    }
    return Single.error(new IllegalArgumentException("You haven't made a query!"));
  }


  @Override
  public Single<List<UserEntity>> moreUsers() {
    Page<?> page = queryMap.get(Type.USER);
    if (page != null) {
      if (page.isLast) {
        return Single.just(new ArrayList<>());
      }
      return service.searchUsersPage(UserEntity.Filter.start()
              .nextPage(page).limit(100).createOptions())
              .map(result -> {
                if (result != null) {
                  queryMap.put(Type.USER, result);
                  return result.collection;
                }
                return null;
              }).map(filter::filterUsers);
    }
    return Single.error(new IllegalArgumentException("You haven't made a query!"));
  }

  private enum Type {
    TRACK, PLAYLIST, USER
  }
}
