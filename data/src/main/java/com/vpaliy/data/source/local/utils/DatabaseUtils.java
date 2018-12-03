package com.vpaliy.data.source.local.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.vpaliy.data.mapper.MapperUtils;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;

import java.util.List;

import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.Users;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.TracksPlaylists;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.LikedTracks;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.UserFollowers;
import static com.vpaliy.data.source.local.MusicContract.MelophileThemes;

public final class DatabaseUtils {

  private static final String TAG = DatabaseUtils.class.getSimpleName();

  public static ContentValues toValues(User user, Track track) {
    if (user == null || track == null) return null;
    ContentValues values = new ContentValues();
    values.put(LikedTracks.TRACK_ID, track.getId());
    values.put(LikedTracks.USER_ID, user.getId());
    return values;
  }

  public static ContentValues toValues(Track track, Playlist playlist) {
    if (track == null || playlist == null) return null;
    ContentValues values = new ContentValues();
    values.put(TracksPlaylists.TRACK_ID, track.getId());
    values.put(TracksPlaylists.PLAYLIST_ID, playlist.getId());
    return values;
  }

  public static ContentValues toValues(User user, User follower) {
    if (user == null || follower == null || TextUtils.equals(user.getId(), follower.getId())) {
      return null;
    }
    ContentValues values = new ContentValues();
    values.put(UserFollowers.USER_ID, user.getId());
    values.put(UserFollowers.FOLLOWER_ID, follower.getId());
    return values;
  }

  public static ContentValues toValues(User user) {
    if (user == null) return null;
    ContentValues values = new ContentValues();
    values.put(Users.USER_ID, user.getId());
    values.put(Users.USER_ART_URL, user.getAvatarUrl());
    values.put(Users.USER_NICKNAME, user.getNickName());
    values.put(Users.USER_FULLNAME, user.getFullName());
    values.put(Users.USER_DESCRIPTION, user.getDescription());
    values.put(Users.USER_FOLLOWINGS_COUNT, user.getFollowingCount());
    values.put(Users.USER_FOLLOWER_COUNT, user.getFollowersCount());
    values.put(Users.USER_TRACKS_COUNT, user.getTracksCount());
    values.put(Users.USER_LIKED_TRACKS_COUNT, user.getLikedTracksCount());
    values.put(Users.USER_IS_FOLLOWED, user.isFollowed() ? 1 : 0);
    values.put(Users.USER_PLAYLISTS_COUNT, user.getPlaylistsCount());
    return values;
  }

  public static User toUser(Cursor cursor) {
    if (cursor == null) return null;
    String id = cursor.getString(cursor.getColumnIndex(Users.USER_ID));
    String art = cursor.getString(cursor.getColumnIndex(Users.USER_ART_URL));
    String nickname = cursor.getString(cursor.getColumnIndex(Users.USER_NICKNAME));
    String fullname = cursor.getString(cursor.getColumnIndex(Users.USER_FULLNAME));
    String description = cursor.getString(cursor.getColumnIndex(Users.USER_DESCRIPTION));
    int followings = cursor.getInt(cursor.getColumnIndex(Users.USER_FOLLOWINGS_COUNT));
    int followers = cursor.getInt(cursor.getColumnIndex(Users.USER_FOLLOWER_COUNT));
    int tracks = cursor.getInt(cursor.getColumnIndex(Users.USER_TRACKS_COUNT));
    int liked = cursor.getInt(cursor.getColumnIndex(Users.USER_LIKED_TRACKS_COUNT));
    int playlists = cursor.getInt(cursor.getColumnIndex(Users.USER_PLAYLISTS_COUNT));
    boolean is = cursor.getInt(cursor.getColumnIndex(Users.USER_IS_FOLLOWED)) == 1;
    //assign data
    User user = new User();
    user.setId(id);
    user.setAvatarUrl(art);
    user.setNickName(nickname);
    user.setFullName(fullname);
    user.setDescription(description);
    user.setFollowingCount(followings);
    user.setFollowersCount(followers);
    user.setTracksCount(tracks);
    user.setLikedTracksCount(liked);
    user.setPlaylistsCount(playlists);
    user.setFollowed(is);
    return user;
  }

  public static ContentValues toValues(Playlist playlist) {
    if (playlist == null) return null;
    ContentValues values = new ContentValues();
    values.put(Playlists.PLAYLIST_ID, playlist.getId());
    values.put(Playlists.PLAYLIST_ART_URL, playlist.getArtUrl());
    values.put(Playlists.PLAYLIST_DURATION, playlist.getDuration());
    values.put(Playlists.PLAYLIST_RELEASE_DATE, playlist.getReleaseDate());
    values.put(Playlists.PLAYLIST_TITLE, playlist.getTitle());
    values.put(Playlists.PLAYLIST_DESCRIPTION, playlist.getDescription());
    values.put(Playlists.PLAYLIST_TRACK_COUNT, playlist.getTrackCount());
    values.put(Playlists.PLAYLIST_GENRES, MapperUtils.toString(playlist.getGenres()));
    values.put(Playlists.PLAYLIST_TAGS, MapperUtils.toString(playlist.getTags()));
    User user = playlist.getUser();
    if (user != null) {
      values.put(Playlists.PLAYLIST_USER_ID, user.getId());
    }
    return values;
  }

  public static Playlist toPlaylist(Cursor cursor) {
    if (cursor == null) return null;
    String id = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_ID));
    String art = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_ART_URL));
    String duration = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_DURATION));
    String release = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_RELEASE_DATE));
    String title = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_TITLE));
    String description = cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_DESCRIPTION));
    List<String> genres = MapperUtils.splitString(cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_GENRES)));
    List<String> tags = MapperUtils.splitString(cursor.getString(cursor.getColumnIndex(Playlists.PLAYLIST_TAGS)));
    int tracks = cursor.getInt(cursor.getColumnIndex(Playlists.PLAYLIST_TRACK_COUNT));

    Playlist playlist = new Playlist();
    playlist.setId(id);
    playlist.setArtUrl(art);
    playlist.setDuration(duration);
    playlist.setReleaseDate(release);
    playlist.setTitle(title);
    playlist.setDescription(description);
    playlist.setGenres(genres);
    playlist.setTags(tags);
    playlist.setTrackCount(tracks);
    //TODO fetch user
    return playlist;
  }

  public static ContentValues toValues(Track track) {
    if (track == null) return null;
    ContentValues values = new ContentValues();
    values.put(Tracks.TRACK_ID, track.getId());
    values.put(Tracks.TRACK_STREAM_URL, track.getStreamUrl());
    values.put(Tracks.TRACK_ART_URL, track.getArtworkUrl());
    values.put(Tracks.TRACK_DURATION, track.getDuration());
    values.put(Tracks.TRACK_TAGS, MapperUtils.toString(track.getTags()));
    values.put(Tracks.TRACK_RELEASE_DATE, track.getReleaseDate());
    values.put(Tracks.TRACK_TITLE, track.getTitle());
    values.put(Tracks.TRACK_ARTIST, track.getArtist());
    values.put(Tracks.TRACK_IS_LIKED, track.isLiked() ? 1 : 0);
    //get the user
    User user = track.getUser();
    if (user != null) {
      values.put(Tracks.TRACK_USER_ID, user.getId());
    }
    return values;
  }

  public static Track toTrack(Cursor cursor) {
    if (cursor == null) return null;
    String id = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_ID));
    String stream = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_STREAM_URL));
    String art = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_ART_URL));
    String duration = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_DURATION));
    String date = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_RELEASE_DATE));
    String title = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_TITLE));
    String artist = cursor.getString(cursor.getColumnIndex(Tracks.TRACK_ARTIST));
    List<String> tags = MapperUtils.splitString(cursor.getString(cursor.getColumnIndex(Tracks.TRACK_TAGS)));
    boolean isLiked = cursor.getInt(cursor.getColumnIndex(Tracks.TRACK_IS_LIKED)) == 1;

    Track track = new Track();
    track.setId(id);
    track.setArtist(artist);
    track.setStreamUrl(stream);
    track.setDuration(duration);
    track.setTitle(title);
    track.setReleaseDate(date);
    track.setArtworkUrl(art);
    track.setTags(tags);
    track.setLiked(isLiked);
    //TODO fetch the user
    return track;
  }

  public static ContentValues toValues(MelophileTheme theme, Track track) {
    if (theme == null || track == null) return null;
    ContentValues values = new ContentValues();
    values.put(MelophileThemes.MELOPHILE_THEME_ID, theme.getTheme());
    values.put(MelophileThemes.MELOPHILE_ITEM_ID, track.getId());
    return values;
  }

  public static ContentValues toValues(MelophileTheme theme, Playlist playlist) {
    if (theme == null || playlist == null) return null;
    ContentValues values = new ContentValues();
    values.put(MelophileThemes.MELOPHILE_THEME_ID, theme.getTheme());
    values.put(MelophileThemes.MELOPHILE_ITEM_ID, playlist.getId());
    return values;
  }
}
