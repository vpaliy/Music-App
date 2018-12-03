package com.vpaliy.melophile.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.roughike.bottombar.BottomBar;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.auth.AuthActivity;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.user.info.FavoriteFragment;
import com.vpaliy.melophile.ui.user.info.FollowersFragment;
import com.vpaliy.melophile.ui.user.info.InfoEvent;
import com.vpaliy.melophile.ui.utils.PresentationUtils;
import com.vpaliy.melophile.ui.view.HomePager;

import butterknife.ButterKnife;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

  private void setBottomNavigation() {
    final int duration = getResources().getInteger(R.integer.page_fading_duration);
    bottomNavigation.setOnTabSelectListener((tabId ->
            homePager.animate()
                    .alpha(0)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        switch (tabId) {
                          case R.id.playlists:
                            homePager.setCurrentItem(0, false);
                            break;
                          case R.id.tracks:
                            homePager.setCurrentItem(1, false);
                            break;
                          case R.id.personal:
                            homePager.setCurrentItem(2, false);
                        }
                        homePager.animate()
                                .alpha(1.f)
                                .setDuration(duration)
                                .setListener(null).start();
                      }
                    }).start()));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.search:
        View search = actionBar.findViewById(R.id.search);
        ViewCompat.setTransitionName(search, getString(R.string.search_trans));
        navigator.search(this, Pair.create(search, getString(R.string.search_trans)));
        return true;
      case R.id.invite:
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invite_message))
                .setDeepLink(Uri.parse("/link"))
                .build();
        startActivityForResult(intent, 1);
        return true;
      case R.id.log_out:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().apply();
        Intent logOutIntent = new Intent(this, AuthActivity.class);
        logOutIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        logOutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logOutIntent);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void handleEvent(@NonNull Object event) {
    if (event instanceof ExposeEvent) {
      navigator.navigate(this, (ExposeEvent) (event));
    } else if (event instanceof InfoEvent) {
      showFavorites((InfoEvent) (event));
    }
  }

  private void showFavorites(InfoEvent event) {
    FragmentTransaction transaction = getSupportFragmentManager()
            .beginTransaction();
    switch (event.code) {
      case InfoEvent.FAVORITE:
        FavoriteFragment.newInstance(event.toBundle())
                .show(transaction, null);
        break;
      case InfoEvent.FOLLOWERS:
        FollowersFragment.newInstance(event.toBundle())
                .show(transaction, null);
    }
  }

  private void setHomePager() {
    homePager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), this));
    homePager.setOffscreenPageLimit(4);
  }


  private void setActionBar() {
    int statusBarHeight = PresentationUtils.getStatusBarHeight(getResources());
    actionBar.getLayoutParams().height += statusBarHeight;
    actionBar.setPadding(0, statusBarHeight, 0, 0);
    setSupportActionBar(actionBar);
  }

  @Override
  public void inject() {
    App.appInstance()
            .appComponent()
            .inject(this);
  }
}
