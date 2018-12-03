package com.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.net.Uri;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class MusicContract {

  interface TrackColumns {
    String TRACK_ID = "track_id";
    String TRACK_STREAM_URL = "track_stream_url";
    String TRACK_ART_URL = "track_art_url";
    String TRACK_DURATION = "track_duration";
    String TRACK_TAGS = "track_tags";
    String TRACK_RELEASE_DATE = "track_release_date";
    String TRACK_TITLE = "track_title";
    String TRACK_ARTIST = "track_artist";
    String TRACK_IS_LIKED = "track_is_liked";
    String TRACK_USER_ID = "ref_track_user_id";
    String TRACK_USER_LIKED_ID = "ref_track_user_liked_id";
    String TRACK_PLAYLIST_ID = "ref_track_playlist_id";

    String[] COLUMNS = {TRACK_ID, TRACK_STREAM_URL,
            TRACK_ART_URL, TRACK_DURATION, TRACK_TAGS,
            TRACK_RELEASE_DATE, TRACK_TITLE, TRACK_ARTIST,
            TRACK_IS_LIKED, TRACK_USER_ID, TRACK_PLAYLIST_ID,
            TRACK_USER_LIKED_ID};
  }

  interface PlaylistColumns {
    String PLAYLIST_ID = "playlist_id";
    String PLAYLIST_ART_URL = "playlist_art_url";
    String PLAYLIST_DURATION = "playlist_duration";
    String PLAYLIST_RELEASE_DATE = "playlist_release_date";
    String PLAYLIST_TITLE = "playlist_title";
    String PLAYLIST_DESCRIPTION = "playlist_description";
    String PLAYLIST_TRACK_COUNT = "playlist_track_count";
    String PLAYLIST_GENRES = "playlist_genres";
    String PLAYLIST_TAGS = "playlist_tags";
    String PLAYLIST_USER_ID = "ref_playlist_user_id";

    String[] COLUMNS = {PLAYLIST_ID, PLAYLIST_ART_URL,
            PLAYLIST_DURATION, PLAYLIST_RELEASE_DATE, PLAYLIST_TITLE,
            PLAYLIST_DESCRIPTION, PLAYLIST_TRACK_COUNT, PLAYLIST_GENRES,
            PLAYLIST_TAGS, PLAYLIST_USER_ID};
  }

  interface MeColumns {
    String ME_ID = "me_id";
    String ME_ART_URL = "me_art_url";
    String ME_NICKNAME = "me_nickname";
    String ME_FULLNAME = "me_fullname";
    String ME_DESCRIPTION = "me_description";
    String ME_FOLLOWINGS_COUNT = "me_followings_count";
    String ME_TRACKS_COUNT = "me_tracks_count";
    String ME_PLAYLISTS_COUNT = "me_playlists_count";
    String ME_FOLLOWER_COUNT = "me_follower_count";
    String ME_LIKED_TRACKS_COUNT = "me_liked_tracks_count";

    String[] COLUMNS = {ME_ART_URL, ME_NICKNAME, ME_FULLNAME, ME_DESCRIPTION,
            ME_DESCRIPTION, ME_FOLLOWER_COUNT, ME_TRACKS_COUNT,
            ME_PLAYLISTS_COUNT, ME_FOLLOWER_COUNT, ME_LIKED_TRACKS_COUNT};
  }

  interface UserColumns {
    String USER_ID = "user_id";
    String USER_ART_URL = "user_art_url";
    String USER_NICKNAME = "user_nickname";
    String USER_FULLNAME = "user_fullname";
    String USER_DESCRIPTION = "user_description";
    String USER_FOLLOWINGS_COUNT = "user_followings_count";
    String USER_TRACKS_COUNT = "user_tracks_count";
    String USER_PLAYLISTS_COUNT = "user_playlists_count";
    String USER_FOLLOWER_COUNT = "user_follower_count";
    String USER_IS_FOLLOWED = "user_is_followed";
    String USER_LIKED_TRACKS_COUNT = "user_liked_tracks_count";

    String[] COLUMNS = {USER_ID, USER_ART_URL, USER_NICKNAME,
            USER_FULLNAME, USER_NICKNAME, USER_DESCRIPTION,
            USER_FOLLOWINGS_COUNT, USER_TRACKS_COUNT, USER_PLAYLISTS_COUNT,
            USER_FOLLOWER_COUNT, USER_IS_FOLLOWED, USER_LIKED_TRACKS_COUNT};
  }

  interface HistoryColumns {
    String HISTORY_ITEM_ID = "history_item_id";
  }


  interface MelophileThemeColumns {
    String MELOPHILE_THEME_ID = "melophile_theme_id";
    String MELOPHILE_ITEM_ID = "melophile_item_id";
  }

  public static final String CONTENT_AUTHORITY = "com.vpaliy.melophile";

  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static final String PATH_TRACK = "tracks";
  public static final String PATH_PLAYLIST = "playlists";
  public static final String PATH_USER = "users";
  public static final String PATH_ME = "me";
  public static final String PATH_HISTORY = "history";
  public static final String PATH_HISTORY_TRACKS = "history/tracks";
  public static final String PATH_HISTORY_PLAYLISTS = "history/playlists";
  public static final String PATH_MELOPHILE_THEMES = "melophile";
  public static final String PATH_MELOPHILE_TRACKS = "melophile/tracks";
  public static final String PATH_MELOPHILE_PLAYLISTS = "melophile/playlists";

  private MusicContract() {
    throw new IllegalArgumentException();
  }

  public static class Tracks implements TrackColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRACK).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRACK;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRACK;

    public static Uri buildTrackUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).build();
    }

    public static Uri buildTrackUserUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_USER).build();
    }

    public static String getTrackId(Uri uri) {
      return uri.getPathSegments().get(1);
    }
  }

  public static class Playlists implements PlaylistColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYLIST).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLIST;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLIST;

    public static Uri buildPlaylistUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).build();
    }

    public static Uri buildPlaylistTracksUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_PLAYLIST).build();
    }

    public static Uri buildPlaylistUserUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_USER).build();
    }

    public static String getPlaylistId(Uri uri) {
      return uri.getPathSegments().get(1);
    }
  }

  public static class Users implements UserColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

    public static Uri buildUserUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).build();
    }

    public static Uri buildUserFollowersUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_USER).build();
    }

    public static Uri buildFavoritesUri(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_TRACK).build();
    }

    public static String getUserId(Uri uri) {
      return uri.getPathSegments().get(1);
    }
  }

  public static class Me implements MeColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ME).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ME;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ME;

    public static Uri buildMeUri() {
      return CONTENT_URI.buildUpon().build();
    }

    public static String getMeId(Uri uri) {
      return uri.getPathSegments().get(1);
    }

    public static Uri buildMyLikedUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_TRACK).build();
    }

    public static Uri buildMyFollowingsUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
    }
  }

  public static class History implements HistoryColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HISTORY).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

    public static Uri buildTracksHistoryUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_TRACK).build();
    }

    public static Uri buildPlaylistsHistoryUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_PLAYLIST).build();
    }

    public static Uri buildPlaylistGetUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_PLAYLIST).appendPath(PATH_HISTORY).build();
    }

    public static Uri buildTrackGetUri() {
      return CONTENT_URI.buildUpon().appendPath(PATH_TRACK).appendPath(PATH_HISTORY).build();
    }
  }

  public static class MelophileThemes implements MelophileThemeColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MELOPHILE_THEMES).build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MELOPHILE_THEMES;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MELOPHILE_THEMES;

    public static Uri buildPlaylistsTheme(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_PLAYLIST).build();
    }

    public static Uri buildTracksTheme(String id) {
      return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_TRACK).build();
    }

    public static Uri buildPlaylistsTheme() {
      return CONTENT_URI.buildUpon().appendPath(PATH_PLAYLIST).build();
    }

    public static Uri buildTracksTheme() {
      return CONTENT_URI.buildUpon().appendPath(PATH_TRACK).build();
    }

    public static String getId(Uri uri) {
      return uri.getPathSegments().get(1);
    }
  }
}
