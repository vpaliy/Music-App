package com.vpaliy.melophile.ui.base.bus.event;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;

public class ExposeEvent {

    public final Bundle data;
    public final Pair<View,String>[] pack;

    private ExposeEvent(Bundle data, Pair<View,String>[] pack){
        this.data=data;
        this.pack=pack;
    }

    public static ExposeEvent exposePlaylist(Bundle data, Pair<View,String> ... pack){
        return new ExposeEvent(data,pack);
    }
}
