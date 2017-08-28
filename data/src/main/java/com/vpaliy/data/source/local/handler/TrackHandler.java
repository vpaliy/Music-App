package com.vpaliy.data.source.local.handler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

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

    public Track query(String id){
        if(!TextUtils.isEmpty(id)){
            Cursor cursor=provider.query(Tracks.buildTrackUri(id),null,null,null,null);
            if(cursor!=null){
                Track track=DatabaseUtils.toTrack(cursor);
                if(!cursor.isClosed()) cursor.close();
                return track;
            }
            return null;
        }
        throw new IllegalArgumentException("Id is null");
    }

    public List<Track> queryAll(){
        Cursor cursor=provider.query(Tracks.CONTENT_URI,null,null,null,null);
        return queryAll(cursor);
    }

    public void insert(Track track){
        if(track!=null){
            ContentValues values= DatabaseUtils.toValues(track);
            provider.insert(Tracks.CONTENT_URI,values);
        }
    }
}
