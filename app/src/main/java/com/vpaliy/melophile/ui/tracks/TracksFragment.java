package com.vpaliy.melophile.ui.tracks;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.domain.model.TrackSet;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseFragment;
import com.vpaliy.melophile.ui.playlists.CategoryAdapter;

import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.vpaliy.melophile.ui.tracks.TracksContract.Presenter;

public class TracksFragment extends BaseFragment
    implements TracksContract.View{

    @BindView(R.id.categories)
    protected RecyclerView categories;

    private CategoryAdapter adapter;
    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_music,container,false);
        bind(root);
        return root;
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
    public void showErrorMessage() {

    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showTrackSet(@NonNull TrackSet trackSet) {
        TracksAdapter tracksAdapter=new TracksAdapter(getContext(),rxBus);
        tracksAdapter.setData(trackSet.getTracks());
        adapter.addItem(CategoryAdapter.CategoryWrapper.wrap(trackSet.getThemeString(),tracksAdapter,0));
    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter!=null){
            presenter.stop();
        }
    }
}
