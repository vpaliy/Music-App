package com.vpaliy.domain.playback;

import com.vpaliy.domain.model.Track;
import java.util.List;

public class QueueManager {

    private int index;
    private List<Track> tracks;

    public QueueManager(List<Track> tracks, int index){
        this.index=index;
        this.tracks=tracks;
    }

    public void setTracks(List<Track> tracks) {
        if(tracks==null){
            throw new IllegalArgumentException("Tracks are null");
        }
        this.tracks = tracks;
    }

    public void addTrack(Track track){
        tracks.add(track);
    }

    public Track current(){
        if(tracks==null||tracks.isEmpty()) return null;
        return tracks.get(index);
    }

    public Track next(){
        if((index+1)<tracks.size()){
            return tracks.get(++index);
        }
        return null;
    }

    public Track previous(){
        if((index==0)||(index-1)>=tracks.size()){
            return null;
        }
        return tracks.get(--index);
    }

    public static QueueManager createQueue(List<Track> tracks, int index){
        return new QueueManager(tracks,index);
    }
}
