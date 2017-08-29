package com.vpaliy.data.source.local.handler;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.MelophileTheme;
import com.vpaliy.domain.model.Playlist;
import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import static com.vpaliy.data.source.local.MusicContract.MelophileThemes;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings({"unused","WeakerAccess"})
@Singleton
public class PlaylistHandler {

    private ContentResolver provider;

    @Inject
    public PlaylistHandler(@NonNull Context context){
        this.provider=context.getContentResolver();
    }

    public List<Playlist>  queryAll(Query query){
        if(query==null){
            return queryAll();
        }
        Cursor cursor=provider.query(Playlists.CONTENT_URI,null,query.selection(),query.args(),null);
        return queryAll(cursor);
    }

    public List<Playlist> queryAll(){
        Cursor cursor=provider.query(Playlists.CONTENT_URI,null,null,null,null);
        return queryAll(cursor);
    }

    private List<Playlist> queryAll(Cursor cursor){
        if(cursor!=null){
            List<Playlist> playlists=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                Playlist playlist= DatabaseUtils.toPlaylist(cursor);
                playlists.add(playlist);
            }
            if(!cursor.isClosed()) cursor.close();
            return playlists;
        }
        return null;
    }

    public List<Playlist> queryByTheme(MelophileTheme theme){
        if(theme!=null){
            Cursor cursor=provider.query(MelophileThemes.buildPlaylistsTheme(theme.getTheme()),null,null,null,null);
            if(cursor!=null){
                List<Playlist> playlists=new ArrayList<>(cursor.getCount());
                while(cursor.moveToNext()){
                    playlists.add(query(cursor.getString(cursor.getColumnIndex(MelophileThemes.MELOPHILE_ITEM_ID))));
                }
                if(!cursor.isClosed()) cursor.close();
                return playlists;
            }
            return null;
        }
        throw new IllegalArgumentException("Theme is null");
    }

    public Playlist query(String id){
        if(TextUtils.isEmpty(id)){
            throw new IllegalArgumentException("Id is null");
        }
        Cursor cursor=provider.query(Playlists.buildPlaylistUri(id),null,null,null,null);
        Playlist playlist=DatabaseUtils.toPlaylist(cursor);
        if(cursor!=null) cursor.close();
        return playlist;
    }

    public void insert(Playlist playlist){
        if(playlist!=null){
            ContentValues values=DatabaseUtils.toValues(playlist);
            provider.insert(Playlists.CONTENT_URI,values);
        }
    }

    public void insert(MelophileTheme theme, Playlist playlist){
        ContentValues values=DatabaseUtils.toValues(theme,playlist);
        if(values!=null){
            provider.insert(MelophileThemes.CONTENT_URI,values);
        }
    }
}
