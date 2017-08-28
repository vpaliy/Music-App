package com.vpaliy.data.source.local;

import static com.vpaliy.data.source.local.MusicContract.Users;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.History;
import static com.vpaliy.data.source.local.MusicContract.Me;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.Tables;

public enum MusicMatchEnum {

    PLAYLISTS(100,Playlists.CONTENT_DIR_TYPE, Tables.PLAYLISTS,MusicContract.PATH_PLAYLIST),
    PLAYLIST(102,Playlists.CONTENT_ITEM_TYPE,Tables.PLAYLISTS,MusicContract.PATH_PLAYLIST+"/#"),
    TRACKS(200,Tracks.CONTENT_DIR_TYPE,Tables.TRACKS,MusicContract.PATH_TRACK),
    TRACK(202,Tracks.CONTENT_ITEM_TYPE,Tables.TRACKS,MusicContract.PATH_TRACK+"/#"),
    USERS(300,Users.CONTENT_DIR_TYPE,Tables.USERS,MusicContract.PATH_USER),
    USER(302,Users.CONTENT_ITEM_TYPE,Tables.USERS,MusicContract.PATH_USER+"/#"),
    HISTORY_TRACKS(400,History.CONTENT_DIR_TYPE,Tables.HISTORY_TRACK,MusicContract.PATH_HISTORY_TRACKS),
    HISTORY_PLAYLISTS(500,History.CONTENT_DIR_TYPE,Tables.HISTORY_PLAYLIST,MusicContract.PATH_HISTORY_PLAYLISTS),
    ME(600,Me.CONTENT_DIR_TYPE,Tables.ME,MusicContract.PATH_ME),

    USER_TRACKS(301,Tracks.CONTENT_ITEM_TYPE,Tables.USER_JOIN_TRACKS,MusicContract.PATH_USER+"/#/"+MusicContract.PATH_TRACK),
    USER_PLAYLISTS(302, Playlists.CONTENT_ITEM_TYPE,Tables.USER_JOIN_PLAYLISTS,MusicContract.PATH_USER+"/#/"+MusicContract.PATH_PLAYLIST),
    USER_LIKED_TRACKS(303,Tracks.CONTENT_ITEM_TYPE,Tables.USER_JOIN_LIKED_TRACKS,MusicContract.PATH_USER+"/#/"+MusicContract.PATH_TRACK),

    PLAYLIST_TRACKS(101,Tracks.CONTENT_ITEM_TYPE,Tables.PLAYLIST_JOIN_TRACKS,MusicContract.PATH_PLAYLIST+"/#/"+MusicContract.PATH_TRACK),

    ME_TRACKS(601,Tracks.CONTENT_ITEM_TYPE,Tables.ME_JOIN_TRACKS,MusicContract.PATH_ME+"/#/"+MusicContract.PATH_TRACK);

    public int code;
    public String contentType;
    public String table;
    public String path;

    MusicMatchEnum(int code, String contentType,String table,String path){
        this.code=code;
        this.contentType=contentType;
        this.table=table;
        this.path=path;
    }
}
