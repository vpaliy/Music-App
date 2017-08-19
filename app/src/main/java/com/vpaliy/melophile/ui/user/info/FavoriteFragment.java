package com.vpaliy.melophile.ui.user.info;

import android.os.Bundle;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.user.UserTracksAdapter;
import java.util.List;
import javax.inject.Inject;
import android.support.annotation.NonNull;

public class FavoriteFragment extends BaseInfoFragment<Track>{

    public static FavoriteFragment newInstance(Bundle args){
        FavoriteFragment fragment=new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showInfo(@NonNull List<Track> tracks) {
        UserTracksAdapter adapter=new UserTracksAdapter(getContext(),rxBus);
        adapter.setData(tracks);
        favorites.setAdapter(adapter);
    }

    @Override
    protected void inject() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull UserInfoContract.Presenter<Track> presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
