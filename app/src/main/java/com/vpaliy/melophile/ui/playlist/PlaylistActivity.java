package com.vpaliy.melophile.ui.playlist;

import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.Constants;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PlaylistActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        if(savedInstanceState==null){
            savedInstanceState=getIntent().getExtras().getBundle(Constants.EXTRA_DATA);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame, PlaylistFragment.newInstance(savedInstanceState))
                    .commit();
        }
    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeEvent){
            navigator.navigate(this,(ExposeEvent)(event));
        }
    }
}
