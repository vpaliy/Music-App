package com.vpaliy.melophile.ui.base.bus.event;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;

public class ExposeEvent {

    public static final int PLAYER=0;
    public static final int PLAYLIST=1;
    public static final int USER=2;

    public final int code;
    public final Bundle data;
    public final Pair<View,String>[] pack;

    private ExposeEvent(Bundle data, Pair<View,String>[] pack, int code){
        this.data=data;
        this.pack=pack;
        this.code=code;
    }

    public static ExposeEvent exposePlaylist(Bundle data, Pair<View,String> ... pack){
        return new ExposeEvent(data,pack,PLAYLIST);
    }

    public static ExposeEvent exposeTrack(Bundle data, Pair<View,String> ... pack){
        return new ExposeEvent(data,pack,PLAYER);
    }

    public static ExposeEvent exposeUser(Bundle data, Pair<View,String>...pack){
        return new ExposeEvent(data,pack,USER);
    }
}
