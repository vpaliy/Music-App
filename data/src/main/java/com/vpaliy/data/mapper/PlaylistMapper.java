package com.vpaliy.data.mapper;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.model.MiniUserEntity;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;

public class PlaylistMapper extends Mapper<Playlist,PlaylistEntity>{

    private Mapper<Track,TrackEntity> trackMapper;
    private Mapper<User,MiniUserEntity> userMapper;

    public PlaylistMapper(Mapper<Track,TrackEntity> trackMapper,
                          Mapper<User,MiniUserEntity> userMapper){
        this.trackMapper=trackMapper;
        this.userMapper=userMapper;
    }

    @Override
    public Playlist map(PlaylistEntity playlistEntity) {
        if(playlistEntity==null) return null;
        Playlist playlist=new Playlist();
        playlist.setId(playlistEntity.id);
        playlist.setArtUrl(playlistEntity.artwork_url);
        playlist.setTitle(playlistEntity.title);
        playlist.setTracks(trackMapper.map(playlistEntity.tracks));
        playlist.setUser(userMapper.map(playlistEntity.user));
        playlist.setDescription(playlistEntity.description);
        playlist.setTrackCount(playlistEntity.track_count);
        playlist.setGenres(MapperUtils.splitString(playlistEntity.genre));
        playlist.setReleaseDate(playlistEntity.release);
        playlist.setTags(MapperUtils.splitString(playlistEntity.tag_list));
        playlist.setDuration(playlistEntity.duration);
        return playlist;
    }

    @Override
    public PlaylistEntity reverse(Playlist playlist) {
        if(playlist==null) return null;
        PlaylistEntity entity=new PlaylistEntity();
        entity.id=playlist.getId();
        entity.artwork_url=playlist.getArtUrl();
        entity.title=playlist.getTitle();
        entity.tracks=trackMapper.reverse(playlist.getTracks());
        entity.user=userMapper.reverse(playlist.getUser());
        entity.description=playlist.getDescription();
        entity.track_count=playlist.getTrackCount();
        entity.genre=MapperUtils.toString(playlist.getGenres());
        entity.release=playlist.getReleaseDate();
        entity.tag_list=MapperUtils.toString(playlist.getTags());
        entity.duration=playlist.getDuration();
        return entity;
    }
}
