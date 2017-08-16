package com.vpaliy.melophile.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vpaliy.melophile.ui.playlists.PlaylistsFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public HomePagerAdapter(FragmentManager manager, Context context){
        super(manager);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int position) {
        return new PlaylistsFragment();
    }

}