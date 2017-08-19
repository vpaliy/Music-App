package com.vpaliy.melophile.ui.user;

import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.user.favorite.FavoriteEvent;
import com.vpaliy.melophile.ui.user.favorite.FavoriteFragment;
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
        }else if(event instanceof FavoriteEvent){
            showFavorites((FavoriteEvent)(event));
        }
    }

    private void showFavorites(FavoriteEvent event){
        FragmentTransaction transaction=getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in,0,R.anim.slide_in,0);
        FavoriteFragment.newInstance(event.toBundle())
                .show(transaction,null);
    }
}
