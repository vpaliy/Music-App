package com.vpaliy.domain.model;

import java.util.List;

public class TrackSet {
    private MelophileTheme theme;
    private List<Track> tracks;

    public MelophileTheme getTheme() {
        return theme;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTheme(MelophileTheme theme) {
        this.theme = theme;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
