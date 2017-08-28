package com.vpaliy.data.source.local.utils;

import android.content.ContentValues;
import android.text.TextUtils;
import com.vpaliy.data.mapper.MapperUtils;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import static com.vpaliy.data.source.local.MusicContract.Tracks;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.Users;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.TracksPlaylists;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.LikedTracks;
import static com.vpaliy.data.source.local.MusicDatabaseHelper.UserFollowers;

public final class DatabaseUtils {

    public static ContentValues toValues(Track track){
        if(track==null) return null;
        ContentValues values=new ContentValues();
        values.put(Tracks.TRACK_ID,track.getId());
        values.put(Tracks.TRACK_STREAM_URL,track.getStreamUrl());
        values.put(Tracks.TRACK_ART_URL,track.getArtworkUrl());
        values.put(Tracks.TRACK_DURATION,track.getDuration());
        values.put(Tracks.TRACK_TAGS,MapperUtils.toString(track.getTags()));
        values.put(Tracks.TRACK_RELEASE_DATE,track.getReleaseDate());
        values.put(Tracks.TRACK_TITLE,track.getTitle());
        values.put(Tracks.TRACK_ARTIST,track.getArtist());
        values.put(Tracks.TRACK_IS_LIKED,track.isLiked()?1:0);
        //get the user
        User user=track.getUser();
        if(user!=null) {
            values.put(Tracks.TRACK_USER_ID, user.getId());
        }
        return values;
    }

    public static ContentValues toValues(Playlist playlist){
        if(playlist==null) return null;
        ContentValues values=new ContentValues();
        values.put(Playlists.PLAYLIST_ID,playlist.getId());
        values.put(Playlists.PLAYLIST_ART_URL,playlist.getArtUrl());
        values.put(Playlists.PLAYLIST_DURATION,playlist.getDuration());
        values.put(Playlists.PLAYLIST_RELEASE_DATE,playlist.getReleaseDate());
        values.put(Playlists.PLAYLIST_TITLE,playlist.getTitle());
        values.put(Playlists.PLAYLIST_DESCRIPTION,playlist.getDescription());
        values.put(Playlists.PLAYLIST_TRACK_COUNT,playlist.getTrackCount());
        values.put(Playlists.PLAYLIST_GENRES, MapperUtils.toString(playlist.getGenres()));
        values.put(Playlists.PLAYLIST_TAGS,MapperUtils.toString(playlist.getTags()));
        User user=playlist.getUser();
        if(user!=null) {
            values.put(Playlists.PLAYLIST_USER_ID, user.getId());
        }
        return values;
    }

    public static ContentValues toValues(User user){
        if(user==null) return null;
        ContentValues values=new ContentValues();
        values.put(Users.USER_ID,user.getId());
        values.put(Users.USER_ART_URL,user.getAvatarUrl());
        values.put(Users.USER_NICKNAME,user.getNickName());
        values.put(Users.USER_FULLNAME,user.getFullName());
        values.put(Users.USER_DESCRIPTION,user.getDescription());
        values.put(Users.USER_FOLLOWINGS_COUNT,user.getFollowingCount());
        values.put(Users.USER_FOLLOWER_COUNT,user.getFollowersCount());
        values.put(Users.USER_TRACKS_COUNT,user.getTracksCount());
        values.put(Users.USER_LIKED_TRACKS_COUNT,user.getLikedTracksCount());
        values.put(Users.USER_IS_FOLLOWED,user.isFollowed()?1:0);
        values.put(Users.USER_PLAYLISTS_COUNT,user.getPlaylistsCount());
        return values;
    }

    public static ContentValues toValues(User user, Track track){
        if(user==null||track==null) return null;
        ContentValues values=new ContentValues();
        values.put(LikedTracks.TRACK_ID,track.getId());
        values.put(LikedTracks.USER_ID,user.getId());
        return values;
    }

    public static ContentValues toValues(Track track, Playlist playlist){
        if(track==null||playlist==null) return null;
        ContentValues values=new ContentValues();
        values.put(TracksPlaylists.TRACK_ID,track.getId());
        values.put(TracksPlaylists.PLAYLIST_ID,playlist.getId());
        return values;
    }

    public static ContentValues toValues(User user, User follower){
        if(user==null||follower==null|| TextUtils.equals(user.getId(),follower.getId())){
            return null;
        }
        ContentValues values=new ContentValues();
        values.put(UserFollowers.USER_ID,user.getId());
        values.put(UserFollowers.FOLLOWER_ID,follower.getId());
        return values;
    }
}
