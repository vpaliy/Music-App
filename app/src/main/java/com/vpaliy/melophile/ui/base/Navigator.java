package com.vpaliy.melophile.ui.base;

import android.app.Activity;
import android.content.Intent;

import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.playlist.PlaylistActivity;
import com.vpaliy.melophile.ui.utils.Constants;

public class Navigator {

    public void navigate(Activity activity, ExposeEvent exposeEvent){
        Intent intent=new Intent(activity, PlaylistActivity.class);
        intent.putExtra(Constants.EXTRA_DATA,exposeEvent.data);
        activity.startActivity(intent);
    }
}
