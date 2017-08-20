package com.vpaliy.data.source.remote;

import android.util.Log;

import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Filter {

    @Inject
    public Filter(){}

    public List<TrackEntity> filterTracks(List<TrackEntity> tracks){
        if(tracks!=null){
            List<TrackEntity> list=new LinkedList<>();
            for(TrackEntity entity:tracks){
                entity=filter(entity);
                if(entity!=null) list.add(entity);
            }
            return list.isEmpty()?null:list;
        }
        return null;
    }

    public List<PlaylistEntity> filterPlaylists(List<PlaylistEntity> playlistEntities){
        if(playlistEntities!=null){
            List<PlaylistEntity> result=new LinkedList<>();
            for(PlaylistEntity entity:playlistEntities){
                entity=filter(entity);
                if(entity!=null){
                    result.add(entity);
                }
            }
            return result.isEmpty()?null:result;
        }
        return null;
    }

    public TrackEntity filter(TrackEntity trackEntity){
        if(trackEntity!=null){
            if(trackEntity.artwork_url!=null && trackEntity.is_streamable){
                return trackEntity;
            }
        }
        return null;
    }

    public PlaylistEntity filter(PlaylistEntity playlistEntity){
        if(playlistEntity!=null){
            if(playlistEntity.artwork_url!=null){
                playlistEntity.tracks=filterTracks(playlistEntity.tracks);
                return playlistEntity;
            }
        }
        return null;
    }

}
