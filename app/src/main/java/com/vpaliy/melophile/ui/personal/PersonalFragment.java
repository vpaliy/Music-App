package com.vpaliy.melophile.ui.personal;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import java.util.List;
import static com.vpaliy.melophile.ui.personal.PersonalContract.Presenter;
import android.support.annotation.NonNull;
import javax.inject.Inject;

public class PersonalFragment extends BaseFragment
        implements PersonalContract.View{

    private Presenter presenter;

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_personal;
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showLovedTracks(@NonNull List<Track> tracks) {

    }

    @Override
    public void showPlaylistHistory(@NonNull List<Playlist> playlists) {

    }

    @Override
    public void showTrackHistory(@NonNull List<Track> tracks) {

    }
}
