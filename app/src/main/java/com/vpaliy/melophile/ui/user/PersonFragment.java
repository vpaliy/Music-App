package com.vpaliy.melophile.ui.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import static com.vpaliy.melophile.ui.user.PersonContract.Presenter;

public class PersonFragment extends BaseFragment
        implements PersonContract.View{

    private Presenter presenter;

    public static PersonFragment newInstance(Bundle args){
        PersonFragment fragment=new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_user;
    }

    private void extractId(Bundle bundle){

    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractId(savedInstanceState);
        if(view!=null){

        }
    }

    @Override
    public void showTracks(List<Track> tracks) {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showAvatar(String avatarUrl) {

    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showPlaylists(List<Playlist> playlists) {

    }
}
