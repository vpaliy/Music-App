package com.vpaliy.melophile.ui.base.bus.event;

import android.os.Bundle;

import com.vpaliy.melophile.ui.utils.Constants;

public class MoreMediaEvent {

    public static final int PLAYLIST=1;
    public static final int TRACK=2;

    public final Bundle data;
    public final int code;

    public MoreMediaEvent(Bundle data, int code){
        this.data=data;
        this.code=code;
    }

    public Bundle toBundle(){
        Bundle bundle=new Bundle();
        bundle.putBundle(Constants.EXTRA_DATA,data);
        bundle.putInt(Constants.EXTRA_CODE,code);
        return bundle;
    }

    public static MoreMediaEvent show(Bundle data, int code){
        return new MoreMediaEvent(data,code);
    }
}
