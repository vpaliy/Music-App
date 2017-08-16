package com.vpaliy.melophile.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import com.roughike.bottombar.BottomBar;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.utils.PresentationUtils;
import com.vpaliy.melophile.ui.view.HomePager;
import butterknife.ButterKnife;
import android.support.v7.widget.Toolbar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.bottom_navigation)
    protected BottomBar bottomNavigation;

    @BindView(R.id.pager)
    protected HomePager homePager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setActionBar();
        setHomePager();
        setBottomNavigation();
    }

    private void setBottomNavigation(){
        final int duration=300;//getResources().getInteger(R.integer.page_fade_duration);
        bottomNavigation.setOnTabSelectListener((tabId ->
                homePager.animate()
                        .alpha(0)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                switch (tabId){
                                    case R.id.playlists:
                                        homePager.setCurrentItem(0,false);
                                        break;
                                    case R.id.tracks:
                                        homePager.setCurrentItem(1,false);
                                        break;
                                    case R.id.personal:
                                        homePager.setCurrentItem(2,false);
                                }
                                homePager.animate()
                                        .alpha(1.f)
                                        .setDuration(duration)
                                        .setListener(null).start();
                            }
                        }).start()));
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }

    private void setHomePager(){
        homePager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),this));
        homePager.setOffscreenPageLimit(3);
    }


    private void setActionBar(){
        int statusBarHeight= PresentationUtils.getStatusBarHeight(getResources());
        actionBar.getLayoutParams().height+=statusBarHeight;
        actionBar.setPadding(0,statusBarHeight,0,0);
        setSupportActionBar(actionBar);
    }


    @Override
    public void inject() {
        App.appInstance()
                .appComponent()
                .inject(this);
    }
}
