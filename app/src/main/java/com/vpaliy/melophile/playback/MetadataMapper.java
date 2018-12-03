package com.vpaliy.melophile.playback;

import android.support.v4.media.MediaMetadataCompat;

import com.vpaliy.data.mapper.Mapper;
import com.vpaliy.domain.model.Track;
import com.vpaliy.soundcloud.auth.LoginActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MetadataMapper extends Mapper<MediaMetadataCompat, Track> {

  @Inject
  public MetadataMapper() {
  }

  @Override
  public MediaMetadataCompat map(Track track) {
    if (track == null) return null;
    return new MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, track.getArtworkUrl())
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, track.getTitle())
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.getArtist())
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, Long.parseLong(track.getDuration()))
            .putString(MediaMetadataCompat.METADATA_KEY_DATE, track.getReleaseDate())
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, track.getStreamUrl())
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, track.getId()).build();
  }

  @Override
  public Track reverse(MediaMetadataCompat metadataCompat) {
    if (metadataCompat == null) return null;
    Track track = new Track();
    track.setArtist(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
    track.setArtworkUrl(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI));
    track.setDuration(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DURATION));
    track.setReleaseDate(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_DATE));
    track.setStreamUrl(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI));
    track.setId(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID));
    return track;
  }
}
