package com.vpaliy.data.source.local;

import com.vpaliy.data.repository.MusicRepository;

import static com.vpaliy.data.source.local.MusicContract.Users;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.History;
import static com.vpaliy.data.source.local.MusicContract.Me;
import static com.vpaliy.data.source.local.MusicContract.MelophileThemes;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.Tables;

public enum MusicMatchEnum {

  PLAYLISTS(100, Playlists.CONTENT_DIR_TYPE, Tables.PLAYLISTS, MusicContract.PATH_PLAYLIST),
  PLAYLIST(102, Playlists.CONTENT_ITEM_TYPE, Tables.PLAYLISTS, MusicContract.PATH_PLAYLIST + "/*"),
  TRACKS(200, Tracks.CONTENT_DIR_TYPE, Tables.TRACKS, MusicContract.PATH_TRACK),
  TRACK(202, Tracks.CONTENT_ITEM_TYPE, Tables.TRACKS, MusicContract.PATH_TRACK + "/*"),
  USERS(300, Users.CONTENT_DIR_TYPE, Tables.USERS, MusicContract.PATH_USER),
  USER(301, Users.CONTENT_ITEM_TYPE, Tables.USERS, MusicContract.PATH_USER + "/*"),
  HISTORY_TRACKS_GET(400, History.CONTENT_DIR_TYPE, Tables.HISTORY_JOIN_TRACKS, MusicContract.PATH_HISTORY_TRACKS + "/" + MusicContract.PATH_HISTORY),
  HISTORY_PLAYLISTS_GET(401, History.CONTENT_DIR_TYPE, Tables.HISTORY_JOIN_PLAYLISTS, MusicContract.PATH_HISTORY_PLAYLISTS + "/" + MusicContract.PATH_HISTORY),
  HISTORY_TRACKS(402, History.CONTENT_DIR_TYPE, Tables.HISTORY_TRACK, MusicContract.PATH_HISTORY_TRACKS),
  HISTORY_PLAYLISTS(403, History.CONTENT_DIR_TYPE, Tables.HISTORY_PLAYLIST, MusicContract.PATH_HISTORY_PLAYLISTS),
  ME(600, Me.CONTENT_DIR_TYPE, Tables.ME, MusicContract.PATH_ME),

  USER_TRACKS(302, Tracks.CONTENT_ITEM_TYPE, Tables.USER_JOIN_TRACKS, MusicContract.PATH_USER + "/*/" + MusicContract.PATH_TRACK),
  USER_PLAYLISTS(303, Playlists.CONTENT_ITEM_TYPE, Tables.USER_JOIN_PLAYLISTS, MusicContract.PATH_USER + "/*/" + MusicContract.PATH_PLAYLIST),
  USER_LIKED_TRACKS(304, Tracks.CONTENT_ITEM_TYPE, Tables.LIKED_TRACKS, MusicContract.PATH_USER + "/*/" + MusicContract.PATH_TRACK),
  USER_FOLLOWERS(305, Users.CONTENT_ITEM_TYPE, Tables.USER_FOLLOWERS, MusicContract.PATH_USER + "/*/" + MusicContract.PATH_USER),

  MELOPHILE_TRACKS(701, MelophileThemes.CONTENT_DIR_TYPE, Tables.MELOPHILE_TRACKS, MusicContract.PATH_MELOPHILE_TRACKS),
  MELOPHILE_PLAYLISTS(702, MelophileThemes.CONTENT_DIR_TYPE, Tables.MELOPHILE_PLAYLISTS, MusicContract.PATH_MELOPHILE_PLAYLISTS),
  MELOPHILE_PLAYLISTS_THEME(703, MelophileThemes.CONTENT_DIR_TYPE, Tables.MELOPHILE_PLAYLISTS, MusicContract.PATH_MELOPHILE_THEMES + "/*/" + MusicContract.PATH_PLAYLIST),
  MELOPHILE_TRACKS_THEME(704, MelophileThemes.CONTENT_DIR_TYPE, Tables.MELOPHILE_TRACKS, MusicContract.PATH_MELOPHILE_THEMES + "/*/" + MusicContract.PATH_TRACK),

  PLAYLIST_TRACKS(101, Tracks.CONTENT_ITEM_TYPE, Tables.TRACKS_PLAYLISTS, MusicContract.PATH_PLAYLIST + "/*/" + MusicContract.PATH_TRACK),
  TRACKS_PLAYLISTS(201, Playlists.CONTENT_ITEM_TYPE, Tables.TRACKS_PLAYLISTS, MusicContract.PATH_TRACK + "/*/" + MusicContract.PATH_PLAYLIST),

  ME_FOLLOWINGS(601, Me.CONTENT_DIR_TYPE, Tables.USERS, MusicContract.PATH_ME + "/" + MusicContract.PATH_USER),
  ME_TRACKS(602, Me.CONTENT_DIR_TYPE, Tables.TRACKS, MusicContract.PATH_ME + "/" + MusicContract.PATH_TRACK);

  public int code;
  public String contentType;
  public String table;
  public String path;

  MusicMatchEnum(int code, String contentType, String table, String path) {
    this.code = code;
    this.contentType = contentType;
    this.table = table;
    this.path = path;
  }
}
