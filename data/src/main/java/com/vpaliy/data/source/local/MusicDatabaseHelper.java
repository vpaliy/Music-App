package com.vpaliy.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.vpaliy.data.source.local.MusicContract.Users;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.History;
import static com.vpaliy.data.source.local.MusicContract.Me;
import static com.vpaliy.data.source.local.MusicContract.MelophileThemes;

import android.support.annotation.NonNull;

@SuppressWarnings({"unused", "WeakerAccess"})
public class MusicDatabaseHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "music_.db";
  private static final int DATABASE_VERSION = 3;

  public interface Tables {
    String TRACKS = "tracks";
    String PLAYLISTS = "playlists";
    String USERS = "users";
    String ME = "me";
    String MELOPHILE_TRACKS = "melophile_tracks";
    String MELOPHILE_PLAYLISTS = "melophile_playlists";
    String USER_FOLLOWERS = "user_followers";
    String TRACKS_PLAYLISTS = "tracks_playlists";
    String HISTORY_TRACK = "history_tracks";
    String HISTORY_PLAYLIST = "history_playlist";
    String LIKED_TRACKS = "liked_tracks";

    String USER_JOIN_TRACKS = "users " +
            "INNER JOIN tracks ON users.user_id=tracks.ref_track_user_id";
    String USER_JOIN_PLAYLISTS = "users " +
            "INNER JOIN tracks on users.user_id=playlists.ref_playlist_user_id";

    String HISTORY_JOIN_TRACKS = "history_tracks " +
            "INNER JOIN tracks ON history_tracks.history_item_id=tracks.track_id";

    String HISTORY_JOIN_PLAYLISTS = "history_playlist " +
            "INNER JOIN playlists ON history_playlist.history_item_id=playlists.playlist_id";
  }

  interface References {
    String USER = "REFERENCES " + Tables.USERS + "(" + Users.USER_ID + ")";
    String TRACK = "REFERENCES " + Tables.TRACKS + "(" + Tracks.TRACK_ID + ")";
    String PLAYLIST = "REFERENCES " + Tables.PLAYLISTS + "(" + Playlists.PLAYLIST_ID + ")";
  }

  public interface UserFollowers {
    String USER_ID = "ref_user_id";
    String FOLLOWER_ID = "ref_follower_id";
  }

  public interface TracksPlaylists {
    String TRACK_ID = "ref_track_id";
    String PLAYLIST_ID = "ref_playlist_id";
  }

  public interface LikedTracks {
    String TRACK_ID = "ref_track_id";
    String USER_ID = "ref_user_id";
  }

  public MusicDatabaseHelper(@NonNull Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + Tables.TRACKS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Tracks.TRACK_ID + " TEXT NOT NULL," +
            Tracks.TRACK_TITLE + " TEXT NOT NULL," +
            Tracks.TRACK_ART_URL + " TEXT NOT NULL," +
            Tracks.TRACK_STREAM_URL + " TEXT NOT NULL," +
            Tracks.TRACK_DURATION + " TEXT," +
            Tracks.TRACK_TAGS + " TEXT," +
            Tracks.TRACK_RELEASE_DATE + " TEXT," +
            Tracks.TRACK_ARTIST + " TEXT," +
            Tracks.TRACK_IS_LIKED + " INTEGER NOT NULL," +
            Tracks.TRACK_USER_ID + " TEXT " + References.USER + "," +
            Tracks.TRACK_USER_LIKED_ID + " TEXT " + References.USER + "," +
            Tracks.TRACK_PLAYLIST_ID + " TEXT " + References.PLAYLIST + "," +
            " UNIQUE (" + Tracks.TRACK_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.PLAYLISTS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Playlists.PLAYLIST_ID + " TEXT NOT NULL," +
            Playlists.PLAYLIST_ART_URL + " TEXT NOT NULL," +
            Playlists.PLAYLIST_DURATION + " TEXT, " +
            Playlists.PLAYLIST_RELEASE_DATE + " TEXT," +
            Playlists.PLAYLIST_TITLE + " TEXT NOT NULL," +
            Playlists.PLAYLIST_DESCRIPTION + " TEXT," +
            Playlists.PLAYLIST_TRACK_COUNT + " INTEGER NOT NULL," +
            Playlists.PLAYLIST_GENRES + " TEXT," +
            Playlists.PLAYLIST_TAGS + " TEXT," +
            Playlists.PLAYLIST_USER_ID + " TEXT " + References.USER + "," +
            " UNIQUE (" + Playlists.PLAYLIST_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.USERS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Users.USER_ID + " TEXT NOT NULL," +
            Users.USER_ART_URL + " TEXT NOT NULL," +
            Users.USER_NICKNAME + " TEXT NOT NULL," +
            Users.USER_FULLNAME + " TEXT," +
            Users.USER_DESCRIPTION + " TEXT," +
            Users.USER_FOLLOWINGS_COUNT + " INTEGER," +
            Users.USER_FOLLOWER_COUNT + " INTEGER," +
            Users.USER_TRACKS_COUNT + " INTEGER," +
            Users.USER_LIKED_TRACKS_COUNT + " INTEGER," +
            Users.USER_PLAYLISTS_COUNT + " INTEGER," +
            Users.USER_IS_FOLLOWED + " INTEGER," +
            " UNIQUE (" + Users.USER_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.ME + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Me.ME_ID + " TEXT NOT NULL," +
            Me.ME_ART_URL + " TEXT NOT NULL," +
            Me.ME_NICKNAME + " TEXT NOT NULL," +
            Me.ME_FULLNAME + " TEXT," +
            Me.ME_DESCRIPTION + " TEXT," +
            Me.ME_FOLLOWINGS_COUNT + " INTEGER," +
            Me.ME_FOLLOWER_COUNT + " INTEGER," +
            Me.ME_TRACKS_COUNT + " INTEGER," +
            Me.ME_LIKED_TRACKS_COUNT + " INTEGER," +
            Me.ME_PLAYLISTS_COUNT + " INTEGER," +
            " UNIQUE (" + Me.ME_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.USER_FOLLOWERS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            UserFollowers.USER_ID + " TEXT NOT NULL " + References.USER + "," +
            UserFollowers.FOLLOWER_ID + " TEXT NOT NULL " + References.USER + "," +
            " UNIQUE (" + UserFollowers.USER_ID + "," + UserFollowers.FOLLOWER_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.HISTORY_TRACK + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            History.HISTORY_ITEM_ID + " TEXT NOT NULL " + References.TRACK + "," +
            " UNIQUE (" + History.HISTORY_ITEM_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.HISTORY_PLAYLIST + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            History.HISTORY_ITEM_ID + " TEXT NOT NULL " + References.PLAYLIST + "," +
            " UNIQUE (" + History.HISTORY_ITEM_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.LIKED_TRACKS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LikedTracks.TRACK_ID + " TEXT NOT NULL " + References.TRACK + "," +
            LikedTracks.USER_ID + " TEXT NOT NULL " + References.USER + "," +
            " UNIQUE (" + LikedTracks.TRACK_ID + "," + LikedTracks.USER_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.TRACKS_PLAYLISTS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TracksPlaylists.TRACK_ID + " TEXT NOT NULL " + References.TRACK + "," +
            TracksPlaylists.PLAYLIST_ID + " TEXT NOT NULL " + References.PLAYLIST + "," +
            " UNIQUE (" + TracksPlaylists.TRACK_ID + "," + TracksPlaylists.PLAYLIST_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.MELOPHILE_PLAYLISTS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MelophileThemes.MELOPHILE_THEME_ID + " TEXT NOT NULL," +
            MelophileThemes.MELOPHILE_ITEM_ID + " TEXT NOT NULL " + References.PLAYLIST + "," +
            " UNIQUE (" + MelophileThemes.MELOPHILE_THEME_ID + "," + MelophileThemes.MELOPHILE_ITEM_ID + ") ON CONFLICT REPLACE)");

    db.execSQL("CREATE TABLE " + Tables.MELOPHILE_TRACKS + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MelophileThemes.MELOPHILE_THEME_ID + " TEXT NOT NULL," +
            MelophileThemes.MELOPHILE_ITEM_ID + " TEXT NOT NULL " + References.TRACK + "," +
            " UNIQUE (" + MelophileThemes.MELOPHILE_THEME_ID + "," + MelophileThemes.MELOPHILE_ITEM_ID + ") ON CONFLICT REPLACE)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + Tables.PLAYLISTS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.TRACKS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.USER_FOLLOWERS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.TRACKS_PLAYLISTS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.LIKED_TRACKS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.HISTORY_PLAYLIST);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.HISTORY_TRACK);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.MELOPHILE_TRACKS);
    db.execSQL("DROP TABLE IF EXISTS " + Tables.MELOPHILE_PLAYLISTS);
    onCreate(db);
  }

  static void deleteDatabase(Context context) {
    context.deleteDatabase(DATABASE_NAME);
  }
}