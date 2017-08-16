package com.vpaliy.melophile.ui.playlist;

import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PlaylistActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        if(savedInstanceState==null)
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,new PlaylistFragment())
                .commit();

    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }
}
