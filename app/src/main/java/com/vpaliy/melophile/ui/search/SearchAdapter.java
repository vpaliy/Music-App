package com.vpaliy.melophile.ui.search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.ui.base.BaseAdapter;
import java.util.List;

@SuppressWarnings("unchecked")
public class SearchAdapter extends FragmentStatePagerAdapter {

    public static final int TYPE_TRACKS=0;
    public static final int TYPE_PLAYLISTS=1;
    public static final int TYPE_USERS=2;

    private ArrayMap<Integer,Pair<SearchResult,BaseAdapter<?>>> results;
    private ArrayMap<SearchResult,Integer> resultMap;

    public SearchAdapter(FragmentManager manager){
        super(manager);
        results=new ArrayMap<>();
        resultMap=new ArrayMap<>();
    }

    @Override
    public SearchResult getItem(int position) {
        Pair<SearchResult,BaseAdapter<?>> pair=results.get(position);
        if(pair==null||pair.first==null){
            SearchResult fragment=new SearchResult();
            results.put(position,Pair.create(fragment,null));
            resultMap.put(fragment,position);
            return fragment;
        }
        return pair.first;
    }

    public void appendTracks(List<Track> data){
        Pair<SearchResult,BaseAdapter<?>> pair=results.get(0);
        if (pair != null) {
            if (pair.second != null) {
                BaseAdapter<Track> adapter=(BaseAdapter<Track>)(pair.second);
                adapter.setData(data);
            }
        }
    }

    public void appendPlaylists(List<Playlist> data){
        Pair<SearchResult,BaseAdapter<?>> pair=results.get(1);
        if (pair != null) {
            if (pair.second != null) {
                BaseAdapter<Playlist> adapter=(BaseAdapter<Playlist>)(pair.second);
                adapter.setData(data);
            }
        }
    }

    public void appendUsers(List<User> data){
        Pair<SearchResult,BaseAdapter<?>> pair=results.get(2);
        if (pair != null) {
            if (pair.second != null) {
                BaseAdapter<User> adapter=(BaseAdapter<User>)(pair.second);
                adapter.setData(data);
            }
        }
    }

    public int getType(SearchResult result){
        if(result==null) return -1;
        return resultMap.get(result);
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
