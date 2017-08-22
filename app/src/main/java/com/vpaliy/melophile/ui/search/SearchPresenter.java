package com.vpaliy.melophile.ui.search;

import com.vpaliy.domain.interactor.PlaylistSearch;
import com.vpaliy.domain.interactor.TrackSearch;
import com.vpaliy.domain.interactor.UserSearch;
import static com.vpaliy.melophile.ui.search.SearchContract.View;
import static dagger.internal.Preconditions.checkNotNull;

import com.vpaliy.melophile.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;

@ViewScope
public class SearchPresenter implements SearchContract.Presenter {

    private TrackSearch trackSearchUseCase;
    private PlaylistSearch playlistSearchUseCase;
    private UserSearch userSearchUseCase;
    private View view;

    @Inject
    public SearchPresenter(TrackSearch trackSearchUseCase,
                           PlaylistSearch playlistSearchUseCase,
                           UserSearch userSearchUseCase){
        this.trackSearchUseCase=trackSearchUseCase;
        this.playlistSearchUseCase=playlistSearchUseCase;
        this.userSearchUseCase=userSearchUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void query(String query) {

    }
}
