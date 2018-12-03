package com.vpaliy.domain.playback;

import com.vpaliy.domain.model.Track;

import java.util.Collections;
import java.util.List;

public class QueueManager {

  private int index;
  private List<Track> tracks;

  public QueueManager(List<Track> tracks, int index) {
    this.index = index;
    this.tracks = tracks;
    //just in case
    invalidateIndexIfNeeded();
  }

  public void setTracks(List<Track> tracks) {
    if (isEmpty()) {
      throw new IllegalArgumentException("Tracks are null");
    }
    this.tracks = tracks;
    invalidateIndexIfNeeded();
  }

  public void addTrack(Track track) {
    tracks.add(track);
  }

  public Track current() {
    return !isEmpty() ? tracks.get(index) : null;
  }

  public Track next() {
    if (hasNext()) {
      return tracks.get(++index);
    }
    return current();
  }

  public Track previous() {
    if (hasPrevious()) {
      return tracks.get(--index);
    }
    return current();
  }

  private boolean isEmpty() {
    return tracks == null || tracks.isEmpty();
  }

  private void invalidateIndexIfNeeded() {
    if (isEmpty() || tracks.size() <= index) {
      index = 0;
    }
  }

  public void shuffle() {
    if (!isEmpty()) {
      Collections.shuffle(tracks);
    }
  }

  public boolean hasNext() {
    return !isEmpty() && tracks.size() > (index + 1);
  }

  public boolean hasPrevious() {
    return !isEmpty() && (index - 1) >= 0;
  }

  public int size() {
    return tracks.size();
  }

  public int currentIndex() {
    return index;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public static QueueManager createQueue(List<Track> tracks, int index) {
    return new QueueManager(tracks, index);
  }
}
