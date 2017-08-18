package com.vpaliy.melophile.ui.playlists;

import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.PlaylistSet;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.base.bus.event.OnClick;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;
import static com.vpaliy.melophile.ui.playlists.PlaylistsContract.Presenter;

public class PlaylistsFragment extends BaseFragment
        implements PlaylistsContract.View{

    private Presenter presenter;
    private CategoryAdapter adapter;

    @BindView(R.id.categories)
    protected RecyclerView categories;

    @Override
    protected int layoutId() {
        return R.layout.fragment_music;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new CategoryAdapter(getContext(),rxBus);
            categories.setAdapter(adapter);
            presenter.start();
        }
    }

    @Override
    public void showEmptyMessage() {
        //TODO show empty message
    }

    @Override
    public void showErrorMessage() {
        //TODO show error message
    }

    @Override
    public void showPlaylists(@NonNull PlaylistSet playlistSet) {
        PlaylistAdapter playlistAdapter=new PlaylistAdapter(getContext(),rxBus);
        playlistAdapter.setData(playlistSet.getPlaylists());
        adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(playlistSet.getThemeString(),playlistAdapter,1));
    }

    @Inject @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}
