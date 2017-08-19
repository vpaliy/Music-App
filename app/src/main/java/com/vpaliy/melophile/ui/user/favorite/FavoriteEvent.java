package com.vpaliy.melophile.ui.user.favorite;

import android.os.Bundle;

import com.vpaliy.melophile.ui.utils.Constants;

public class FavoriteEvent {

    public final String id;

    public FavoriteEvent(String id){
        this.id=id;
    }

    public Bundle toBundle(){
        Bundle bundle=new Bundle();
        bundle.putString(Constants.EXTRA_ID,id);
        return bundle;
    }

    public static FavoriteEvent show(String id){
        return new FavoriteEvent(id);
    }
}
