package com.vpaliy.melophile.ui.search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;

public class SearchAdapter extends FragmentStatePagerAdapter {

    private ArrayMap<Integer,SearchResult> results;

    public SearchAdapter(FragmentManager manager){
        super(manager);
        results=new ArrayMap<>();
    }

    @Override
    public SearchResult getItem(int position) {
        SearchResult fragment=results.get(position);
        if(fragment==null){
            fragment=new SearchResult();
            results.put(position,fragment);
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tracks";
            case 1:
                return "Playlists";
            default:
                return "Profiles";
        }
    }
}
