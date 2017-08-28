package com.vpaliy.data.source.local.handler;

import android.content.ContentProvider;
import android.database.Cursor;

import com.vpaliy.data.source.local.MusicContract;
import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.Track;

import java.util.ArrayList;
import java.util.List;
import static com.vpaliy.data.source.local.MusicContract.Tracks;

@SuppressWarnings({"unused","WeakerAccess"})
public class TrackHandler {

    private ContentProvider provider;

    public TrackHandler(ContentProvider provider){
        this.provider=provider;
    }

    public List<Track> queryAll(Query query){
        if(query!=null){
            Cursor cursor=provider.query(Tracks.CONTENT_URI,null,query.selection(),query.args(),null);
            return queryAll(cursor);
        }
        return queryAll();
    }

    private List<Track> queryAll(Cursor cursor){
        if(cursor!=null){
            List<Track> tracks=new ArrayList<>();
            while(cursor.moveToNext()){
                Track track= DatabaseUtils.toTrack(cursor);
                tracks.add(track);
            }
            if(!cursor.isClosed()) cursor.close();
            return tracks;
        }
        return null;
    }

    public List<Track> queryAll(){
        Cursor cursor=provider.query(Tracks.CONTENT_URI,null,null,null,null);
        return queryAll(cursor);
    }
}
