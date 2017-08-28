package com.vpaliy.data.source.local.handler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.Playlist;
import java.util.ArrayList;
import java.util.List;
import static com.vpaliy.data.source.local.MusicContract.Playlists;
import android.support.annotation.NonNull;
import android.text.TextUtils;

@SuppressWarnings({"unused","WeakerAccess"})
public class PlaylistHandler {

    private ContentProvider provider;

    public PlaylistHandler(@NonNull ContentProvider provider){
        this.provider=provider;
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
}
