package com.vpaliy.melophile.ui.user;

import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.user.info.FollowersFragment;
import com.vpaliy.melophile.ui.user.info.InfoEvent;
import com.vpaliy.melophile.ui.user.info.FavoriteFragment;
import com.vpaliy.melophile.ui.utils.Constants;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


public class PersonActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(savedInstanceState==null){
            savedInstanceState=getIntent().getExtras().getBundle(Constants.EXTRA_DATA);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame, PersonFragment.newInstance(savedInstanceState))
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
        }else if(event instanceof InfoEvent){
            showFavorites((InfoEvent)(event));
        }
    }

    private void showFavorites(InfoEvent event){
        FragmentTransaction transaction=getSupportFragmentManager()
                .beginTransaction();
        switch (event.code) {
            case InfoEvent.FAVORITE:
                FavoriteFragment.newInstance(event.toBundle())
                    .show(transaction, null);
                break;
            case InfoEvent.FOLLOWERS:
                FollowersFragment.newInstance(event.toBundle())
                        .show(transaction,null);
        }
    }
}
