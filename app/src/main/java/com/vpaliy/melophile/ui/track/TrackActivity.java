package com.vpaliy.melophile.ui.track;


import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TrackActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }
}
