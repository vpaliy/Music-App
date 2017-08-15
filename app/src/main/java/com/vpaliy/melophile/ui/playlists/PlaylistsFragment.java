package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.melophile.ui.base.BaseFragment;
import java.util.List;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.Presenter;

public class PlaylistsFragment extends BaseFragment
        implements PlaylistsContract.View{

    private Presenter presenter;

    @Override
    public void showEmptyMessage() {
        //TODO show empty message
    }

    @Override
    public void showErrorMessage() {
        //TODO show error message
    }

    @Override
    public void showPlaylists(@NonNull String category, @NonNull List<Playlist> playlists) {

    }

    @Inject @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {

    }
}
