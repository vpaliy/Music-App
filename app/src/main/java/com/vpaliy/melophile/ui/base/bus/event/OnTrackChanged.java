package com.vpaliy.melophile.ui.base.bus.event;

public class OnTrackChanged {

    public final int position;

    public OnTrackChanged(int position){
        this.position=position;
    }

    public static OnTrackChanged change(int position){
        return new OnTrackChanged(position);
    }
}
