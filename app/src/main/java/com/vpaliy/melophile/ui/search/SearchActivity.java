package com.vpaliy.melophile.ui.search;

import android.os.Bundle;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseActivity;
import java.util.List;
import static com.vpaliy.melophile.ui.search.SearchContract.Presenter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SearchActivity extends BaseActivity
            implements SearchContract.View{

    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void inject() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showTracks(@NonNull List<Track> tracks) {

    }

    @Override
    public void showPlaylists(@NonNull List<Playlist> playlists) {

    }

    @Override
    public void showUsers(@NonNull List<User> users) {

    }

    @Override
    public void showEmptyMessage() {
        //TODO add empty message
    }

    @Override
    public void showErrorMessage() {
        //TODO add error message
    }
}
