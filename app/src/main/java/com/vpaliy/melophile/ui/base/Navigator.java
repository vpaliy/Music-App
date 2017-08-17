package com.vpaliy.melophile.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;

import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.playlist.PlaylistActivity;
import com.vpaliy.melophile.ui.track.TrackActivity;
import com.vpaliy.melophile.ui.utils.Constants;
import com.vpaliy.melophile.ui.utils.Permission;

public class Navigator {

    public void navigate(Activity activity, ExposeEvent exposeEvent){
        Class<?> clazz=PlaylistActivity.class;
        switch (exposeEvent.code){
            case ExposeEvent.PLAYER:
                clazz= TrackActivity.class;
                break;
        }
        Intent intent=new Intent(activity,clazz);
        intent.putExtra(Constants.EXTRA_DATA,exposeEvent.data);
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            ActivityOptionsCompat optionsCompat= ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,exposeEvent.pack);
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }
}
