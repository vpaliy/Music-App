package com.vpaliy.melophile.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vpaliy.melophile.ui.personal.PersonalFragment;
import com.vpaliy.melophile.ui.playlists.PlaylistsFragment;
import com.vpaliy.melophile.ui.tracks.TracksFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    HomePagerAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PlaylistsFragment();
            case 1:
                return new TracksFragment();
            default:
                return new PersonalFragment();
        }
    }
}