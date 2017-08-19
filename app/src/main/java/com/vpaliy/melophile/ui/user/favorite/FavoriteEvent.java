package com.vpaliy.melophile.ui.user.favorite;

public class FavoriteEvent {

    public final String id;

    public FavoriteEvent(String id){
        this.id=id;
    }

    public static FavoriteEvent show(String id){
        return new FavoriteEvent(id);
    }
}
