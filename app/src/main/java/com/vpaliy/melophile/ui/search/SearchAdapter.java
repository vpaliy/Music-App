package com.vpaliy.melophile.ui.search;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseAdapter;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class SearchAdapter extends FragmentStatePagerAdapter {

  private static final String TAG = SearchAdapter.class.getSimpleName();

  public static final int TYPE_TRACKS = 0;
  public static final int TYPE_PLAYLISTS = 1;
  public static final int TYPE_USERS = 2;

  private SearchResult<Track> trackResult;
  private SearchResult<Playlist> playlistResult;
  private SearchResult<User> userResult;
  private Context context;

  private ArrayMap<SearchResult<?>, Integer> resultMap;

  public SearchAdapter(Context context, FragmentManager manager) {
    super(manager);
    this.context = context;
    resultMap = new ArrayMap<>();
  }

  @Override
  public SearchResult<?> getItem(int position) {
    switch (position) {
      case TYPE_TRACKS:
        if (trackResult == null) {
          trackResult = new SearchResult<>();
          resultMap.put(trackResult, TYPE_TRACKS);
        }
        return trackResult;
      case TYPE_PLAYLISTS:
        if (playlistResult == null) {
          playlistResult = new SearchResult<>();
          resultMap.put(playlistResult, TYPE_PLAYLISTS);
        }
        return playlistResult;
      default:
        if (userResult == null) {
          userResult = new SearchResult<>();
          resultMap.put(userResult, TYPE_USERS);
        }
        return userResult;
    }
  }

  public void appendTracks(List<Track> data) {
    trackResult.appendData(data);
    trackResult.stopRefreshing();
  }

  public void appendPlaylists(List<Playlist> data) {
    playlistResult.appendData(data);
    playlistResult.stopRefreshing();
  }

  public void appendUsers(List<User> data) {
    userResult.appendData(data);
    userResult.stopRefreshing();
  }

  public void setTracks(BaseAdapter<Track> tracks) {
    trackResult.setAdapter(tracks);
  }

  public void setPlaylists(BaseAdapter<Playlist> playlists) {
    playlistResult.setAdapter(playlists);
  }

  public void setUsers(BaseAdapter<User> users) {
    userResult.setAdapter(users);
  }

  public int getType(SearchResult result) {
    if (result == null) return -1;
    return resultMap.get(result);
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return context.getString(R.string.tracks_label);
      case 1:
        return context.getString(R.string.playlist_label);
      default:
        return context.getString(R.string.profiles_label);
    }
  }
}
